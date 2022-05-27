package net.yenil.strikeplaceholders;

import lombok.Getter;
import org.bukkit.plugin.java.*;

public class Main extends JavaPlugin
{

    @Getter
    public static Main instance;

    public void onEnable() {
        instance = this;
        new PlaceholderManager().register();
    }
}
