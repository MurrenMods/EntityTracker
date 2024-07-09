# Entity Tracker
A minecraft plugin to track entity spawns.

## Features
The plugin features two distinct functions for players to make. These do not persist through reloads, restarts or rejoins. They are player-specific, so multiple players can use it independently.
### Entity Notifiers
Entity notifiers can be created per entity type and a configurable radius. When a player creates a notifier, they will get a message and sound effect whenever an entity of the chosen type spawns within their radius (or serverwide when no radius is specified). 
### Entity Trackers
Entity tracker can be created per entity type and a configurable radius. When a player creates a tracker, it will get counted by the plugin when an entity of the chosen type spawns within their radius (or serverwide when no radius is specified). The statistics can then be retrieved of the tracker, which will tell you how many entities of the chosen type spawned, how long the tracker ran and what the hourly spawn rate of that entity was.

## Commands
### /notifier <create|delete|list|listall> \[entitytype] \[radius]
#### /notifier create <entitytype> \[radius]
Creates a notifier for the specified entity type and the given radius. Can also be used without specifying a radius.
#### /notifier delete <entitytype>
Deletes the player's notifier of the given type, if present.
#### /notifier list
Lists all notifiers the player currently has running.
#### /notifier listall
Lists all notifiers the plugin currently has running.

### /tracker <create/delete/list/log/listall> \[entityType] \[distance]
#### /tracker create <entitytype> \[radius]
Creates a tracker for the specified entity type and the given radius. Can also be used without specifying a radius.
#### /tracker delete <entitytype>
Logs the stats and deletes the player's tracker of the given type, if present.
#### /tracker list
Lists all trackers the player currently has running.
#### /tracker log <entitytype>
Logs the stats of the player's tracker of the given type, if present.
#### /tracker listall
Lists all trackers the plugin currently has running.

## Config
- allow-notifiers: Toggles if /notifier should be enabled.
- allow-trackers: Toggles if /tracker should be enabled.
- maximum-notifiers: Maximum amount of notifiers a player is allowed to have consecutively. (put 0 to remove the limit)
- maximum-trackers: Maximum amount of trackers a player is allowed to have consecutively. (put 0 to remove the limit)

## Permissions
- entitytracker.*: All permissions. Do not use for normal players!
- entitytracker.notifiers: Allow player to use /notifier (default on)
- entitytracker.trackers: Allow player to use /tracker (default on)
- entitytracker.bypasslimit: Allow player to bypass notifier and tracker limits. (default op only)
- entitytracker.listall: Allow player to list all trackers/notifiers. (default op only)
