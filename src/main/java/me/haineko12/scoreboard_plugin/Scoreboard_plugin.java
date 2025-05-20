package me.haineko12.scoreboard_plugin;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.checkerframework.checker.units.qual.A;


public final class Scoreboard_plugin extends JavaPlugin implements Listener {

    public ScoreboardManager manager;
    public Scoreboard board;
    Objective objective;


    public Team red_team;
    public Team blue_team;

    private static final String Teamname_red= "Red Team";
    private static final String Teamname_blue= "Blue Team";

    Score scoreRed;
    Score scoreBlue;

    public Location redCoreLocation;
    public Location blueCoreLocation;

    public static int GameFinished=0;

    Team_assign assign = new Team_assign(this);
    Armor_manager armor_manager = new Armor_manager();

    World target_world;
    Location lobby;





    @Override
    public void onEnable() {
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();

        target_world = Bukkit.getWorld("world_corepvp");
        lobby = new Location(Bukkit.getWorld("world_lobby"), 0,-58,0);



        //スコアボード作成
        if(red_team== null){
            red_team = board.registerNewTeam(Teamname_red);
            red_team.setPrefix(ChatColor.RED.toString());
            red_team.setSuffix(ChatColor.RESET.toString());
            red_team.setDisplayName("Red Team");
            red_team.setAllowFriendlyFire(false);
        }


        if(blue_team== null){
            blue_team = board.registerNewTeam(Teamname_blue);
            blue_team.setPrefix(ChatColor.BLUE.toString());
            blue_team.setSuffix(ChatColor.RESET.toString());
            blue_team.setDisplayName("Blue Team");
            blue_team.setAllowFriendlyFire(false);
        }

        objective = board.registerNewObjective("test", Criteria.DUMMY, "Test Scoreboard", RenderType.INTEGER);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        scoreRed = objective.getScore("Red Team");
        scoreBlue = objective.getScore("Blue Team");


        scoreRed.setScore(10);
        scoreBlue.setScore(10);



        if (manager == null){
            Bukkit.getLogger().warning("マネージャーを取得できませんでした");
        }

        //各プレイヤーに配置
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(board);
        }

        //プラグイン登録
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Team_assign(this), this);
        getServer().getPluginManager().registerEvents(new Player_Login(this),this);

        //コマンド登録
        this.getCommand("coreset").setExecutor(new Coreset(this));

        run();

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



    //コア位置代入
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
        Player player = e.getPlayer();
        Team breaker_team=null;

        for(Team team : board.getTeams()){
            if(team.hasEntry(player.getName())){
                breaker_team =team;
                break;
            }
        }

        if (b.getType() == Material.END_STONE) {
            if (loc.equals(redCoreLocation)&& breaker_team.equals(blue_team)) {
                Point_operate.Decrease_score("Red Team", objective);
            }

            else if (loc.equals(blueCoreLocation)&&breaker_team.equals(red_team)) {
                Point_operate.Decrease_score("Blue Team", objective);
            }

            else{
                Bukkit.broadcastMessage("味方のコアです");
            }

            e.setCancelled(true);
        }
    }




    //コアの体力が0になるとゲーム終了 終了判定
    public void run(){
        new BukkitRunnable(){
            @Override
            public void run(){
                Score RedScore =objective.getScore("Red Team");
                Score BlueScore =objective.getScore("Blue Team");

                int RedCurrentScore = RedScore.getScore();
                int BlueCurrentScore = BlueScore.getScore();


                if(RedCurrentScore <= 0 && GameFinished==0){
                    Bukkit.broadcastMessage("青チーム勝利");
                    GameFinished = 1;
                    assign.al_joined=0;

                    Bukkit.getScheduler().runTaskLater(getPlugin(Scoreboard_plugin.class), () ->{
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            if (player.getWorld().equals(target_world)) {
                                player.teleport(lobby);
                                red_team.removePlayer(player);
                                blue_team.removePlayer(player);
                            }
                        }
                        scoreRed.setScore(10);
                        scoreBlue.setScore(10);
                        GameFinished = 0;
                        },200L);


                }

                if(BlueCurrentScore <=0 && GameFinished==0){
                    Bukkit.broadcastMessage("赤チーム勝利");
                    GameFinished = 1;
                    assign.al_joined=0;

                    Bukkit.getScheduler().runTaskLater(getPlugin(Scoreboard_plugin.class), () ->{
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            if (player.getWorld().equals(target_world)) {
                                player.teleport(lobby);
                                red_team.removePlayer(player);
                                blue_team.removePlayer(player);
                            }
                        }
                        scoreRed.setScore(10);
                        scoreBlue.setScore(10);
                        GameFinished = 0;

                        },200L);


                }
            }
        }.runTaskTimer(this, 0L, 2L);
    }




    @Override
    public void onDisable() {
        //Plugin shutdown logic
        if (board != null) {
            board.clearSlot(DisplaySlot.SIDEBAR);
        }
    }
}
