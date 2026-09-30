package io.github.tear360.simplesettings;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Point d'entrée du plugin SimpleSettings.
 *
 * <p>Fournit la commande {@code /settings} qui ouvre un menu de paramètres en
 * utilisant les écrans natifs de Minecraft (dialogs), et non un inventaire de
 * coffre.</p>
 */
public final class SimpleSettingsPlugin extends JavaPlugin implements Listener {

    /** État en mémoire : UUID du joueur -> true si son propre pseudo est masqué. */
    private final Map<UUID, Boolean> ownNametagHidden = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("settings"), "La commande settings est absente de plugin.yml")
            .setExecutor(new SettingsCommand(this));
        getLogger().info("SimpleSettings activé. Commande /settings disponible.");
    }

    /**
     * Ré-applique les paramètres du joueur lorsqu'il rejoint le serveur.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (isOwnNametagHidden(player.getUniqueId())) {
            player.hidePlayer(this, player);
        }
    }

    /**
     * Indique si le joueur a masqué son propre pseudo (visible en 3e personne / F5).
     */
    public boolean isOwnNametagHidden(UUID playerId) {
        return ownNametagHidden.getOrDefault(playerId, false);
    }

    /**
     * Masque ou ré-affiche le propre pseudo du joueur au-dessus de sa tête
     * (visible uniquement par lui, en troisième personne / F5).
     */
    public void setOwnNametagHidden(Player player, boolean hidden) {
        if (hidden) {
            player.hidePlayer(this, player);
        } else {
            player.showPlayer(this, player);
        }
        ownNametagHidden.put(player.getUniqueId(), hidden);
    }
}
