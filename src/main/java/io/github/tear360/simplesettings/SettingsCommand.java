package io.github.tear360.simplesettings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** Exécuteur de la commande {@code /settings}. */
public final class SettingsCommand implements CommandExecutor {

    private final SimpleSettingsPlugin plugin;

    public SettingsCommand(SimpleSettingsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Seuls les joueurs peuvent ouvrir le menu des paramètres.", NamedTextColor.RED));
            return true;
        }
        if (!player.hasPermission("simplesettings.use")) {
            player.sendMessage(Component.text("Tu n'as pas la permission d'utiliser cette commande.", NamedTextColor.RED));
            return true;
        }
        SettingsMenus.openMainMenu(plugin, player);
        return true;
    }
}
