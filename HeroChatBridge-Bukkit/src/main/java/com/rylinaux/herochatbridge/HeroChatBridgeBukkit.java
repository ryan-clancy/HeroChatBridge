package com.rylinaux.herochatbridge;

import com.rylinaux.herochatbridge.listeners.HeroChatListener;
import com.rylinaux.herochatbridge.listeners.HeroChatPluginMessageListener;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HeroChatBridgeBukkit extends JavaPlugin {

    @Getter
    private static Chat chat = null;

    @Getter
    private static String serverKey = null;

    @Getter
    private List<String> channels = null;

    @Getter
    private List<String> blackList = null;

    @Override
    public void onEnable() {

        if (!initChat()) {
            this.getServer().getLogger().log(Level.SEVERE, "Vault not installed, disabling.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        initConfig();

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

    private void initConfig() {
        this.getConfig().options().copyDefaults(true);
        serverKey = this.getConfig().getString("key");
        channels = this.getConfig().getStringList("channels");
        blackList = this.getConfig().getStringList("blacklist");
        this.saveConfig();
    }

    public boolean isBlacklisted(String channel) {
        return blackList.contains(channel);
    }

    public boolean isValidChannel(String channel) {
        return channels.contains(channel);
    }

}
