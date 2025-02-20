package org.powell.craftify;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class CraftifyListener implements Listener {
    private File file;
    private Craftify main;
    private YamlConfiguration config;

    private String key;
    private String ingredient;
    private String output;

    public CraftifyListener(Craftify main, File file, YamlConfiguration config) {
        this.main = main;
        this.file = file;
        this.config = config;

        key = main.getConfig().getConfigurationSection("Crafting_Recipes.recipes").getKeys(false).toString();
        ingredient = config.getString("Crafting_Recipes.recipes." + key + ".ingredient");
        output = config.getString("Crafting_Recipes.recipes." + key + ".output");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getView().getTitle().equals(ChatColor.DARK_AQUA + "Craftify Menu") && e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()) {
                case 0:
                    e.setCancelled(true);
                    break;
                case 44:
                    e.setCancelled(true);
                    ItemStack[] contents = e.getInventory().getContents();
                    ItemStack item = contents[24];
                    if (item != null) {
                        Random rand = new Random();
                        int craft = rand.nextInt(1000000001);
                        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(main, "craft" + craft), item);
                            if (contents[10] != null) { recipe.setIngredient('A', contents[10].getType()); } else {
                                recipe.shape(
                                        " BC",
                                        "DEF",
                                        "GHI");
                            }
                            if (contents[11] != null) { recipe.setIngredient('B', contents[11].getType()); } else {
                                recipe.shape(
                                        "A C",
                                        "DEF",
                                        "GHI");
                            }
                            if (contents[12] != null) { recipe.setIngredient('C', contents[12].getType()); } else {
                                recipe.shape(
                                        "AB ",
                                        "DEF",
                                        "GHI");
                            }
                            if (contents[19] != null) { recipe.setIngredient('D', contents[19].getType()); } else {
                                recipe.shape(
                                        "ABC",
                                        " EF",
                                        "GHI");
                            }
                            if (contents[20] != null) { recipe.setIngredient('E', contents[20].getType()); } else {
                                recipe.shape(
                                        "ABC",
                                        "D F",
                                        "GHI");
                            }
                            if (contents[21] != null) { recipe.setIngredient('F', contents[21].getType()); } else {
                                recipe.shape(
                                        "ABC",
                                        "DE ",
                                        "GHI");
                            }
                            if (contents[28] != null) { recipe.setIngredient('G', contents[28].getType()); } else {
                                recipe.shape(
                                        "ABC",
                                        "DEF",
                                        " HI");
                            }
                            if (contents[29] != null) { recipe.setIngredient('H', contents[29].getType()); } else {
                                recipe.shape(
                                        "ABC",
                                        "DEF",
                                        "G I");
                            }
                            if (contents[30] != null) { recipe.setIngredient('I', contents[30].getType()); } else {
                                recipe.shape(
                                        "ABC",
                                        "DEF",
                                        "GH ");
                        }
                            try {
                                config = YamlConfiguration.loadConfiguration(file);

                                Bukkit.addRecipe(recipe);
                                config.set("Crafting_Recipes.recipes." + recipe.getKey(), recipe.getShape());
                                config.set("Crafting_Recipes.recipes." + recipe.getKey() + ".ingredient", recipe.getIngredientMap());
                                config.set("Crafting_Recipes.recipes." + recipe.getKey() + ".output", item.getType().toString());

                                try {
                                    config.save(file);
                                } catch (IOException i) {
                                    i.printStackTrace();
                                }
                                player.sendMessage(ChatColor.DARK_AQUA + "Recipe Added.");
                            } catch (IndexOutOfBoundsException i){
                                player.sendMessage(ChatColor.RED + "You need an ingredient.");
                            }
                    } else {
                        player.sendMessage(ChatColor.RED + "You need an output.");
                    }
                    break;
                    case 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 13, 22, 31, 14, 32, 33, 23, 15, 16, 25, 34:
                        e.setCancelled(true);
                default:
                    return;
            }
            player.closeInventory();
        }
    }

    public String getKey() { return key; }
    public String ingredient() { return ingredient; }
    public String output() { return output; }
}
