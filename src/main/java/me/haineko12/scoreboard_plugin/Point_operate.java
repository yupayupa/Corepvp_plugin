package me.haineko12.scoreboard_plugin;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.*;


public class Point_operate {
    public static int currentScore;



    public static void Decrease_score(String teamName, Objective objective){

        if (objective == null) {
            Bukkit.getLogger().warning("オブジェクティブ 'score' が見つかりません。");
            return;
        }

        if (teamName == null){
            Bukkit.getLogger().warning("TeamNameが見つかりません。");
            return;
        }

        Score score =objective.getScore(teamName);
        currentScore = score.getScore();
        score.setScore(Math.max(currentScore - 1, 0));
    }
}
