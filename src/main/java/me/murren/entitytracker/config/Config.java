package me.murren.entitytracker.config;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public boolean ALLOW_NOTIFIERS;
    public boolean ALLOW_TRACKERS;
    public int MAX_NOTIFIERS;
    public int MAX_TRACKERS;

    public Config(FileConfiguration config)
    {
        ALLOW_NOTIFIERS = config.getBoolean("allow-notifiers");
        ALLOW_TRACKERS = config.getBoolean("allow-trackers");
        MAX_NOTIFIERS = config.getInt("maximum-notifiers");
        MAX_TRACKERS = config.getInt("maximum-trackers");
    }
}
