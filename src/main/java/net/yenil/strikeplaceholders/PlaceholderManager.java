package net.yenil.strikeplaceholders;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion
{
    @NotNull
    public String getAuthor() {
        return Main.getInstance().getDescription().getAuthors().toString();
    }

    public boolean persist() {
        return true;
    }

    @NotNull
    public String getIdentifier() {
        return "SPP";
    }

    @NotNull
    public String getVersion() {
        return Main.getInstance().getDescription().getVersion();
    }

    public String onPlaceholderRequest(Player p, @NotNull String i) {
        if (p == null) {
            return "";
        }
        if (i.startsWith("queuecount_")) {
            String kit = i.split("_")[1];
            int c = 0;
            StrikePracticeAPI api = StrikePractice.getAPI();
            for (Player on : Bukkit.getOnlinePlayers()) {
                if (api.isInQueue(on) && api.getQueuedKit(on).getName().equalsIgnoreCase(kit)) {
                    ++c;
                }
            }
            return String.valueOf(c);
        }
        if (i.startsWith("infightcount_")) {
            String kit = i.split("_")[1];
            int c = 0;
            StrikePracticeAPI api = StrikePractice.getAPI();
            for (Player on : Bukkit.getOnlinePlayers()) {
                if (api.isInFight(on) && api.getFight(on).getKit().getName().equalsIgnoreCase(kit)) {
                    ++c;
                }
            }
            return String.valueOf(c);
        }
        return null;
    }
}