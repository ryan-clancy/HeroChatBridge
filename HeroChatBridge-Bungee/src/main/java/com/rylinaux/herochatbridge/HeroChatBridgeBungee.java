package com.rylinaux.herochatbridge;

import net.md_5.bungee.api.plugin.Plugin;

import com.rylinaux.herochatbridge.listeners.PluginMessageListener;

public class HeroChatBridgeBungee extends Plugin {

    public void onEnable() {
        this.getProxy().registerChannel("HeroChatBridge");
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
    }

}