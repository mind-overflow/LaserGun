package net.mindoverflow.lasergun.utils;

public enum ConfigEntries
{
    //HELP("help");
    RADIUS("radius"),

    DAMAGE("damage"),

    SOUND("sound"),

    UPDATE_CHECKER("update-checker");

    public String path;

    ConfigEntries(String path)
    {
        this.path = path;
    }
}
