package fr.bobinho.nocollisiondamagefix;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import fr.bobinho.nocollisiondamagefix.api.logger.BLogger;
import fr.bobinho.nocollisiondamagefix.api.validate.BValidate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Core of the plugin
 */
public final class NoCollisionDamageFixCore extends JavaPlugin {

    /**
     * Fields
     */
    private static final BLogger bLogger = new BLogger(NoCollisionDamageFixCore.class.getSimpleName());

    /**
     * Gets the plugin
     *
     * @return the plugin
     */
    public static NoCollisionDamageFixCore getInstance() {
        return JavaPlugin.getPlugin(NoCollisionDamageFixCore.class);
    }

    /**
     * Gets the logger
     *
     * @return the logger
     */
    public static BLogger getBLogger() {
        return bLogger;
    }

    /**
     * Gets the FPlayer
     *
     * @param player the player
     * @return the FPlayer
     */
    public static FPlayer getFPlayer(@Nonnull Player player) {
        BValidate.notNull(player);

        return FPlayers.getInstance().getByPlayer(player);
    }

    /**
     * Enables and initializes the plugin
     */
    @Override
    public void onEnable() {
        bLogger.info("Loading the plugin...");

        //Registers commands and listeners
        registerListeners();
    }

    /**
     * Disables the plugin and save data
     */
    @Override
    public void onDisable() {
        bLogger.info("Unloading the plugin...");
    }

    /**
     * Registers listeners
     */
    private void registerListeners() {
        Reflections reflections = new Reflections("fr.bobinho.nocollisiondamagefix.listeners");
        Set<Class<? extends Listener>> classes = reflections.getSubTypesOf(Listener.class);
        for (@Nonnull Class<? extends Listener> listener : classes) {
            try {
                Bukkit.getServer().getPluginManager().registerEvents(listener.getDeclaredConstructor().newInstance(), this);
            } catch (Exception exception) {
                getBLogger().error("Couldn't register command(" + listener.getName() + ")!", exception);
            }
        }
        bLogger.info("Successfully loaded listeners.");
    }

}
