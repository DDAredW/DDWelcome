package lunadev.main;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class welcomemessage extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info(ChatColor.GREEN + "WelcomeMessage enabled! 0_0");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "WelcomeMessage disabled! ^_^ ");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinMessage = getConfig().getString("joinMessage").replace("%player%", event.getPlayer().getName());
        joinMessage = ChatColor.translateAlternateColorCodes('&', joinMessage);
        event.getPlayer().sendMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String quitMessage = getConfig().getString("quitMessage").replace("%player%", event.getPlayer().getName());
        quitMessage = ChatColor.translateAlternateColorCodes('&', quitMessage);
        getServer().broadcastMessage(quitMessage);
    }
}
