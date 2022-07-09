package fr.bobinho.nocollisiondamagefix.api.logger;

import fr.bobinho.nocollisiondamagefix.api.validate.BValidate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Bobinho logger library
 */
public class BLogger {

    private final String prefix;

    /**
     * Creates a new logger
     *
     * @param prefix the logger prefix
     */
    public BLogger(@Nonnull String prefix) {
        BValidate.notNull(prefix);

        this.prefix = "[" + prefix + "]";
    }

    /**
     * Sends information message to bukkit console
     * It supports bukkit color codes
     *
     * @param message Information message
     */
    public void info(@Nonnull String message) {
        BValidate.notNull(message);

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.BLUE + prefix + " " + message));
    }

    /**
     * Sends warning message to bukkit console
     * It supports bukkit color codes
     *
     * @param message Information message
     */
    public void warn(@Nonnull String message) {
        BValidate.notNull(message);

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + prefix + " " + message));
    }

    /**
     * Sends error message to bukkit console
     * It supports bukkit color codes
     *
     * @param message Information message
     */
    public void error(@Nonnull String message) {
        BValidate.notNull(message);

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + prefix + " " + message));
    }

    /**
     * Sends error message to bukkit console
     * It supports bukkit color codes
     *
     * @param message   Information message
     * @param exception Exception
     */
    public void error(@Nonnull String message, @Nonnull Exception exception) {
        BValidate.notNull(message);
        BValidate.notNull(exception);

        StringWriter exceptionWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(exceptionWriter));

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.DARK_RED + prefix + " " + message + ": &c" + exceptionWriter));
    }

    /**
     * Sends error message to bukkit console
     * It supports bukkit color codes
     *
     * @param message Information message
     * @param error   Error
     */
    public void error(@Nonnull String message, @Nonnull Error error) {
        BValidate.notNull(message);
        BValidate.notNull(error);

        StringWriter errorWriter = new StringWriter();
        error.printStackTrace(new PrintWriter(errorWriter));

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.DARK_RED + prefix + " " + message + ": &c" + errorWriter));
    }

    /**
     * Sends information message to bukkit console
     * It supports bukkit color codes
     *
     * @param message Information message
     */
    public void debug(@Nonnull String message) {
        BValidate.notNull(message);

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.AQUA + prefix + " " + message));
    }

}
