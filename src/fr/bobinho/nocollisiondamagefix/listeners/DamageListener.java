package fr.bobinho.nocollisiondamagefix.listeners;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.struct.Relation;
import fr.bobinho.nocollisiondamagefix.NoCollisionDamageFixCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Listener of damage
 */
public final class DamageListener implements Listener {

    private final Set<Player> damagedPlayers = new HashSet<>();

    /**
     * Listen when a player attack another player
     *
     * @param e the entity damage by entity event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) {
            return;
        }

        //Gets FPlayers
        FPlayer victim = NoCollisionDamageFixCore.getFPlayer((Player) e.getEntity());
        FPlayer attacker = NoCollisionDamageFixCore.getFPlayer((Player) e.getDamager());

        //Damages the "inside" player
        if (damagedPlayers.contains((Player) e.getEntity())) {
            if (victim.getFaction().getRelationTo(attacker.getFaction()) != Relation.ALLY) {
                ((Player) e.getEntity()).damage(e.getDamage());
                ((Player) e.getEntity()).setLastDamage(e.getDamage());
            }
            damagedPlayers.remove((Player) e.getEntity());
            return;
        }

        //Gets all "inside" players
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> !player.equals(e.getEntity()))
                .filter(player -> !player.equals(e.getDamager()))
                .filter(player -> player.getWorld().equals(e.getDamager().getWorld()))
                .filter(player -> player.getLocation().distance(e.getEntity().getLocation()) <= 0.3)
                .forEach(player -> {

                    //Adds the player to the damaged players list
                    damagedPlayers.add(player);

                    //Creates the damage event
                    EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(e.getDamager(), player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, e.getDamage());
                    player.setLastDamageCause(event);

                    //Calls the damage event
                    Bukkit.getServer().getPluginManager().callEvent(event);
                });
    }

    /**
     * Listen when a player quit the server
     *
     * @param e the player quit event
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        damagedPlayers.removeIf(player -> player.equals(e.getPlayer()));
    }

}
