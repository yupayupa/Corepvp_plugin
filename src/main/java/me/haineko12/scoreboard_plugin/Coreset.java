package me.haineko12.scoreboard_plugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Coreset implements CommandExecutor {

    private final Scoreboard_plugin plugin;

    public Coreset(Scoreboard_plugin plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(!(sender instanceof Player)){
            sender.sendMessage("このコマンドはプレイヤーのみが使用できます");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 4) {
            player.sendMessage("引数が足りません");
            return true;
        }

        if(args[0].equals("red")) {
            int Red_X = Integer.parseInt(args[1]);
            int Red_Y = Integer.parseInt(args[2]);
            int Red_Z = Integer.parseInt(args[3]);

            Location coreRedLocation = new Location(player.getWorld(), Red_X, Red_Y, Red_Z);
            coreRedLocation.getBlock().setType(Material.END_STONE);

            plugin.setRedCoreLocation(coreRedLocation);
            player.sendMessage("赤コア設置完了");
            player.sendMessage(String.valueOf(Red_X) + " " + Red_Y + " " + Red_Z);
        }

        if(args[0].equals("blue")) {
            int Blue_X = Integer.parseInt(args[1]);
            int Blue_Y = Integer.parseInt(args[2]);
            int Blue_Z = Integer.parseInt(args[3]);

            Location coreBlueLocation = new Location(player.getWorld(), Blue_X, Blue_Y, Blue_Z);
            coreBlueLocation.getBlock().setType(Material.END_STONE);

            plugin.setBlueCoreLocation(coreBlueLocation);
            player.sendMessage("青コア設置完了");
            player.sendMessage(String.valueOf(Blue_X) + " " + Blue_Y + " " + Blue_Z);
        }

        return true;
    }
}
