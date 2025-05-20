package me.haineko12.scoreboard_plugin;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class Team_assign implements Listener {
    private final Scoreboard_plugin plugin;
    public int al_joined=0;
    public static Team player_team;



    public Team_assign(Scoreboard_plugin main_plugin) {
        this.plugin=main_plugin;
    }



    //ゲーム参加時の処理
    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        World toWorld = player.getWorld();
        Team player_team=null;

        Team red_team = plugin.red_team;
        Team blue_team = plugin.blue_team;
        int red_size;
        int blue_size;

        Scoreboard board = plugin.board;



        for(Team team : board.getTeams()){
            if(team.hasEntry(player.getName())){
                player_team =team;
                break;
            }
        }

        //まだ参加していない場合の処理
        if(player_team == null){
            if(toWorld.getName().equals("world_corepvp")) {
                red_size = red_team.getSize();
                blue_size = blue_team.getSize();
                if(al_joined==0){

                    Armor_manager.give_ini(player);
                    al_joined=1;
                }

                if (red_size <= blue_size) {
                    red_team.addPlayer(player);
                    player.setPlayerListName(ChatColor.RED + player.getName());
                    player.sendMessage("あなたは赤チームです");
                } else {
                    blue_team.addPlayer(player);
                    player.setPlayerListName(ChatColor.BLUE + player.getName());
                    player.sendMessage("あなたは青チームです");
                }
            }

        }

    }



}
