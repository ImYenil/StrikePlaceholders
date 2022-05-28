package net.yenil.strikeplaceholders;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.party.Party;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.yenil.strikeplaceholders.messages.Message;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion {
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

    public String onPlaceholderRequest(Player player, @NotNull String placeholder) {
        if (player == null) {
            return "";
        }
        String kit;
        int count;

        /* inQueueCount */
        if (placeholder.startsWith("inqueuecount_")) {
            kit = placeholder.replace("inqueuecount_", "");
            if (Main.getInstance().getPlayerAmountQueue().containsKey(kit)) {
                count = Main.getInstance().getPlayerAmountQueue().get(kit);
                return String.valueOf(count);
            } else return Message.INVALID_KIT.toString();
        }

        /* inFightCount */
        if (placeholder.startsWith("infightcount_")) {
            kit = placeholder.replace("infightcount_", "");
            if (Main.getInstance().getPlayerAmountFight().containsKey(kit)) {
                count = Main.getInstance().getPlayerAmountFight().get(kit);
                return String.valueOf(count);
            } else return Message.INVALID_KIT.toString();
        }

        /* partyMember */
        if (placeholder.startsWith("partymember_")) {
            StrikePracticeAPI api = StrikePractice.getAPI();
            Party party = api.getParty(player);
            if (party == null) {
                return Message.INVALID_PARTY.toString();
            }
            String stringnumber = placeholder.replace("partymember_", "");
            int number;
            try {
                number = Integer.parseInt(stringnumber);
            } catch (NumberFormatException ignored) {
                Main.getInstance().debug("%SEP_" + placeholder + "% has in invalid int");
                return "Invalid Party Number " + stringnumber;
            }

            if (party.getMembersNames().size() > number) {
                return (String) party.getMembersNames().toArray()[number - 1];
            }
            return Message.PARTYMEMBER_INVALID_PLAYER.toString();
        }

        return Message.INVALID_PLACEHOLDER.toString();
    }
}