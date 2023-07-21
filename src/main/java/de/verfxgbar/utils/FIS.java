package de.verfxgbar.utils;

import de.verfxgbar.commands.CMD_fis;
import de.verfxgbar.listener.Join_Leave;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FIS extends JavaPlugin {

    private static FIS instance;

    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new Join_Leave(), this);
        getCommand("fis").setExecutor(new CMD_fis());

        FileManager.createFiles();
    }

    public final static FIS getInstance() {
        return instance;
    }
}
