import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class RankPlugin extends JavaPlugin implements Listener {
    private Map<String, ChatColor> rankColors;

    @Override
    public void onEnable() {
        // ลงทะเบียน Listener เพื่อรับเหตุการณ์เข้าร่วมของผู้เล่น
        getServer().getPluginManager().registerEvents(this, this);

        // กำหนดสีของยศ
        rankColors = new HashMap<>();
        rankColors.put("Admin", ChatColor.RED);
        rankColors.put("Member", ChatColor.GREEN);
        rankColors.put("Supreme", ChatColor.BLUE);
        rankColors.put("Legend", ChatColor.GOLD);
        rankColors.put("MVP", ChatColor.LIGHT_PURPLE);
        rankColors.put("VIP", ChatColor.YELLOW);
        rankColors.put("Ultra", ChatColor.AQUA);
        rankColors.put("Premium", ChatColor.DARK_GREEN);
        rankColors.put("SuperVIP", ChatColor.DARK_PURPLE);

        getLogger().info("Rank Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Rank Plugin has been disabled.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // เรียกใช้เมธอดที่กำหนดยศของผู้เล่นเมื่อเข้าสู่เซิร์ฟเวอร์
        setRank(player);
    }

    private void setRank(Player player) {
        String rank = getRank(player);
        ChatColor rankColor = rankColors.get(rank);

        // ตั้งค่าสีของชื่อผู้เล่น
        String displayName = rankColor + "[" + rank + "] " + player.getName();
        player.setDisplayName(displayName);

        // ส่งข้อความยินดีต้อนรับและแสดงสียศในแชท
        player.sendMessage(ChatColor.YELLOW + "Welcome to the server, " + rankColor + rank + " " + player.getName() + "!");
    }

    private String getRank(Player player) {
        // ทำโค้ดที่คืนค่ายศของผู้เล่นที่เกี่ยวข้องจากข้อมูลของคุณ
        // เช่น อ่านจากไฟล์ค่า หรือจากฐานข้อมูล

        // ตัวอย่างโค้ดที่คืนค่ายศตามชื่อผู้เล่นเพียงแค่ตัวอย่าง
        String playerName = player.getName();
        if (playerName.equalsIgnoreCase("admin123")) {
            return "Admin";
        } else if (playerName.equalsIgnoreCase("member456")) {
            return "Member";
        } else if (playerName.equalsIgnoreCase("supreme789")) {
            return "Supreme";
        } else if (playerName.equalsIgnoreCase("legend999")) {
            return "Legend";
        } else if (playerName.equalsIgnoreCase("mvp777")) {
            return "MVP";
        } else if (playerName.equalsIgnoreCase("vip123")) {
            return "VIP";
        } else if (playerName.equalsIgnoreCase("ultra456")) {
            return "Ultra";
        } else if (playerName.equalsIgnoreCase("premium789")) {
            return "Premium";
        } else if (playerName.equalsIgnoreCase("supervip999")) {
            return "SuperVIP";
        }

        // หากไม่พบยศใดที่ตรงกับชื่อผู้เล่น ให้คืนค่า "Member" เป็นค่าเริ่มต้น
        return "Member";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setRank")) {
            // ตรวจสอบว่าผู้ส่งคำสั่งเป็นผู้เล่นหรือไม่
            if (sender instanceof Player) {
                Player player = (Player) sender;

                // ตรวจสอบว่าผู้ส่งคำสั่งมีสิทธิ์ในการกำหนดยศหรือไม่
                if (player.hasPermission("rank.set")) {
                    // ตรวจสอบว่ามีอาร์กิวเมนต์เพียงพอหรือไม่
                    if (args.length == 2) {
                        String targetPlayerName = args[0];
                        String rank = args[1];

                        // ตรวจสอบว่ายศที่กำหนดถูกต้องหรือไม่
                        if (rankColors.containsKey(rank)) {
                            // ตรวจสอบว่าผู้เล่นที่ต้องการกำหนดยศให้มีออนไลน์หรือไม่
                            Player targetPlayer = Bukkit.getPlayerExact(targetPlayerName);
                            if (targetPlayer != null) {
                                // กำหนดยศให้กับผู้เล่น
                                setRank(targetPlayer, rank);

                                sender.sendMessage(ChatColor.GREEN + "Successfully set rank " + rank + " to player " + targetPlayerName);
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.RED + "Player " + targetPlayerName + " is not online.");
                                return true;
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Invalid rank. Available ranks are: Admin, Member, Supreme, Legend, MVP, VIP, Ultra, Premium, SuperVIP");
                            return true;
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage: /setRank <player> <rank>");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
                return true;
            }
        }

        return false;
    }
}
