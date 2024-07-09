package me.murren.entitytracker;

import me.murren.entitytracker.config.Config;
import me.murren.entitytracker.notifier.NotifierCommand;
import me.murren.entitytracker.notifier.NotifierManager;
import me.murren.entitytracker.notifier.NotifierTabCompleter;
import me.murren.entitytracker.tracker.TrackerCommand;
import me.murren.entitytracker.tracker.TrackerManager;
import me.murren.entitytracker.tracker.TrackerTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class EntityTracker extends JavaPlugin {

    private static Config config;
    public static Config config() {
        return config;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new Config(getConfig());

        RegisterManagers();
        RegisterCommands();
        getLogger().info("Plugin successfully enabled!");
    }

    private void RegisterManagers()
    {
        // Register managers here
        getServer().getPluginManager().registerEvents(new NotifierManager(), this);
        getServer().getPluginManager().registerEvents(new TrackerManager(), this);
    }

    private void RegisterCommands()
    {
        // Register commands here
        this.getCommand("notifier").setExecutor(new NotifierCommand());
        this.getCommand("notifier").setTabCompleter(new NotifierTabCompleter());
        this.getCommand("tracker").setExecutor(new TrackerCommand());
        this.getCommand("tracker").setTabCompleter(new TrackerTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
