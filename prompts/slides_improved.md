# Prompt Amélioré : Présentation sur le Développement avec l'IA

## 🎯 Objectif
Créer une présentation professionnelle et visuellement attractive sur le développement assisté par IA, déployable sur GitHub Pages. Public cible : développeurs et professionnels techniques.

## 📋 Spécifications Techniques

### Technologies
- **Frontend** : HTML5, CSS3, JavaScript vanilla
- **Déploiement** : GitHub Pages (pas de build process requis)
- **Responsive** : Compatible mobile, tablette, desktop
- **Performance** : Chargement rapide (<2s), images optimisées
- **Accessibilité** : Navigation au clavier, contraste WCAG AA

### Structure des Fichiers
```
docs/
├── index.html           # Page d'accueil
├── slide1.html          # Cursor & Concurrence
├── slide2.html          # Modèles LLM
├── slide3.html          # Context Engineering 1
├── slide4.html          # Context Engineering 2
├── slide5.html          # Context Engineering 3
├── css/
│   ├── style.css        # Styles principaux
│   └── slides.css       # Styles spécifiques slides
├── js/
│   ├── navigation.js    # Navigation entre slides
│   └── animations.js    # Animations et transitions
└── images/
    └── diagrams/        # Diagrammes SVG
```

## 🎨 Design Requirements

### Palette de Couleurs
- **Primary** : Dégradés violet (#8B5CF6) → bleu (#3B82F6)
- **Secondary** : Charcoal (#1F2937), Light gray (#F3F4F6)
- **Accents** : Green (#10B981), Orange (#F59E0B)

### Typographie
- **Headers** : Poppins (bold, 600-800)
- **Body** : Inter (regular, 400-500)
- **Code** : Fira Code / JetBrains Mono

### Composants Visuels
- Cards avec ombres subtiles et hover effects
- Animations de transition fluides (0.3s ease)
- Boutons de navigation avec états hover/active
- Progress bar pour indiquer position dans présentation
- Icons modernes (Feather Icons ou similaire)

## 📊 Contenu Détaillé des Slides

### **Slide 1 : Cursor & Écosystème des Outils IA**

**Titre** : "Cursor : L'IDE Augmenté par l'IA"

**Contenu Principal** :
1. **Qu'est-ce que Cursor ?**
   - IDE basé sur VS Code avec IA intégrée
   - Completion de code intelligente
   - Chat contextuel dans l'éditeur
   - Refactoring assisté par IA

2. **Fonctionnalités Clés** (avec icônes) :
   - 💬 Chat contextuel (Cmd+K)
   - ⚡ Auto-completion prédictive
   - 🔄 Refactoring automatique
   - 📝 Génération de code à partir de commentaires
   - 🐛 Debugging assisté

3. **Outils Concurrents** (tableau comparatif) :

| Outil | Forces | Modèle | Prix |
|-------|--------|--------|------|
| **Cursor** | Intégration VS Code, Chat inline | GPT-4, Claude | 20$/mois |
| **Claude Code** | Autonomous coding, TDD focus | Claude Sonnet 4 | Inclus Anthropic |
| **Kilo Code** | Lightweight, fast | Multiple | Freemium |
| **Code Assist** | AWS integration | Amazon Titan | AWS pricing |
| **Gemini CLI** | Terminal-based, Google ecosystem | Gemini Pro | Google AI |

**Éléments Visuels** :
- Screenshot de Cursor en action
- Logos des outils concurrents
- Diagramme de comparaison visuelle

---

### **Slide 2 : L'Importance du Choix du Modèle**

**Titre** : "Choisir le Bon Modèle : Performance vs Coût"

**Contenu Principal** :

1. **Pourquoi le Modèle Compte ?**
   - Impact sur la qualité du code généré
   - Coût par requête (tokens)
   - Vitesse de génération
   - Spécialisation (code, chat, raisonnement)

2. **Comparaison des Modèles** (visual cards) :

**Claude Sonnet 3.5/4**
- ✅ Excellent pour code complexe
- ✅ Raisonnement approfondi
- ⚠️ Coût élevé ($3-15/1M tokens)
- 📊 200k tokens de contexte

**GPT-4 Turbo / GPT-4o**
- ✅ Polyvalent, rapide
- ✅ Large adoption
- ⚠️ Coût moyen ($5-10/1M tokens)
- 📊 128k tokens de contexte

**DeepSeek Coder V3**
- ✅ Spécialisé code
- ✅ Open source
- ✅ Très économique (<$1/1M tokens)
- 📊 64k tokens de contexte

**Gemini 2.0 Flash**
- ✅ Ultra-rapide
- ✅ Multimodal
- ✅ Gratuit (quotas généreux)
- 📊 1M tokens de contexte

**GLM-4 (Zhipu AI)**
- ✅ Optimisé Chinois
- ✅ Bon rapport qualité/prix
- 📊 128k tokens de contexte

3. **Guide de Sélection** (flowchart) :
```
Besoin de raisonnement complexe?
  → Oui : Claude Sonnet 4
  → Non : Budget serré?
         → Oui : DeepSeek / Gemini Flash
         → Non : GPT-4o (polyvalence)
```

**Éléments Visuels** :
- Graphique radar comparant vitesse/coût/qualité
- Timeline d'évolution des modèles
- Diagramme de décision interactif

---

### **Slide 3 : Context Engineering (Partie 1) - Mémoire & Stockage**

**Titre** : "Gérer l'Absence de Mémoire : Stratégies de Persistence"

**Contenu Principal** :

1. **Le Problème : Pas de Mémoire Persistante**
   - Les LLMs sont **sans état** (stateless)
   - Chaque conversation = nouveau contexte
   - Perte d'informations entre sessions

2. **Solution 1 : Fichiers Markdown (.md)**
   ```
   project/
   ├── .cursorrules          # Règles globales du projet
   ├── CLAUDE.md             # Instructions pour Claude
   ├── docs/
   │   ├── architecture.md   # Contexte architectural
   │   └── conventions.md    # Standards de code
   ```

3. **Solution 2 : Context Files**
   - `@filename` pour inclure dans le contexte
   - `@folder` pour contexte de répertoire
   - `@web` pour récupérer docs externes

4. **Solution 3 : MCP (Model Context Protocol)**
   - Serveurs de contexte externes
   - Accès à bases de connaissances
   - Exemple : Context7 pour docs officielles

**Diagramme** :
```
Requête Utilisateur
     ↓
[Fichiers MD] → [Context Loader] → [LLM] → Réponse
     ↑              ↑
[MCP Servers]  [Conversation History]
```

**Éléments Visuels** :
- Diagramme d'architecture de context loading
- Exemples de fichiers .cursorrules et CLAUDE.md
- Icônes pour chaque type de stockage

---

### **Slide 4 : Context Engineering (Partie 2) - Tokens & Fenêtre de Contexte**

**Titre** : "Comprendre les Tokens : Optimiser la Fenêtre de Contexte"

**Contenu Principal** :

1. **Qu'est-ce qu'un Token ?**
   - Unité de traitement du texte (~4 caractères en anglais, ~2-3 en français)
   - Exemples :
     - "Hello" = 1 token
     - "Bonjour" = 2 tokens (Bon + jour)
     - "const x = 10;" = 5 tokens

2. **La Fenêtre de Contexte** (Context Window)

   **Visualisation** :
   ```
   [──────────── Context Window (200k tokens) ────────────]
   │                                                       │
   │ System     │ Files     │ Chat      │ Output         │
   │ Prompt     │ @included │ History   │ Generation     │
   │ (5k)       │ (50k)     │ (100k)    │ (45k)          │
   └────────────┴───────────┴───────────┴─────────────────┘
   ```

3. **Problème : Dépassement de Contexte**
   - ⚠️ Erreur : "Context length exceeded"
   - Impact : LLM oublie début de conversation
   - Coût : Plus de tokens = plus cher

4. **Stratégies de Compression** :
   - ✂️ **Résumé** : Condenser l'historique
   - 🎯 **Sélection** : N'inclure que fichiers pertinents
   - 🗜️ **Compression** : Reformuler concisément
   - 🔄 **Rotation** : Archiver conversations anciennes

**Diagramme : Token Budget** (pie chart)
- System Prompt : 5%
- Files & Docs : 30%
- Chat History : 50%
- Output Buffer : 15%

**Éléments Visuels** :
- Animation de remplissage de contexte
- Compteur de tokens en temps réel (exemple)
- Graphique de distribution des tokens

---

### **Slide 5 : Context Engineering (Partie 3) - MCP & Sources de Contexte**

**Titre** : "MCP & Écosystème de Contexte : Tout Connecter"

**Contenu Principal** :

1. **MCP (Model Context Protocol)**
   - Standard pour connecter LLMs à sources externes
   - Créé par Anthropic (Claude)
   - Architecture client-serveur

2. **Serveurs MCP Populaires** :

   **Context7** 🔍
   - Docs officielles (React, Python, etc.)
   - Toujours à jour
   - Exemples de code canoniques

   **Filesystem** 📁
   - Accès au système de fichiers
   - Lecture/écriture automatisée

   **GitHub** 🐙
   - Repos, issues, PRs
   - Context des projets open source

   **Web Search** 🌐
   - Recherche en temps réel
   - Informations actualisées

3. **Architecture MCP** :
   ```
   ┌─────────────┐
   │   Cursor    │
   │   (Client)  │
   └──────┬──────┘
          │
    ┌─────┴──────┐
    │ MCP Router │
    └─────┬──────┘
          │
   ┌──────┼──────────┬──────────┐
   │      │          │          │
   ▼      ▼          ▼          ▼
[Context7] [GitHub] [Filesystem] [Custom]
   ```

4. **Sources de Contexte (Résumé)** :
   - 📄 **Fichiers** : Code, docs, config
   - 💬 **Conversation** : Historique du chat
   - 🔌 **MCP** : Serveurs externes
   - 📚 **Exemples** : Snippets, templates
   - 📏 **Rules** : .cursorrules, CLAUDE.md
   - 🌐 **Web** : Docs, Stack Overflow

**Formule du Contexte** :
```
Context Total = System Prompt
              + Files (@included)
              + Conversation History
              + MCP Data
              + Examples & Rules
```

**Éléments Visuels** :
- Diagramme d'architecture MCP
- Icons pour chaque source de contexte
- Flowchart de résolution de contexte

---

## 🖼️ Assets Visuels Requis

### Images à Créer/Trouver
- [ ] Logo Cursor (haute résolution)
- [ ] Screenshots de Cursor en action (chat, completion)
- [ ] Logos des outils concurrents (Claude Code, Kilo, etc.)
- [ ] Icons pour fonctionnalités (chat, debug, refactor)
- [ ] Diagramme de comparaison modèles (radar chart)
- [ ] Architecture diagram pour context loading
- [ ] Token visualization (animated if possible)
- [ ] MCP architecture diagram

### Diagrammes à Générer (SVG ou Mermaid)
1. **Flowchart** : Guide de sélection de modèle
2. **Architecture** : Context loading system
3. **Pie Chart** : Token budget distribution
4. **Network Diagram** : MCP server connections
5. **Timeline** : Évolution des modèles LLM

## 🚀 Déploiement GitHub Pages

### Configuration
1. Repository public sur GitHub
2. Settings → Pages → Source: `main` branch, `/docs` folder
3. URL : `https://[username].github.io/[repo-name]`

### Checklist Déploiement
- [ ] Tous les chemins relatifs (pas d'URL absolues)
- [ ] Images optimisées (<200KB chacune)
- [ ] Meta tags OpenGraph pour partage social
- [ ] Favicon configuré
- [ ] Mobile responsive testé
- [ ] Navigation fonctionnelle entre slides

## 📝 Notes Supplémentaires

### Navigation
- Flèches gauche/droite pour navigation clavier
- Boutons "Précédent" / "Suivant" visibles
- Barre de progression en haut
- Menu latéral optionnel pour accès rapide

### Animations
- Entrée en fondu pour chaque slide
- Transitions entre slides : slide-in
- Hover effects sur cards et boutons
- Animations subtiles, pas distrayantes

### Accessibilité
- Contraste minimum WCAG AA (4.5:1)
- Focus visible pour navigation clavier
- Alt text pour toutes les images
- Aria labels pour éléments interactifs

---

## 🔄 Améliorations vs Prompt Original

| Aspect | Original | Amélioré |
|--------|----------|----------|
| Structure | 3 slides | 5 slides (meilleure granularité) |
| Typos | "diagremmes", "exsite" | Corrigé |
| Specs techniques | Aucune | Détaillées (responsive, perf, a11y) |
| Design | "Beau" | Palette, typo, composants spécifiés |
| Visuels | "Images et diagrammes" | Liste précise de 8+ assets |
| Déploiement | Non spécifié | Guide GitHub Pages complet |
| Contenu | Plan basique | Détails complets avec exemples |
| Navigation | Non mentionnée | Spécifications complètes |

---

**Statut** : ✅ Prompt prêt pour implémentation
**Estimation** : 6-8h développement + 2h création assets visuels
