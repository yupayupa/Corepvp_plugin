package me.haineko12.scoreboard_plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


import org.bukkit.scoreboard.*;


public class CreateScoreboard {

    private static Scoreboard board;

    public static Objective CreateScoreboard() {

        ScoreboardManager manager = Bukkit.getScoreboardManager();

        if (manager == null){
            Bukkit.getLogger().warning("マネージャーを取得できませんでした");
        }

        board = manager.getNewScoreboard();


        Team team = board.registerNewTeam("red");
        Team team2 = board.registerNewTeam("blue");


        Objective objective = board.registerNewObjective("test", Criteria.DUMMY, "Test Scoreboard", RenderType.INTEGER);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        Score score = objective.getScore("Red Team");
        score.setScore(300);

        Score scoreBlue = objective.getScore("Blue Team");
        scoreBlue.setScore(300);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(board);
        }

        Bukkit.getLogger().info("スコアボード作成完了");

        return objective;

    }
}