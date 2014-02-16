package com.rylinaux.herochatbridge.bungee;

import com.rylinaux.herochatbridge.bungee.listeners.PluginMessageListener;

import net.md_5.bungee.api.plugin.Plugin;

public class HeroChatBridgeBungee extends Plugin {

    public static final String CHANNEL = "HeroChatBridge";

    @Override
    public void onEnable() {
        this.getProxy().registerChannel(CHANNEL);
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
    }

    @Override
    public void onDisable() {
        this.getProxy().unregisterChannel(CHANNEL);
        this.getProxy().getPluginManager().unregisterListeners(this);
    }

}