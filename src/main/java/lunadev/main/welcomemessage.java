package lunadev.main;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DDWelcome extends JavaPlugin implements Listener {

    private final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]){6}");

    // Дорогой, я_из_будущего! Пожалуйста, прости меня за этот код.
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DDWelcome enabled! 0_0");
    }

    private String formatHexColors(String message) {
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color.substring(1)).toString());
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private void spawnParticles(Player player, String type) {
        String path = "particles." + type + ".";
        Particle particleType = Particle.valueOf(getConfig().getString(path + "type"));
        int count = getConfig().getInt(path + "count");
        player.getWorld().spawnParticle(particleType, player.getLocation(), count);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (getConfig().getBoolean("features.join-message")) {
            String message = getConfig().getString("messages.join")
                    .replace("%player%", event.getPlayer().getName());
            event.setJoinMessage(formatHexColors(message));
        }

        if (getConfig().getBoolean("features.join-particles")) {
            spawnParticles(event.getPlayer(), "join");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (getConfig().getBoolean("features.quit-message")) {
            String message = getConfig().getString("messages.quit")
                    .replace("%player%", event.getPlayer().getName());
            event.setQuitMessage(formatHexColors(message));
        }

        if (getConfig().getBoolean("features.quit-particles")) {
            spawnParticles(event.getPlayer(), "quit");
        }
    }
}
