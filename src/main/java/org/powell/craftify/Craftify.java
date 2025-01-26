package org.powell.craftify;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Craftify extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("craftify").setExecutor(new CraftifyCommand());
        Bukkit.getPluginManager().registerEvents(new CraftifyListener(), this);

    }

}
