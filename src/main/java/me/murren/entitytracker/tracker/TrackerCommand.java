package me.murren.entitytracker.tracker;

import me.murren.entitytracker.EntityTracker;
import me.murren.entitytracker.notifier.NotifierManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TrackerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!EntityTracker.config().ALLOW_TRACKERS) {
            commandSender.sendMessage("§cTrackers are disabled!");
            return true;
        }

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        Player p = (Player) commandSender;
        if(args.length < 1) {
            p.sendMessage("§cUsage: /tracker <create/delete/list/log/listall> [entityType] [distance]");
            return true;
        }
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        switch(args[0].toLowerCase()) {
            case "create":
                createTracker(p, subArgs);
                break;
            case "delete":
                removeTracker(p, subArgs);
                break;
            case "list":
                TrackerManager.listTrackers(p);
                break;
            case "log":
                logTracker(p, subArgs);
                break;
            case "listall":
                listAllTrackers(p);
                break;
            default:
                p.sendMessage("§cUsage: /tracker <create/delete/list/log/listall> [entityType] [distance]");
                break;
        }
        return true;
    }

    private void createTracker(Player p, String[] args)
    {
        int maxtrackers = EntityTracker.config().MAX_TRACKERS;
        if(args.length == 0 || args.length > 2) {
            p.sendMessage("§cUsage: /tracker create <entityType> [distance]");
            return;
        }
        if(maxtrackers != 0 && TrackerManager.counts.getOrDefault(p.getUniqueId(), 0) >= maxtrackers && !p.hasPermission("entitytracker.bypasslimit"))
        {
            p.sendMessage("§cYou have reached the maximum number of trackers!");
            return;
        }
        EntityType et;
        try {
            et = EntityType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            p.sendMessage("§cInvalid entity type!");
            return;
        }
        int distance = args.length == 1 ? 0 : Integer.parseInt(args[1]);
        Tracker tracker = new Tracker(et, p, distance);
        if(TrackerManager.addTracker(tracker))
        {
            p.sendMessage("§aTracker created for entity type " + args[0] + (distance == 0 ? "." : " within " +  distance + " blocks of you."));
            return;
        }
        p.sendMessage("§aYou already have a tracker of entity type "+ args[0] +"!");
    }

    private void removeTracker(Player p, String[] args)
    {
        if(args.length != 1) {
            p.sendMessage("§cUsage: /tracker delete <entityType>");
            return;
        }
        EntityType et;
        try {
            et = EntityType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            p.sendMessage("§cInvalid entity type!");
            return;
        }
        boolean exists = TrackerManager.removeTracker(p, et);
        if(!exists) {
            p.sendMessage("§cYou don't have any trackers for entity type " + args[0] + "!");
            return;
        }
        p.sendMessage("§cTracker removed for entity type " + args[0] + ".");
    }

    private void logTracker(Player p, String[] args)
    {
        if(args.length != 1) {
            p.sendMessage("§cUsage: /tracker log <entityType>");
            return;
        }
        EntityType et;
        try {
            et = EntityType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            p.sendMessage("§cInvalid entity type!");
            return;
        }
        TrackerManager.LogTracker(p, et);
    }

    private void listAllTrackers(Player p) {
        if(!p.hasPermission("entitytracker.listall")) {
            p.sendMessage("§cYou don't have permission to use this command!");
            return;
        }
        TrackerManager.listAll(p);
    }
}
