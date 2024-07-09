package me.murren.entitytracker.tracker;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Date;

public class Tracker {

    public EntityType trackingType;
    public Player player;
    public int distance;

    public int count = 0;
    public Date start = new Date();

    public Tracker(EntityType trackingType, Player player, int distance) {
        this.trackingType = trackingType;
        this.player = player;
        this.distance = distance;
    }

    public void spawnEvent(EntitySpawnEvent event) {
        if (event.getEntity().getType() == trackingType) {
            if (distance == 0 || (event.getLocation().getWorld() == player.getLocation().getWorld() && event.getLocation().distance(player.getLocation()) <= distance)) {
                count++;
            }
        }
    }
}
