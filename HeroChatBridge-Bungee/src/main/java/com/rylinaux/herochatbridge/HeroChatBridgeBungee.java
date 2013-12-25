package com.rylinaux.herochatbridge;

import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;

import com.rylinaux.herochatbridge.listeners.PluginMessageListener;

import java.util.List;

public class HeroChatBridgeBungee extends ConfigurablePlugin {

    private List<String> channels = null;

    public void onEnable() {
        initConfig();
        registerChannel();
        initListeners();
    }

    public void onDisable() {
        unregisterChannel();
    }

    private void initConfig() {
        this.saveDefaultConfig();
        channels = this.getConfig().getStringList("channels");
    }

    private void initListeners() {
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
    }

    public boolean isValidChannel(String channel) {
        return channels.contains(channel);
    }

    private void registerChannel() {
        this.getProxy().registerChannel("HeroChatBridge");
    }

    private void unregisterChannel() {
        this.getProxy().unregisterChannel("HeroChatBridge");
    }

}