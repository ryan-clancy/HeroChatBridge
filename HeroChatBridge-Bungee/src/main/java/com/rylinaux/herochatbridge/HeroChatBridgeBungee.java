package com.rylinaux.herochatbridge;

import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;

import com.rylinaux.herochatbridge.listeners.PluginMessageListener;

public class HeroChatBridgeBungee extends ConfigurablePlugin {

    public void onEnable() {
        this.getProxy().registerChannel("HeroChatBridge");
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
    }

}