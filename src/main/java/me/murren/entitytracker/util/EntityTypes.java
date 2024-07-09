package me.murren.entitytracker.util;

import java.util.ArrayList;
import java.util.List;

public class EntityTypes {
    private static List<String> entityTypes = null;
    public static List<String> getAllEntityTypes()
    {
        if(entityTypes == null)
        {
            entityTypes = new ArrayList<>();
            for (org.bukkit.entity.EntityType entityType : org.bukkit.entity.EntityType.values()) {
                entityTypes.add(entityType.toString().toLowerCase());
            }
        }
        return entityTypes;
    }
}
