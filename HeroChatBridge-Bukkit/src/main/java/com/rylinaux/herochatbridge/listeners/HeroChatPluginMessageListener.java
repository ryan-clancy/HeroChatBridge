package com.rylinaux.herochatbridge.listeners;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.Herochat;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import com.rylinaux.herochatbridge.HeroChatBridgeBukkit;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class HeroChatPluginMessageListener implements PluginMessageListener {

    private final HeroChatBridgeBukkit plugin;

    public HeroChatPluginMessageListener(HeroChatBridgeBukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String header, Player player, byte[] bytes) {

        if (!(header.equalsIgnoreCase("HeroChatBridge"))) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

        String channelName = in.readUTF();

        Channel channel = Herochat.getChannelManager().getChannel(channelName);

        if (channel == null || !Herochat.getChannelManager().hasChannel(channelName)) {
            Bukkit.getServer().getLogger().log(Level.SEVERE, String.format("HeroChat channel %s does not exist - check your configuration.", channelName));
            return;
        }

        if (plugin.isBlacklisted(channelName)) {
            return;
        }

        String playerName = in.readUTF();
        String message = in.readUTF();
        String fromServer = in.readUTF();

        String nick = channel.getNick();
        String coloredName = getNameWithPrefix(playerName);

        ChatColor cColor = channel.getColor();

        channel.sendRawMessage(cColor + "[" + nick + ":" + fromServer + "] " + ChatColor.translateAlternateColorCodes('&', coloredName) + cColor + ": " + message);

    }

    public String getNameWithPrefix(String playerName) {
        return plugin.getChat().getPlayerPrefix(plugin.getServer().getWorlds().get(0), playerName) + playerName;
    }

}
