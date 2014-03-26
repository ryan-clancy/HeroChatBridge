package com.rylinaux.herochatbridge.bukkit;

import net.milkbowl.vault.chat.Chat;

import com.rylinaux.herochatbridge.bukkit.listeners.HeroChatListener;
import com.rylinaux.herochatbridge.bukkit.listeners.HeroChatPluginMessageListener;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HeroChatBridgeBukkit extends JavaPlugin {

    public static final String CHANNEL = "HeroChatBridge";

    private static Chat chat = null;

    private String serverKey = null;

    private List<String> channels = null;

    private List<String> ignored = null;

    @Override
    public void onEnable() {

        if (!initChat()) {
            this.getServer().getLogger().log(Level.SEVERE, "Vault not installed, disabling.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        initConfig();

        this.getServer().getPluginManager().registerEvents(new HeroChatListener(this), this);

        this.getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL, new HeroChatPluginMessageListener(this));
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL);

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
        ignored = this.getConfig().getStringList("ignored");
        this.saveConfig();
    }

    public boolean isIgnored(String channel) {
        return ignored.contains(channel);
    }

    public boolean isValidChannel(String channel) {
        return channels.contains(channel);
    }

    public static Chat getChat() {
        return chat;
    }

    public String getServerKey() {
        return serverKey;
    }

    public List<String> getChannels() {
        return channels;
    }

    public List<String> getIgnored() {
        return ignored;
    }

}
