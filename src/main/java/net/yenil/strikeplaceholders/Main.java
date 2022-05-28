package net.yenil.strikeplaceholders;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import lombok.Getter;
import net.yenil.strikeplaceholders.command.StrikePlaceholdersCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.*;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    @Getter
    public static Main instance;

    @Getter
    private LinkedHashMap<String, Integer> playerAmountQueue;

    @Getter
    private LinkedHashMap<String, Integer> playerAmountFight;

    int taskID;
    private boolean debug;

    @Override
    public void onEnable() {
        instance = this;
        this.playerAmountQueue = new LinkedHashMap<>();
        this.playerAmountFight = new LinkedHashMap<>();
        saveDefaultConfig();
        runTask();
        new PlaceholderManager().register();
        Objects.requireNonNull(Bukkit.getPluginCommand("strikeplaceholders")).setExecutor(new StrikePlaceholdersCommand());
        this.debug = getConfig().getBoolean("settings.debug");
    }

    @Override
    public void onDisable() {
        cancelTask();
    }

    private void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void reloadSystem() {
        cancelTask();
        runTask();
    }

    private void runTask() {
        StrikePracticeAPI api = StrikePractice.getAPI();
        playerAmountQueue.clear();
        playerAmountFight.clear();
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            api.getKits().forEach(kit -> {
                String kitName = kit.getName();
                int queueAmount = 0;
                int fightAmount = 0;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (api.isInFight(player) && api.getFight(player) != null) {
                        if (api.getFight(player).getKit().getName().equalsIgnoreCase(kitName))
                            fightAmount++;
                    }
                    if (api.isInQueue(player) && api.getQueuedKit(player) != null &&
                            api.getQueuedKit(player).getName().equalsIgnoreCase(kitName)) {
                        queueAmount++;
                    }
                }
                playerAmountQueue.put(kitName, queueAmount);
                playerAmountFight.put(kitName, fightAmount);
            });
        }, 0, 2 * 20);
        if (taskID == -1) runTask();
    }

    public void debug(String message) {
        if (!debug) return;
        getLogger().log(Level.INFO, message);
    }
}
