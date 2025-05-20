package me.haineko12.scoreboard_plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

public final class Player_Login implements Listener {


    private final Scoreboard_plugin plugin;

    public Player_Login(Scoreboard_plugin main_plugin) {
        this.plugin=main_plugin;
    }

    @EventHandler
    public void AsyncPlayerPreLoginEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location loc = new Location(Bukkit.getWorld("world_lobby"), 0, -58, 0);
        player.teleport(loc);
    }
}
