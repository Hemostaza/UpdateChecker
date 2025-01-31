package com.hemostaza.updateChecker;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

// From: https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates
public class UpdateChecker {

    private final JavaPlugin plugin;
    private final int resourceId;
    private String latestVersion;
    private final String currentVersion;
    private static UpdateChecker instance = null;

    private static boolean listenerAlreadyRegistered = false;

    {
        instance = this;
    }

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        currentVersion = plugin.getDescription().getVersion();
        this.resourceId = resourceId;

        if (!listenerAlreadyRegistered) {
            Bukkit.getPluginManager().registerEvents(new UpdateCheckerListener(), plugin);
            listenerAlreadyRegistered = true;
        }

        getVersion();

    }

    public static UpdateChecker getInstance(){
        return instance;
    }

    public void getVersion() {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream is = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId + "/~").openStream(); Scanner scann = new Scanner(is)) {
                if (scann.hasNext()) {
                    latestVersion = scann.next();
                }
            } catch (IOException e) {
                plugin.getLogger().info("Unable to check for updates: " + e.getMessage());
                latestVersion = "";
            }
            if(ResultMessage()==null){
                Bukkit.getLogger().info(plugin.getName()+" is in the latest version");
            }else Bukkit.getLogger().info(ResultMessage());
        });
    }

    public String ResultMessage(){
        if(!currentVersion.equals(latestVersion)) {
            return plugin.getName() + " has a newer version available.\nhttps://www.spigotmc.org/resources/" + plugin.getName() + "." + resourceId;
        }
        return null;
    }

}