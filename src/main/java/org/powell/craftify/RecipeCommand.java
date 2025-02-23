package org.powell.craftify;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RecipeCommand implements CommandExecutor {
    private Craftify craftify;

    public RecipeCommand(Craftify craftify) {
        this.craftify = craftify;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            FileConfiguration yml = craftify.getConfig();
            for (String recipeKey : yml.getConfigurationSection("Crafting_Recipes").getKeys(false)) {
                NamespacedKey key = new NamespacedKey("bukkit", recipeKey);
                p.sendMessage(key.toString());
                p.discoverRecipe(key);
            }
        }
            return false;
        }
}
