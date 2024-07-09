package me.murren.entitytracker.tracker;

import me.murren.entitytracker.util.EntityTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrackerTabCompleter implements TabCompleter {

    private final List<String> operations = Arrays.asList("create", "delete", "list", "log", "listall");
    private final List<String> distance = Collections.singletonList("[distance]");

    private final List<String> empty = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        switch(args.length)
        {
            case 1:
                return operations;
            case 2:
                return EntityTypes.getAllEntityTypes();
            case 3:
                return args[0].equals("create") ? distance : empty;
        }
        return empty;
    }
}
