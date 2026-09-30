# SimpleSettings

Menu `/settings` pour les serveurs **Paper** sous **Minecraft 26.1, 26.1.2, 26.2 et 26.3**, construit avec les **écrans natifs de Minecraft** (l'API *Dialog*, introduite en 1.21.6) — pas un inventaire de coffre : de vrais boutons, des survols, et une fermeture avec Échap.

![GitHub release](https://img.shields.io/github/v/release/tear360/SimpleSettings?style=flat-square)

## Paramètres disponibles

| Paramètre | Description |
|---|---|
| **Cacher / afficher son propre pseudo** | Masque ton nom au-dessus de ta tête, visible uniquement par toi en **troisième personne (F5)**. L'état est conservé tant que le serveur tourne et ré-appliqué automatiquement à ta reconnexion. |

## Installation

1. Télécharge le dernier `SimpleSettings-x.y.z.jar` depuis la page [Releases](https://github.com/tear360/SimpleSettings/releases).
2. Dépose-le dans le dossier `plugins/` de ton serveur Paper.
3. Redémarre le serveur, puis tape `/settings` en jeu.

## Compilation

```bash
./gradlew build
```

Le jar est généré dans `build/libs/`.

## Compatibilité

Compilé contre `paper-api 26.1.2` (l'API utilisée existe déjà dans cette version), donc le plugin fonctionne tel quel sur :

- ✅ Minecraft 26.1 / 26.1.1 / 26.1.2 (Paper)
- ✅ Minecraft 26.2 (Paper)
- ✅ Minecraft 26.3 (Paper)

## Licence

[MIT](LICENSE)
