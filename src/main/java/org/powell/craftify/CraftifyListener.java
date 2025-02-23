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
    private NamespacedKey key;

    public CraftifyListener(Craftify main, File file, YamlConfiguration config) {
        this.main = main;
        this.file = file;
        this.config = config;

        key = NamespacedKey.randomKey();

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String cleanedKey = key.getKey().replace("bukkit:", "");
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
                        ShapedRecipe recipe = new ShapedRecipe(key, item);
                        recipe.shape(
                                "ABC",
                                "DEF",
                                "GHI");
                            if (contents[10] != null) { recipe.setIngredient('A', contents[10].getType()); }
                            if (contents[11] != null) { recipe.setIngredient('B', contents[11].getType()); }
                            if (contents[12] != null) { recipe.setIngredient('C', contents[12].getType()); }
                            if (contents[19] != null) { recipe.setIngredient('D', contents[19].getType()); }
                            if (contents[20] != null) { recipe.setIngredient('E', contents[20].getType()); }
                            if (contents[21] != null) { recipe.setIngredient('F', contents[21].getType()); }
                            if (contents[28] != null) { recipe.setIngredient('G', contents[28].getType()); }
                            if (contents[29] != null) { recipe.setIngredient('H', contents[29].getType()); }
                            if (contents[30] != null) { recipe.setIngredient('I', contents[30].getType()); }
                            try {
                                config = YamlConfiguration.loadConfiguration(file);

                                Bukkit.addRecipe(recipe);
                                System.out.println(key);

                                config.set("Crafting_Recipes." + cleanedKey + ".shape", recipe.getShape());
                                config.set("Crafting_Recipes." + cleanedKey + ".ingredient", recipe.getIngredientMap());
                                config.set("Crafting_Recipes." + cleanedKey + ".output", item.getType().toString());
                                for (Player p : Bukkit.getOnlinePlayers()){
                                    p.discoverRecipe(key);
                                }
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

}
