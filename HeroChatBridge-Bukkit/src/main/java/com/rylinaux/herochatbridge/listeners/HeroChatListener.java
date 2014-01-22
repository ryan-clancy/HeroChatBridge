package com.rylinaux.herochatbridge.listeners;

import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import com.rylinaux.herochatbridge.HeroChatBridgeBukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class HeroChatListener implements Listener {

    private final HeroChatBridgeBukkit plugin;

    public HeroChatListener(HeroChatBridgeBukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChannelChat(ChannelChatEvent event) {
        // Make sure the chat is allowed and it isn't a PM
        if ((event.getResult() == Chatter.Result.ALLOWED)) {
            if (!(event.getFormat().equalsIgnoreCase(Herochat.getChannelManager().getConversationFormat()))) {
                String channel = event.getChannel().getName();
                if (plugin.isValidChannel(channel)) {
                    String player = event.getSender().getName();
                    String message = event.getMessage();
                    transmit(channel, player, message);
                }
            }
        }
    }

    private void transmit(String channel, String player, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel);
        out.writeUTF(player);
        out.writeUTF(message);
        out.writeUTF(plugin.getServerKey());
        plugin.getServer().sendPluginMessage(plugin, "HeroChatBridge", out.toByteArray());
    }

}
