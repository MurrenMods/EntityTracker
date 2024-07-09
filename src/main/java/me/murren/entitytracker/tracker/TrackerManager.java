package me.murren.entitytracker.tracker;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.*;

public class TrackerManager implements Listener {
    public static List<Tracker> trackers = new ArrayList<>();
    public static HashMap<UUID, Integer> counts = new HashMap<>();

    public static boolean addTracker(Tracker tracker) {
        for(Tracker t : trackers)
        {
            if (t.trackingType == tracker.trackingType && t.player == tracker.player) {
                return false;
            }
        }
        trackers.add(tracker);
        counts.put(tracker.player.getUniqueId(), counts.getOrDefault(tracker.player.getUniqueId(), 0) + 1);
        return true;
    }

    public static boolean removeTracker(Player p, EntityType et) {
        Tracker toRemove = null;
        for(Tracker t : trackers)
        {
            if (t.trackingType == et && t.player == p) {
                toRemove = t;
                break;
            }
        }
        if (toRemove != null) {
            trackers.remove(toRemove);
            LogTracker(toRemove);
            counts.put(toRemove.player.getUniqueId(), counts.get(toRemove.player.getUniqueId()) - 1);
            return true;
        }
        return false;
    }

    public static void LogTracker(Tracker t) {
        t.player.sendMessage("§eThis tracker has tracked " + t.count + " " + t.trackingType.toString().toLowerCase() + "(s).");
        String time = "§eIt has been active for ";
        long diff = (new Date().getTime() - t.start.getTime());
        int diffDays = (int) diff / (1000 * 60 * 60 * 24);
        time += diffDays > 0 ? diffDays + " days, " : "";
        int diffHours = (int) diff / (1000 * 60 * 60) % 24;
        time += diffHours > 0 ? diffHours + " hours, " : "";
        int diffMinutes = (int) diff / (1000 * 60) % 60;
        time += diffMinutes > 0 ? diffMinutes + " minutes, " : "";
        int diffSeconds = (int) diff / 1000 % 60;
        time += diffSeconds > 0 ? diffSeconds + " seconds," : "";
        t.player.sendMessage(time);
        float rate = (t.count / (float)diff) * 3600000;
        t.player.sendMessage("§eresulting in " + rate + " " + t.trackingType.toString().toLowerCase() + "(s) per hour.");
    }

    public static void LogTracker(Player p, EntityType et)
    {
        for(Tracker t : trackers)
        {
            if (t.trackingType == et && t.player == p) {
                LogTracker(t);
                break;
            }
        }
    }

    public static void listTrackers(Player p) {
        List<Tracker> listed = new ArrayList<>();
        for(Tracker t : trackers)
        {
            if(t.player == p)
            {
                listed.add(t);
            }
        }
        if(listed.isEmpty())
        {
            p.sendMessage("§cYou have no trackers.");
            return;
        }
        p.sendMessage("§eYou have §6" + listed.size() + " §etrackers:");
        listed.forEach(t -> {
            p.sendMessage("§eTracking §2" + t.trackingType.toString().toLowerCase() + (t.distance == 0 ? "§e." : " §ewithin §2" + t.distance + " §eblocks."));
        });
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        for(Tracker t : trackers)
        {
            t.spawnEvent(event);
        }
    }

    public static void listAll(Player p)
    {
        if(trackers.isEmpty())
        {
            p.sendMessage("§cThere are no trackers.");
            return;
        }
        p.sendMessage("§eThere are §6" + trackers.size() + " §etrackers:");
        trackers.forEach(t -> {
            p.sendMessage("§6"+ t.player.getName() + " §eis tracking §2" + t.trackingType.toString().toLowerCase() + (t.distance == 0 ? "§e." : " §ewithin §2" + t.distance + " §eblocks."));
        });
    }
}
