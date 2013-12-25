package com.rylinaux.herochatbridge.listeners;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import com.rylinaux.herochatbridge.HeroChatBridgeBungee;

import java.net.InetSocketAddress;

public class PluginMessageListener implements Listener {

    private final HeroChatBridgeBungee plugin;

    public PluginMessageListener(HeroChatBridgeBungee plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void receivePluginMessage(PluginMessageEvent event) {
        if (event.getTag().equalsIgnoreCase("HeroChatBridge")) {
            String channel = ByteStreams.newDataInput(event.getData()).readUTF();
            if (plugin.isValidChannel(channel)) {
                InetSocketAddress address = event.getSender().getAddress();
                for (ServerInfo server : plugin.getProxy().getServers().values()) {
                    if (server.getPlayers().size() != 0) {
                        server.sendData("HeroChatBridge", getModifiedByteStream(event.getData(), server.getName()));
                    }
                }
            }
        }
    }

    /**
     * Takes the byte array recieved by the event and appends the server symbols.
     *
     * @param array the original array
     * @return a byte array with the server's symbol injected
     */
    private byte[] getModifiedByteStream(byte[] array, String toServer) {

        ByteArrayDataInput input = ByteStreams.newDataInput(array);
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        // Channel, Player, Message, From Server, To Server
        String[] info = new String[5];
        info[0] = input.readUTF();
        info[1] = input.readUTF();
        info[2] = input.readUTF();
        info[3] = plugin.getProxy().getPlayer(info[1]).getServer().getInfo().getName().substring(0, 1).toUpperCase();
        info[4] = toServer.substring(0, 1).toUpperCase();

        for (int i = 0; i < info.length; i++) {
            output.writeUTF(info[i]);
        }

        return output.toByteArray();

    }

}
