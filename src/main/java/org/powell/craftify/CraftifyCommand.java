package org.powell.craftify;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CraftifyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            if (commandSender instanceof Player){
                Player player = (Player) commandSender;

                Inventory inv = Bukkit.createInventory(player, 45, ChatColor.GOLD + "Craftify Menu");

                ItemStack close = new ItemStack(Material.BARRIER);
                ItemMeta closemeta = close.getItemMeta();
                closemeta.setDisplayName(ChatColor.RED + "Close");
                closemeta.setLore(Arrays.asList(" "));

                close.setItemMeta(closemeta);
                inv.setItem(0, close);

                ItemStack addRecipe = new ItemStack(Material.ARROW);
                ItemMeta addemeta = addRecipe.getItemMeta();
                addemeta.setDisplayName(ChatColor.DARK_GREEN + "Add Recipe");
                addemeta.setLore(Arrays.asList(" "));

                addRecipe.setItemMeta(addemeta);
                inv.setItem(44, addRecipe);


                ItemStack frame = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta fmeta = frame.getItemMeta();
                fmeta.setDisplayName(ChatColor.DARK_GRAY + "_");
                fmeta.setLore(Arrays.asList(" "));
                frame.setItemMeta(fmeta);

                for (int i: new int[]{1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,13,22,31,14,32,33,23,15,16,25,34}){
                    inv.setItem(i, frame);
                }
                ItemStack[] contents = inv.getContents();
                //10,11,12,19,20,21,28,29,30 output 24
                ShapedRecipe recipe = new ShapedRecipe(contents[24]);
                recipe.shape(
                        "ABC",
                        "DEF",
                        "GHI");

                recipe.setIngredient('A', contents[10].getType());
                recipe.setIngredient('B', contents[11].getType());
                recipe.setIngredient('C', contents[12].getType());
                recipe.setIngredient('D', contents[19].getType());
                recipe.setIngredient('E', contents[20].getType());
                recipe.setIngredient('F', contents[21].getType());
                recipe.setIngredient('G', contents[28].getType());
                recipe.setIngredient('H', contents[29].getType());
                recipe.setIngredient('I', contents[30].getType());




                player.openInventory(inv);
            }

        return false;
    }
}
