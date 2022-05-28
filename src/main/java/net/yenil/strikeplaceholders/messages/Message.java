package net.yenil.strikeplaceholders.messages;

import net.yenil.strikeplaceholders.Main;
import org.bukkit.ChatColor;

public enum Message {
    // shared placeholders
    INVALID_PLACEHOLDER("&cINVALID PLACEHOLDER", "message.invalid-placeholder"),
    INVALID_KIT("&cKIT DOESN'T EXIST", "message.invalid-kit"),
    INVALID_PARTY("&cINVALID PARTY", "message.invalid-prty"),

    // specified placeholders
    PARTYMEMBER_INVALID_PLAYER("", "message.party-member.invalid-player");

    private final String defaultMessage, path;

    Message(String defaultMessage, String path) {
        this.defaultMessage = defaultMessage;
        this.path = path;
    }

    @Override
    public String toString() {
        String message = Main.getInstance().getConfig().getString(path);
        if (message == null) {
            Main.getInstance().debug("Message in " + path + " is not set.");
            return translate(defaultMessage);
        }
        return translate(message);
    }

    private String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}