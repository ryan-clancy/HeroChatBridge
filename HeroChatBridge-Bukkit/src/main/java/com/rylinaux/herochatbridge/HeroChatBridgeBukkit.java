package com.rylinaux.herochatbridge;

import com.rylinaux.herochatbridge.listeners.HeroChatListener;
import com.rylinaux.herochatbridge.listeners.HeroChatPluginMessageListener;

import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HeroChatBridgeBukkit extends JavaPlugin {

    private static Chat chat = null;

    @Override
    public void onEnable() {
        if (!initChat()) {
            this.getServer().getLogger().log(Level.SEVERE, "Vault not installed, disabling.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.getServer().getPluginManager().registerEvents(new HeroChatListener(this), this);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "HeroChatBridge", new HeroChatPluginMessageListener(this));
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "HeroChatBridge");
    }

    private boolean initChat() {
        RegisteredServiceProvider<Chat> chatProvider = this.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null)
            chat = chatProvider.getProvider();
        return (chat != null);
    }

    public static Chat getChat() {
        return chat;
    }

}
