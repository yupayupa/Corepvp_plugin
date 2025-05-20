package me.haineko12.scoreboard_plugin;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Armor_manager {
    public static void give_ini(Player player){
        PlayerInventory inv= player.getInventory();

        ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemStack woodenPixel = new ItemStack(Material.WOODEN_PICKAXE,1);
        ItemStack woodenAxe = new ItemStack(Material.WOODEN_AXE,1);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        Color color = Color.RED;
        helmet = dyeArmor(helmet, color);
        chestplate = dyeArmor(chestplate, color);
        leggings = dyeArmor(leggings, color);
        boots = dyeArmor(boots, color);

        player.getInventory().addItem(woodenSword,woodenPixel,woodenAxe);
        inv.setHelmet(helmet);
        inv.setChestplate(chestplate);
        inv.setLeggings(leggings);
        inv.setBoots(boots);

    }

    private static ItemStack dyeArmor(ItemStack item, Color color) {
        if (item.getItemMeta() instanceof LeatherArmorMeta meta) {
            meta.setColor(color);
            item.setItemMeta(meta);
        }
        return item;
    }
}
