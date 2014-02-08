package com.rylinaux.herochatbridge.bungee.listeners;

import com.rylinaux.herochatbridge.bungee.HeroChatBridgeBungee;

import java.net.InetSocketAddress;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {

    private final HeroChatBridgeBungee plugin;

    public PluginMessageListener(HeroChatBridgeBungee plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void receivePluginMessage(PluginMessageEvent event) {
        if (event.getTag().equalsIgnoreCase("HeroChatBridge")) {
            InetSocketAddress senderAddress = event.getSender().getAddress();
            for (ServerInfo server : plugin.getProxy().getServers().values()) {
                if ((server.getPlayers().size() != 0) && !(senderAddress.equals(server.getAddress()))) {
                    server.sendData("HeroChatBridge", event.getData());
                }
            }
        }
    }

}
