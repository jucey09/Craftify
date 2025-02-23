package org.powell.craftify;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Craftify extends JavaPlugin implements Listener {
private CraftifyListener craftifyListener;
private Craftify main;
private File file;
private YamlConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);

        main = this;
        craftifyListener = new CraftifyListener(this, file, config);

        try {
            loadConfig("recipes.yml", main);
            loadRecipes(config);
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Craftify Recipes Loaded!");
        } catch (Exception e) {
            e.printStackTrace();
        }


        getCommand("craftify").setExecutor(new CraftifyCommand());
        getCommand("clr").setExecutor(new RecipeCommand(this));

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

    }
    public void loadRecipes(YamlConfiguration config) {
        FileConfiguration yml = config;
        if (yml.getConfigurationSection("Crafting_Recipes") != null) {
            for (String recipeKey : yml.getConfigurationSection("Crafting_Recipes").getKeys(false)) {
                ItemStack[] ingredients = loadIngredients(yml, recipeKey);
                ItemStack result = loadResult(yml, recipeKey);

                if (ingredients.length > 0 && result != null) {
                    ShapedRecipe shapedRecipe = createShapedRecipe(result, ingredients);
                    Bukkit.addRecipe(shapedRecipe);
                } else {
                    System.out.println("Invalid recipe or missing ingredients for: " + recipeKey);
                }
            }
        } else {
            System.out.println("No Recipes Found");
        }
    }

    private ItemStack[] loadIngredients(FileConfiguration config, String recipeKey) {
        ItemStack[] ingredients = new ItemStack[9];
        for (char row = 'A'; row <= 'I'; row++) {
            String ingredientPath = "Crafting_Recipes." + recipeKey + ".ingredient." + row;
            Object ingredient = config.get(ingredientPath);

            if (ingredient instanceof ItemStack) {
                ingredients[row - 'A'] = (ItemStack) ingredient;
            } else if (ingredient instanceof String) {
                String itemType = (String) ingredient;
                if (itemType != null) {
                    Material material = Material.getMaterial(itemType);
                    if (material != null) {
                        ingredients[row - 'A'] = new ItemStack(material);
                    } else {
                        getLogger().warning("Invalid item type in ingredient " + row + ": " + itemType);
                    }
                }
            } else {
                ingredients[row - 'A'] = null;
            }
        }

        return ingredients;
    }


    private ItemStack loadResult(FileConfiguration config, String recipeKey) {
        String resultPath = "Crafting_Recipes." + recipeKey + ".output";
        String resultType = config.getString(resultPath);
        if (resultType != null) {
            Material resultMaterial = Material.getMaterial(resultType);
            if (resultMaterial != null) {
                return new ItemStack(resultMaterial);
            } else {
                getLogger().warning("Invalid result item type: " + resultType);
            }
        }
        return null;
    }

    private ShapedRecipe createShapedRecipe(ItemStack result, ItemStack[] ingredients) {
        ShapedRecipe recipe = new ShapedRecipe(result);
        recipe.shape("ABC", "DEF", "GHI");

        for (int i = 0; i < 9; i++) {
            if (ingredients[i] != null) {
                recipe.setIngredient((char) ('A' + i), ingredients[i].getType());
            }
        }

        return recipe;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        loadRecipes(config);
        Bukkit.getScheduler().runTaskLater(this, () -> {
            player.performCommand("clr");
            player.sendMessage(ChatColor.DARK_AQUA + "Your custom recipes have been added!");
        }, 20L);

    }

    @Override
    public YamlConfiguration getConfig() { return config; }
}
