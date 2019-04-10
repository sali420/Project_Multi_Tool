package pmt.terramc.us;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class PlayTimeCMD implements CommandExecutor {

    private final Main plugin;

    public PlayTimeCMD(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("playtime").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            LocalDateTime defTime = LocalDateTime.parse("2019-01-01T00:00:00");

            if (args.length == 0) {
                LocalDateTime playTime = defTime.plusSeconds(player.getTicksLived() / 20);

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&3Total playtime&6: &a" + (playTime.getDayOfMonth() - 1)
                        + " &7days&6, &a" + playTime.getHour()
                        + " &7hours&6, &a" + playTime.getMinute()
                        + " &7minutes&6, &a" + playTime.getSecond()
                        + " &7seconds&6."));
            }
            else if (args.length == 1) {
                String targetStr = args[0];
                Player target = Bukkit.getPlayer(targetStr);
                if (target != null) {
                    LocalDateTime playTime = defTime.plusSeconds(target.getTicksLived() / 20);
                    String dispName = target.getDisplayName();

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&3Total playtime for " + dispName + "&6: &a" + (playTime.getDayOfMonth() - 1)
                            + " &7days&6, &a" + playTime.getHour()
                            + " &7hours&6, &a" + playTime.getMinute()
                            + " &7minutes&6, &a" + playTime.getSecond()
                            + " &7seconds&6."));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&cError: Could not obtain player."));
                }
            } else { player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.prefix + "&cError: Invalid argument(s)."));}
        } else {
            sender.sendMessage("Command only available to players.");
        }
        return true;
    }
}
