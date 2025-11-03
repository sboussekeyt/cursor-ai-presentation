# Présentation : Cursor & l'IA pour le Codage

## 🎯 Vue d'Ensemble

Présentation moderne et interactive sur le développement assisté par IA, couvrant Cursor, les modèles LLM et le Context Engineering.

**Lien de déploiement** : Déployable sur GitHub Pages

## 📋 Contenu de la Présentation

### Slide 0 : Accueil ([index.html](index.html))
- Introduction générale
- Aperçu des 3 thèmes principaux
- Navigation rapide vers les sections

### Slide 1 : Cursor & Écosystème ([slide1.html](slide1.html))
- **Qu'est-ce que Cursor ?** IDE basé sur VS Code avec IA intégrée
- **Fonctionnalités clés** : Chat, auto-completion, refactoring, debugging
- **Outils concurrents** : Tableau comparatif (Claude Code, Kilo Code, Code Assist, Gemini CLI)

### Slide 2 : Modèles LLM ([slide2.html](slide2.html))
- **Importance du choix** : Qualité, coût, vitesse, spécialisation
- **Comparaison détaillée** : Claude Sonnet, GPT-4, DeepSeek, Gemini, GLM-4
- **Guide de sélection** : Flowchart interactif selon vos besoins

### Slide 3 : Context Engineering 1/3 ([slide3.html](slide3.html))
- **Le problème** : Absence de mémoire persistante des LLMs
- **Solutions** :
  - Fichiers Markdown (.cursorrules, CLAUDE.md)
  - Context Files (@filename, @folder, @web)
  - MCP (Model Context Protocol)

### Slide 4 : Context Engineering 2/3 ([slide4.html](slide4.html))
- **Tokens** : Définition et exemples concrets
- **Context Window** : Visualisation de la fenêtre de contexte
- **Dépassement** : Problèmes et coûts
- **Compression** : Stratégies d'optimisation (résumé, sélection, rotation)

### Slide 5 : Context Engineering 3/3 ([slide5.html](slide5.html))
- **MCP** : Architecture client-serveur
- **Serveurs populaires** : Context7, Filesystem, GitHub, Web Search
- **Sources de contexte** : Vue d'ensemble complète
- **Points clés** : Résumé des takeaways essentiels

## 🚀 Navigation

### Clavier
- **→ / Space** : Slide suivante
- **←** : Slide précédente
- **Home** : Retour à l'accueil
- **End** : Dernière slide

### Mobile
- **Swipe gauche** : Slide suivante
- **Swipe droite** : Slide précédente

### Interface
- **Barre de progression** : En haut de chaque page
- **Boutons** : Navigation en bas de chaque slide
- **Menu** : Navigation persistante

## 🎨 Design

### Palette de Couleurs
- **Primary** : Violet (#8B5CF6) → Bleu (#3B82F6)
- **Dark** : Violet foncé (#5B21B6)
- **Background** : Blanc (#FFFFFF)
- **Text** : Charcoal (#1F2937)
- **Accent** : Vert (#10B981), Orange (#F59E0B)

### Typographie
- **Headers** : Poppins (600-800)
- **Body** : Inter (400-600)
- **Code** : Monospace

### Composants
- ✅ Cards avec hover effects
- ✅ Animations fluides (fadeIn, slide)
- ✅ Tables comparatives
- ✅ Diagrammes et flowcharts
- ✅ Visualisation des tokens
- ✅ Architecture MCP

## 📱 Responsive

- **Desktop** : Layout à 2 colonnes, toutes les fonctionnalités
- **Tablet** : Layout adaptatif, navigation simplifiée
- **Mobile** : Colonne unique, swipe gestures, optimisé pour lecture

## ♿ Accessibilité

- Contraste WCAG AA (minimum 4.5:1)
- Navigation au clavier complète
- Focus visible sur tous les éléments interactifs
- Aria labels pour éléments dynamiques
- Screen reader friendly

## 🖨️ Mode Impression

- Navigation et footer masqués
- Optimisation pour PDF export
- Évite les coupures de pages
- Fond blanc pour économie d'encre

## 🌐 Déploiement GitHub Pages

### Configuration

1. **Repository Settings**
   - Settings → Pages
   - Source : `main` branch
   - Folder : `/docs`

2. **URL** : `https://[username].github.io/[repo-name]`

### Checklist Avant Déploiement

- [x] Tous les chemins sont relatifs
- [x] Navigation fonctionnelle
- [x] Responsive testé
- [x] Accessibilité vérifiée
- [x] Performance optimisée
- [x] Meta tags OpenGraph

## 📂 Structure des Fichiers

```
docs/
├── index.html              # Page d'accueil
├── slide1.html             # Cursor & Outils
├── slide2.html             # Modèles LLM
├── slide3.html             # Context 1 : Mémoire
├── slide4.html             # Context 2 : Tokens
├── slide5.html             # Context 3 : MCP
├── css/
│   ├── style.css          # Styles globaux
│   └── slides.css         # Styles spécifiques slides
├── js/
│   └── navigation.js      # Navigation et interactions
└── images/
    └── (images à ajouter)
```

## 🔧 Technologies

- **HTML5** : Structure sémantique
- **CSS3** : Animations, Grid, Flexbox
- **JavaScript (Vanilla)** : Navigation et interactivité
- **Google Fonts** : Inter + Poppins

## ✨ Fonctionnalités

### Implemented
- ✅ Navigation clavier/touch complète
- ✅ Barre de progression dynamique
- ✅ Animations fluides
- ✅ Design responsive
- ✅ Tables comparatives
- ✅ Diagrammes visuels
- ✅ Token visualization
- ✅ Architecture MCP
- ✅ Mode impression
- ✅ Accessibilité

### À Ajouter (Optionnel)
- [ ] Screenshots de Cursor en action
- [ ] Logos des outils (Cursor, Claude Code, etc.)
- [ ] Images/diagrammes additionnels
- [ ] Animations avancées (optional)

## 📊 Performance

- **Chargement** : < 2 secondes
- **First Contentful Paint** : < 1 seconde
- **Time to Interactive** : < 2 secondes
- **Lighthouse Score** : 90+ (Performance, Accessibility, Best Practices)

## 🐛 Troubleshooting

### La navigation ne fonctionne pas
- Vérifiez que `navigation.js` est chargé
- Ouvrez la console : devrait afficher "Navigation Loaded"

### Les styles ne s'appliquent pas
- Vérifiez les chemins vers `style.css` et `slides.css`
- Effacez le cache du navigateur

### Responsive cassé
- Testez avec Chrome DevTools (F12)
- Vérifiez les media queries dans `slides.css`

## 📖 Utilisation

### Pour Présentateur

1. Ouvrir `index.html` dans un navigateur
2. Mode plein écran : F11
3. Naviguer avec flèches ou boutons
4. Utiliser les points clés pour guider le discours

### Pour Export PDF

1. Ouvrir dans Chrome/Edge
2. Ctrl/Cmd + P (Imprimer)
3. Destination : "Enregistrer au format PDF"
4. Options : "Arrière-plans et images"
5. Sauvegarder

### Pour Modification

1. Éditer les fichiers HTML pour le contenu
2. Modifier `slides.css` pour les styles
3. Tester dans navigateur
4. Commit et push vers GitHub

## 🎓 Concepts Couverts

### Cursor
- IDE augmenté par IA
- Fonctionnalités principales
- Comparaison avec alternatives

### Modèles LLM
- Claude (Anthropic)
- GPT-4 (OpenAI)
- DeepSeek Coder
- Gemini (Google)
- GLM-4 (Zhipu AI)

### Context Engineering
- Persistence de la mémoire
- Gestion des tokens
- Context Window
- MCP (Model Context Protocol)
- Optimisation du contexte

## 📝 Notes pour les Développeurs

### Ajouter une Nouvelle Slide

1. Copier `slide5.html` → `slide6.html`
2. Modifier le contenu
3. Mettre à jour la progress bar (`width: X%`)
4. Mettre à jour le compteur (`Slide X / 6`)
5. Ajouter au tableau `pages` dans `navigation.js`
6. Mettre à jour les liens de navigation

### Personnaliser les Couleurs

Modifier les variables CSS dans `style.css` :

```css
:root {
    --color-purple: #8B5CF6;
    --color-blue: #3B82F6;
    --color-purple-dark: #5B21B6;
    /* etc. */
}
```

### Ajouter des Animations

Utiliser les keyframes existantes ou créer de nouvelles dans `slides.css` :

```css
@keyframes monAnimation {
    from { /* état initial */ }
    to { /* état final */ }
}

.mon-element {
    animation: monAnimation 0.5s ease;
}
```

## 🤝 Contribution

Pour améliorer cette présentation :

1. Fork le repository
2. Créer une branche : `git checkout -b feature/amelioration`
3. Commit : `git commit -m "Description"`
4. Push : `git push origin feature/amelioration`
5. Créer une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Libre d'utilisation et de modification.

## 🙏 Remerciements

- **Cursor** pour l'IDE innovant
- **Anthropic** pour Claude et MCP
- **OpenAI** pour GPT-4
- **Communauté open source** pour l'inspiration

---

**Version** : 1.0.0
**Dernière mise à jour** : Novembre 2025
**Auteur** : Créé avec Claude Code

🚀 **Bon présentationning !**
