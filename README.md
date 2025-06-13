# 🔬 ArkaLaboratoire

**ArkaLaboratoire** est un plugin Minecraft (1.8) ajoutant un système de **laboratoire** évolutif où les joueurs peuvent faire des expériences, découvrir des mutations de cultures, 
et accumuler des points pour débloquer des récompenses.

---

## ⚙️ Commandes administrateur

| Commande | Description |
|---------|-------------|
| `/arkalaboadmin give <joueur> <item>` | Donne un objet lié au laboratoire |
| `/arkalaboadmin reset <joueur>` | Réinitialise les données laboratoire d’un joueur |
| `/arkalaboadmin addpoints <joueur> <montant>` | Ajoute des points de laboratoire |
| `/arkalaboadmin removepoints <joueur> <montant>` | Retire des points de laboratoire |

---

## 🧩 Placeholders

### 🔢 Leaderboard

| Placeholder | Description |
|-------------|-------------|
| `%arkalabo_leaderboard.experiments_total_top.top_X%` | Top X joueurs par nombre total d’expériences |
| `%arkalabo_leaderboard.mutations_success_top.top_X%` | Top X joueurs par mutations réussies |
| `%arkalabo_leaderboard.mutations_failed_top.top_X%` | Top X joueurs par mutations échouées |

### 👤 Joueur

| Placeholder | Description |
|-------------|-------------|
| `%arkalabo_player.experiments_success%` | Nombre d’expériences réussies |
| `%arkalabo_player.experiments_failed%` | Nombre d’expériences échouées |
| `%arkalabo_player.experiments_total%` | Nombre total d’expériences |
| `%arkalabo_player.success_rate%` | Taux de réussite (%) |
| `%arkalabo_player.failure_rate%` | Taux d’échec (%) |

### ⚗️ Données laboratoire

| Placeholder | Description |
|-------------|-------------|
| `%arkalabo_experience_success_rate%` | Chance actuelle de réussite d'une expérience |
| `%arkalabo_experience_failure_rate%` | Chance actuelle d’échec |
| `%arkalabo_resources_used%` | Ressources utilisées lors de la dernière expérience |
| `%arkalabo_resources_saved%` | Ressources économisées (si applicable) |
| `%arkalabo_points%` | Points de laboratoire actuels |
| `%arkalabo_experiments_success%` | Expériences réussies (total) |
| `%arkalabo_experiments_failed%` | Expériences échouées (total) |
| `%arkalabo_experiments_total%` | Expériences totales effectuées |

---
