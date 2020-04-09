package net.mindoverflow.lasergun.utils;

public enum Permissions
{
    ITEM_GET("lasergun.command.getgun"),

    ITEM_USE("lasergun.usegun"),

    COMMAND_RELOAD("lasergun.command.reload"),

    GET_UPDATES_NOTIFICATIONS("lasergun.updates"),

    ;

    public String permission;

    Permissions(String permission) { this.permission = permission; }
}
