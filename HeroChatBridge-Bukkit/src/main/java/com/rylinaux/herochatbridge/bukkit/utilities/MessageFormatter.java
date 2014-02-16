package com.rylinaux.herochatbridge.bukkit.utilities;

import com.dthielke.herochat.Channel;

import org.bukkit.ChatColor;

public class MessageFormatter {

    public static String format(String sender, String message, String fromServer, String world, Channel channel) {
        String formatted = channel.getFormatSupplier().getStandardFormat();
        formatted = formatted.replace("{name}", channel.getName());
        formatted = formatted.replace("{nick}", channel.getNick() + "|" + fromServer);
        formatted = formatted.replace("{color}", channel.getColor().toString());
        formatted = formatted.replace("{msg}", message);
        formatted = formatted.replace("{sender}", VaultUtil.getPlayerName(sender));
        formatted = formatted.replace("{plainsender}", sender);
        formatted = formatted.replace("{world}", world);
        formatted = formatted.replace("{prefix}", VaultUtil.getPlayerPrefix(sender));
        formatted = formatted.replace("{suffix}", VaultUtil.getPlayerSuffix(sender));
        formatted = formatted.replace("{group}", VaultUtil.getGroup(sender));
        formatted = formatted.replace("{groupprefix}", VaultUtil.getGroupPrefix(sender));
        formatted = formatted.replace("{groupsuffix}", VaultUtil.getGroupSuffix(sender));
        return ChatColor.translateAlternateColorCodes('&', formatted);
    }

}
