package org.powell.craftify;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public final class Craftify extends JavaPlugin {
private CraftifyListener craftifyListener;
private Craftify main;
private File file;
private YamlConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        craftifyListener = new CraftifyListener(this, file, config);

        try {
            loadConfig("recipes.json", main);
            loadConfig("recipes.yml", main);
        } catch (Exception e) {
            e.printStackTrace();
        }


        getCommand("craftify").setExecutor(new CraftifyCommand());

        Bukkit.getPluginManager().registerEvents(new CraftifyListener(this, file, config), this);

    }

    public void loadConfig(String fileName, Craftify main) throws Exception {
        if (!main.getDataFolder().exists()){
            main.getDataFolder().mkdir();
        }
        file = new File(main.getDataFolder(), fileName);
        config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()){
            file.createNewFile();
        }
        Data data = new Data(craftifyListener.getKey(), craftifyListener.ingredient(), craftifyListener.output());
        try {
            Gson gson = new Gson();
            Writer writer = new FileWriter(file, false);
            gson.toJson(data, writer);
            writer.flush();
            writer.close();
            System.out.println("Saved Data!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
