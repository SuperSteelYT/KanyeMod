package org.kanyecraft.kanyemod.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kanyecraft.kanyemod.admin.AdminList;
import org.kanyecraft.kanyemod.listener.Freeze;
import org.kanyecraft.kanyemod.rank.Rank;
import org.kanyecraft.kanyemod.util.KUtil;

@CommandParameters(name = "admin", description = "Manage the admins.", usage = "/<command> [add <player> | remove <player> | setrank <player> <rank>]", aliases = "saconfig", rank = Rank.ADMIN)
public class Command_admin extends KanyeCommand
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args)
    {
        switch (args.length)
        {
            case 2:
            {
                if (args[0].equalsIgnoreCase("add"))
                {
                    if (!isConsole(sender))
                    {
                        sender.sendMessage(onlyConsole);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null)
                    {
                        sender.sendMessage(playerNotFound);
                        return true;
                    }
                    if (AdminList.isImpostor(player))
                    {
                        action(sender.getName(), "Re-adding " + player.getName() + " to the admin list", ChatColor.RED);
                        AdminList.addIp(player, KUtil.getIp(player));
                        Freeze.unfreeze(player);
                        player.setOp(true);
                        player.sendMessage(opped);
                        return true;
                    }
                    if (AdminList.isAdmin(player))
                    {
                        sender.sendMessage(ChatColor.GRAY + "That player is already an admin.");
                        return true;
                    }
                    action(sender.getName(), "Adding " + player.getName() + " to the admin list", ChatColor.RED);
                    AdminList.addAdmin(player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("remove"))
                {
                    if (!isConsole(sender))
                    {
                        sender.sendMessage(onlyConsole);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null)
                    {
                        sender.sendMessage(playerNotFound);
                        return true;
                    }
                    if (!AdminList.isAdmin(player))
                    {
                        sender.sendMessage(ChatColor.GRAY + "That player is not an admin.");
                        return true;
                    }
                    action(sender.getName(), "Removing " + player.getName() + " from the admin list", ChatColor.RED);
                    AdminList.removeAdmin(player);
                    return true;
                }
            }
            case 3:
            {
                if (args[0].equalsIgnoreCase("setrank"))
                {
                    if (!isConsole(sender))
                    {
                        sender.sendMessage(onlyConsole);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null)
                    {
                        sender.sendMessage(playerNotFound);
                        return true;
                    }
                    if (!AdminList.isAdmin(player))
                    {
                        sender.sendMessage(ChatColor.GRAY + "That player is not an admin.");
                        return true;
                    }
                    Rank rank = Rank.findRank(args[2]);
                    if (rank == null)
                    {
                        sender.sendMessage(ChatColor.GRAY + "Cannot find that rank.");
                        return true;
                    }
                    if (rank.equals(Rank.CONSOLE))
                    {
                        sender.sendMessage(ChatColor.GRAY + "You cannot set a user to the console rank!");
                        return true;
                    }
                    action(sender.getName(), "Setting " + player.getName() + "'s rank to " + rank.getName(), ChatColor.RED);
                    AdminList.updateRank(player, rank);
                    return true;
                }
            }
        }
        return false;
    }
}
