package net.mindoverflow.lasergun.utils;

public enum LocalizedMessages {


    ERROR_NO_PERMISSIONS("error.no_permissions"),

    ERROR_CONSOLE_ACCESS_BLOCKED("error.console_access_blocked"),

    LASERGUN_NAME("info.lasergun_name"),

    LASERGUN_LORE("info.lasergun_lore");

    public String path;

    LocalizedMessages(String path)
    {
        this.path = path;
    }
}
