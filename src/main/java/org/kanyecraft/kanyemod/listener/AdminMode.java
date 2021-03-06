package org.kanyecraft.kanyemod.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.kanyecraft.kanyemod.KanyeMod;
import org.kanyecraft.kanyemod.admin.AdminList;

public class AdminMode implements Listener
{
    private KanyeMod plugin;
    public AdminMode(KanyeMod plugin)
    {
        this.plugin = plugin;
    }

    private static boolean ADMINMODE = false;

    public static void enable()
    {
        ADMINMODE = true;
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (!AdminList.isAdmin(player))
            {
                player.kickPlayer(ChatColor.RED + "You were kicked since AdminMode was enabled.");
            }
        }
    }

    public static void disable()
    {
        ADMINMODE = false;
    }

    public static boolean isAdminModeEnabled()
    {
        return ADMINMODE;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e)
    {
        Player player = e.getPlayer();
        if (isAdminModeEnabled() && !AdminList.isAdmin(player))
        {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Only admins can join the server at this time.");
        }
    }
}
