package me.murren.entitytracker.notifier;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NotifierManager implements Listener {
    public static List<Notifier> notifiers = new ArrayList<>();
    public static HashMap<UUID, Integer> counts = new HashMap<>();

    public static boolean addNotifier(Notifier notifier) {
        for (Notifier n : notifiers) {
            if (n.trackingType == notifier.trackingType && n.player == notifier.player) {
                return false;
            }
        }
        notifiers.add(notifier);
        counts.put(notifier.player.getUniqueId(), counts.getOrDefault(notifier.player.getUniqueId(), 0) + 1);
        return true;
    }

    public static boolean removeNotifier(Player p, EntityType et) {
        Notifier toRemove = null;
        for (Notifier n : notifiers) {
            if (n.trackingType == et && n.player == p) {
                toRemove = n;
                break;
            }
        }
        if (toRemove != null) {
            notifiers.remove(toRemove);
            counts.put(toRemove.player.getUniqueId(), counts.get(toRemove.player.getUniqueId()) - 1);
            return true;
        }
        return false;
    }

    public static void listNotifiers(Player p) {
        List<Notifier> listed = new ArrayList<>();
        for (Notifier n : notifiers) {
            if(n.player == p)
            {
                listed.add(n);
            }
        }
        if(listed.isEmpty())
        {
            p.sendMessage("§cYou have no notifiers.");
            return;
        }
        p.sendMessage("§eYou have §6" + notifiers.size() + " §enotifiers:");
        for(Notifier n : listed)
        {
            p.sendMessage("§eTracking " + n.trackingType.toString().toLowerCase() + (n.distance == 0 ? "." : " within " + n.distance + " blocks."));
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        notify(event);
    }

    public static void notify(EntitySpawnEvent event) {
        for (Notifier notifier : notifiers) {
            notifier.spawnEvent(event);
        }
    }

    public static void listAll(Player p)
    {
        if(notifiers.isEmpty())
        {
            p.sendMessage("§cThere are no notifiers.");
            return;
        }
        p.sendMessage("§eThere are §6" + notifiers.size() + " §enotifiers:");
        notifiers.forEach(n -> {
            p.sendMessage("§6"+ n.player.getName() + " §eis tracking §2" + n.trackingType.toString().toLowerCase() + (n.distance == 0 ? "§e." : " §ewithin §2" + n.distance + " §eblocks."));
        });
    }
}
