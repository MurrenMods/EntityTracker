package me.murren.entitytracker.notifier;

import me.murren.entitytracker.EntityTracker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class NotifierCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!EntityTracker.config().ALLOW_NOTIFIERS) {
            commandSender.sendMessage("§cNotifiers are disabled!");
            return true;
        }

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        Player p = (Player) commandSender;
        if(args.length < 1) {
            p.sendMessage("§cUsage: /notifier <create/delete/list/listall> [entityType] [distance]");
            return true;
        }
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        switch(args[0].toLowerCase()) {
            case "create":
                createNotifier(p, subArgs);
                break;
            case "delete":
                removeNotifier(p, subArgs);
                break;
            case "list":
                NotifierManager.listNotifiers(p);
                break;
            case "listall":
                listAllNotifiers(p);
                break;
            default:
                p.sendMessage("§cUsage: /notifier <create/delete/list/listall> [entityType] [distance]");
                break;
        }
        return true;
    }

    private void createNotifier(Player p, String[] args)
    {
        int maxnotifs = EntityTracker.config().MAX_NOTIFIERS;
        if(args.length == 0 || args.length > 2) {
            p.sendMessage("§cUsage: /notifier create <entityType> [distance]");
            return;
        }
        if(maxnotifs != 0 && NotifierManager.counts.getOrDefault(p.getUniqueId(), 0) >= maxnotifs && !p.hasPermission("entitytracker.bypasslimit"))
        {
            p.sendMessage("§cYou have reached the maximum number of notifiers!");
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
        Notifier notifier = new Notifier(et, p, distance);
        if(NotifierManager.addNotifier(notifier))
        {
            p.sendMessage("§aNotifier created for entity type " + args[0] + (distance == 0 ? "." : " within " +  distance + " blocks of you."));
            return;
        }
        p.sendMessage("§aYou already have a notifier of entity type "+ args[0] +"!");
    }

    private void removeNotifier(Player p, String[] args)
    {
        if(args.length != 1) {
            p.sendMessage("§cUsage: /notifier delete <entityType>");
            return;
        }
        EntityType et;
        try {
            et = EntityType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            p.sendMessage("§cInvalid entity type!");
            return;
        }
        boolean exists = NotifierManager.removeNotifier(p, et);
        if(!exists) {
            p.sendMessage("§cYou don't have any notifiers for entity type " + args[0] + "!");
            return;
        }
        p.sendMessage("§cNotifier removed for entity type " + args[0] + ".");
    }

    private void listAllNotifiers(Player p) {
        if(!p.hasPermission("entitytracker.listall")) {
            p.sendMessage("§cYou don't have permission to use this command!");
            return;
        }
        NotifierManager.listAll(p);
    }
}
