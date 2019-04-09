package pmt.terramc.us;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.List;

public class RepairTool implements CommandExecutor {
    private final Main plugin;

    public RepairTool(Main plugin) { // registering our command
        this.plugin = plugin;
        plugin.getCommand("repairme").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack repairCoin = new ItemStack(Material.EMERALD);
            List<String> coinLore = new ArrayList<>();
            coinLore.add("Good for a single item repair!");
            ItemMeta meta = repairCoin.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aRepair Coin"));
            meta.setLore(coinLore);
            meta.addEnchant(Enchantment.LOYALTY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            repairCoin.setItemMeta(meta);

            if (args.length == 0) {
                if (player.getInventory().containsAtLeast(repairCoin, 1)) {
                    ItemStack held = player.getInventory().getItemInMainHand();
                    Damageable _held = (Damageable) held.getItemMeta();
                    if (_held.hasDamage()) {
                        for (ItemStack i : player.getInventory().getContents()) {
                            if (i != null && i.isSimilar(repairCoin)) {
                                _held.setDamage(0);
                                held.setItemMeta((ItemMeta) _held);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&aItem repaired."));
                                i.setAmount(i.getAmount() - 1);
                            }
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&cError: Invalid item."));
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&cError: you need at least one Repair Coin to use this command."));
                }
            } else if (player.hasPermission("pmt.admin")) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&cError: enter an amount."));
                    } else if (args.length == 2) {
                        try {
                            String amtStr = args[1];
                            int amt = Integer.parseInt(amtStr);
                            ItemStack coins = repairCoin;
                            coins.setAmount(amt);
                            player.getInventory().addItem(coins);
                        } catch (NumberFormatException e) {player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&cError: invalid amount."));}
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&cError: Invalid argument(s)"));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&cError: You do not have permission to do this."));
            }
        }
        else {
            sender.sendMessage("Command only available to players.");
            return true;
        }
        return true;
    }
}