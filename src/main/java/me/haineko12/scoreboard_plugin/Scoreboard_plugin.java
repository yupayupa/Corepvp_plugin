package me.haineko12.scoreboard_plugin;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;




public final class Scoreboard_plugin extends JavaPlugin implements Listener {

    private ScoreboardManager manager;
    private Scoreboard board;
    Objective objective;

    public Location redCoreLocation;
    public Location blueCoreLocation;







    @Override
    public void onEnable() {
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();

        Team team = board.registerNewTeam("red");
        Team team2 = board.registerNewTeam("blue");

        objective = board.registerNewObjective("test", Criteria.DUMMY, "Test Scoreboard", RenderType.INTEGER);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore("Red Team");
        score.setScore(300);

        Score scoreBlue = objective.getScore("Blue Team");
        scoreBlue.setScore(300);



        if (manager == null){
            Bukkit.getLogger().warning("マネージャーを取得できませんでした");
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(board);
        }

        //objective = CreateScoreboard.CreateScoreboard();
        getServer().getPluginManager().registerEvents(this, this);

        //コマンド登録
        this.getCommand("coreset").setExecutor(new Coreset(this));


        new BukkitRunnable() {
            @Override
            public void run() {
                Score score =objective.getScore("Red Team");
                int currentScore = score.getScore();
                if(currentScore==0){
                    
                }
            }

        }.runTaskTimer(this,0L, 1L);

        Bukkit.getLogger().info("正常にプラグインが導入されています");
    }



    //プレイヤー参加に対するスコアボード作成
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(board);
        }


        if (board == null){
            Bukkit.getLogger().info("ボードが存在しません");
        }
        Player player = event.getPlayer();
        player.setScoreboard(board);
    }



    public void setRedCoreLocation(Location location){
        this.redCoreLocation = location;
    }

    public void setBlueCoreLocation(Location location){
        this.blueCoreLocation = location;
    }



    //コア破壊によるスコア減少後エンドストーン復活
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        Location loc = b.getLocation();

        if (b.getType() == Material.END_STONE) {
            if (loc.equals(redCoreLocation)) {
                Point_operate.Decrease_score("Red Team", objective);
                e.setCancelled(true);
            }

            if (loc.equals(blueCoreLocation)) {
                Point_operate.Decrease_score("Blue Team", objective);
                e.setCancelled(true);
            }
        }
    }




    @Override
    public void onDisable() {
        //Plugin shutdown logic
        if (board != null) {
            board.clearSlot(DisplaySlot.SIDEBAR);
        }
    }
}
