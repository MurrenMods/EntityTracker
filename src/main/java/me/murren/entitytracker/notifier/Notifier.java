package me.murren.entitytracker.notifier;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntitySpawnEvent;

public class Notifier {
    public EntityType trackingType;
    public Player player;
    public int distance;

    public Notifier(EntityType trackingType, Player player, int distance) {
        this.trackingType = trackingType;
        this.player = player;
        this.distance = distance;
    }

    public void spawnEvent(EntitySpawnEvent event) {
        if (event.getEntity().getType() == trackingType) {
            if (distance == 0 || (event.getLocation().getWorld() == player.getLocation().getWorld() && event.getLocation().distance(player.getLocation()) <= distance)) {
                player.sendMessage("§bEntity of type §e§o" + trackingType + " §bspawned!");
                player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1, 1);
            }
        }
    }
}
