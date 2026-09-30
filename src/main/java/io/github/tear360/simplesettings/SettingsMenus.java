package io.github.tear360.simplesettings;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.dialog.DialogLike;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Construit et affiche les menus de paramètres à l'aide de l'API Dialog de
 * Paper : de véritables écrans natifs de Minecraft (introduits en 1.21.6)
 * avec boutons, survol et fermeture par Échap — pas un inventaire de coffre.
 *
 * <p>Chaque clic sur un bouton est géré par un {@link ClickCallback} enregistré
 * côté serveur, puis le menu est reconstruit et ré-affiché pour refléter
 * l'état à jour du paramètre (interrupteur visuel ✔ / ✘).</p>
 */
final class SettingsMenus {

    private static final TextColor GREEN = TextColor.color(0x55FF55);
    private static final TextColor RED = TextColor.color(0xFF5555);
    private static final TextColor ACCENT = TextColor.color(0x7FC7FF);
    private static final int BUTTON_WIDTH = 260;

    private SettingsMenus() {
    }

    /**
     * Ouvre le menu principal des paramètres.
     */
    static void openMainMenu(SimpleSettingsPlugin plugin, Player player) {
        player.showDialog(buildMainMenu(plugin, player));
    }

    private static DialogLike buildMainMenu(SimpleSettingsPlugin plugin, Player player) {
        List<ActionButton> buttons = new ArrayList<>();

        boolean hidden = plugin.isOwnNametagHidden(player.getUniqueId());
        buttons.add(ActionButton.create(
            toggleLabel("Afficher mon pseudo au-dessus de ma tête", !hidden),
            Component.text("Visible uniquement par toi, en troisième personne (F5).", NamedTextColor.GRAY),
            BUTTON_WIDTH,
            DialogAction.customClick((view, audience) -> {
                if (audience instanceof Player clicked) {
                    boolean newValue = !plugin.isOwnNametagHidden(clicked.getUniqueId());
                    plugin.setOwnNametagHidden(clicked, newValue);
                    feedback(clicked, newValue
                        ? Component.text("Ton pseudo est maintenant masqué.", RED)
                        : Component.text("Ton pseudo est maintenant affiché.", GREEN));
                    openMainMenu(plugin, clicked);
                }
            }, ClickCallback.Options.builder().build())
        ));

        // Futurs paramètres : ajouter ici de nouveaux boutons avec leur callback.

        return Dialog.create(builder -> builder.empty()
            .base(DialogBase.builder(Component.text("Paramètres", ACCENT))
                .body(List.of(
                    DialogBody.plainMessage(Component.text(
                        "Modifie tes préférences personnelles.", NamedTextColor.GRAY))))
                .build())
            .type(DialogType.multiAction(buttons, exitButton(), 1)));
    }

    /**
     * Bouton "Fermer" : action null, le client ferme simplement l'écran.
     */
    private static ActionButton exitButton() {
        return ActionButton.create(
            Component.text("Fermer", NamedTextColor.GRAY),
            null,
            BUTTON_WIDTH,
            null
        );
    }

    /**
     * Libellé d'interrupteur : ✔ vert quand activé, ✘ gris sinon.
     */
    private static Component toggleLabel(String label, boolean enabled) {
        return Component.text()
            .append(Component.text(enabled ? "✔ " : "✘ ", enabled ? GREEN : NamedTextColor.GRAY))
            .append(Component.text(label, enabled ? NamedTextColor.WHITE : NamedTextColor.GRAY))
            .build();
    }

    private static void feedback(Player player, Component message) {
        player.sendMessage(message);
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.6f, 1.4f);
    }
}
