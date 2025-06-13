# 🧿 ArkaItem

**ArkaItem** est un plugin Minecraft (1.8) qui permet de créer, gérer et distribuer des items spéciaux . Chaque item peut traquer des données spécifiques (kills, blocs minés, dégâts, etc.) et offrir des effets uniques.

---

## ⚙️ Commandes

| Commande | Description |
|----------|-------------|
| `/arkaitem reload` | Recharge la configuration du plugin |
| `/arkaitem give <joueur> <item>` | Donne un item configuré à un joueur |
| `/arkaitem menu` | Ouvre le menu de gestion des items personnalisés |

---

## 🧩 Placeholders dynamiques

Ces variables peuvent être utilisées dans les lores des items pour afficher des statistiques en temps réel :

### 🎯 Statistiques générales

- `{kills}` : Nombre de kills réalisés avec l’item
- `{damage_done}` : Dégâts infligés totaux
- `{last_kill}` : Nom du dernier joueur tué
- `{last_enemy_hit}` : Dernier ennemi touché
- `{arrows_shot}` : Nombre de flèches tirées
- `{blocks_travelled}` : Distance parcourue (blocs)
- `{blocks_mined}` : Blocs minés avec l’outil
- `{hits_taken}` : Coups reçus (pour les armures)
- `{mobs_killed}` : Nombre de mobs tués
- `{trees_chopped}` : Nombre d’arbres coupés (bûches)
- `{lighting}` : Joueurs électrocutés
- `{power_retiré}` : Nombre de power retirés
- `{esquive}` : Nombre d’esquives réussies avec l’armure
- `{moneysteal}` : Nombre de vols d’argent réussis
- `{item_owner}` : Nom du propriétaire de l’item

### 🍽️ Consommables

- `{uses}` : Utilisations restantes
- `{maxuses}` : Nombre total d’utilisations possibles

---

## 💥 Effets spéciaux disponibles

Les items peuvent être configurés avec l’un des effets suivants (activés automatiquement ou via clic droit) :

- `Stealth` : Invisibilité temporaire
- `Bolt` : Frappe éclair sur un ennemi
- `SpawnerRemover` : Permet de retirer un spawner
- `CutTree` : Coupe instantanément un arbre complet
- `LifeSteal` : Vole de la vie à l’ennemi lors des attaques
- `NoFall` : Immunité aux dégâts de chute
- `MoneySteal` : Vole de l’argent en attaquant un joueur
- `Teleport` : Chance d'être régénéré et téléporter à coté du joueur qui nous a tué
- `TeleportToPlayer` : Téléportation vers un joueur
- `ChestReader` : Permet de lire le contenu d’un coffre dans un claim
- `MaskFarm` : Bonus pour les cultures et récoltes
- `RepairTool` : Répare un outil ou une armure
- `ArmorDodge` : Chance d’esquiver un coup
- `RotateEnemy` : Désoriente l’ennemi temporairement
- `RandomItemInventory` : Met le désordre dans l'inventaire de l'enemie

---
