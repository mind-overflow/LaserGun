# LaserGun Plugin

This is a very basic Spigot plugin that adds a laser gun in Minecraft.
**The plugin depends on [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)**.

## Commands
- /lasergun: see basic plugin info.
- /lasergun help: see the commands list.
- /lasergun get: add the gun to your inventory!
- /lasergun reload: reload the config.

## Permissions
- lasergun.command.getgun: get the gun!
- lasergun.usegun: fire the gun!
- lasergun.command.reload: reload the config.
- lasergun.updates: get updates notifications.

## Config
```yaml
radius: 10 # maximum shooting range
damage: 6.0 # lasergun projectile damage
sound: true # play a sound when shooting?
update-checker: true # enable updates checking?
```