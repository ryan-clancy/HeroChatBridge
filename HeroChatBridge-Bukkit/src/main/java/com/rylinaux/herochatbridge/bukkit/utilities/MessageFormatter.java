package com.rylinaux.herochatbridge.bukkit.utilities;

import com.dthielke.herochat.Channel;

import org.bukkit.ChatColor;

public class MessageFormatter {

    public static String format(String sender, String message, String fromServer, String world, Channel channel) {
        String formatted = channel.getFormatSupplier().getStandardFormat()
                .replace("{name}", channel.getName())
                .replace("{nick}", channel.getNick() + "|" + fromServer)
                .replace("{color}", channel.getColor().toString())
                .replace("{msg}", message)
                .replace("{sender}", VaultUtil.getPlayerName(sender))
                .replace("{plainsender}", sender)
                .replace("{world}", world)
                .replace("{prefix}", VaultUtil.getPlayerPrefix(sender))
                .replace("{suffix}", VaultUtil.getPlayerSuffix(sender))
                .replace("{group}", VaultUtil.getGroup(sender))
                .replace("{groupprefix}", VaultUtil.getGroupPrefix(sender))
                .replace("{groupsuffix}", VaultUtil.getGroupSuffix(sender));
        return ChatColor.translateAlternateColorCodes('&', formatted);
    }

}
