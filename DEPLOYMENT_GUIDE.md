# Guide de Déploiement sur GitHub Pages

Ce guide vous explique comment déployer votre présentation Cursor et l'IA pour le Codage sur GitHub Pages de manière permanente.

## 📋 Prérequis

- Un compte GitHub (créez-en un sur https://github.com si vous n'en avez pas)
- Git installé sur votre ordinateur
- Un terminal/invite de commande

## 🚀 Étapes de Déploiement

### Étape 1 : Créer un Repository GitHub

1. Allez sur https://github.com/new
2. Remplissez les informations :
   - **Repository name** : `cursor-ai-presentation`
   - **Description** : `Présentation interactive sur Cursor et l'IA pour le codage`
   - **Visibility** : Sélectionnez **Public**
3. Cliquez sur **Create repository**

### Étape 2 : Préparer votre Machine Locale

1. Ouvrez un terminal/invite de commande
2. Naviguez vers le répertoire du projet :
   ```bash
   cd /home/ubuntu/cursor-ai-presentation
   ```

3. Vérifiez que Git est initialisé (devrait déjà être fait) :
   ```bash
   git status
   ```

### Étape 3 : Ajouter le Repository Distant

1. Remplacez `Sboussekeyt` par votre nom d'utilisateur GitHub :
   ```bash
   git remote add origin https://github.com/Sboussekeyt/cursor-ai-presentation.git
   ```

2. Renommez la branche principale en `main` (si nécessaire) :
   ```bash
   git branch -M main
   ```

### Étape 4 : Pousser le Code vers GitHub

1. Poussez tous les fichiers vers GitHub :
   ```bash
   git push -u origin main
   ```

2. Vous serez peut-être invité à vous authentifier. Utilisez :
   - **Nom d'utilisateur** : Votre nom d'utilisateur GitHub
   - **Mot de passe** : Un token d'accès personnel (voir ci-dessous)

#### Créer un Token d'Accès Personnel (si nécessaire)

1. Allez sur https://github.com/settings/tokens
2. Cliquez sur **Generate new token**
3. Donnez un nom au token (ex: "cursor-ai-deployment")
4. Sélectionnez les permissions : `repo` (accès complet aux repositories)
5. Cliquez sur **Generate token**
6. Copiez le token et utilisez-le comme mot de passe lors du push

### Étape 5 : Activer GitHub Pages

1. Allez sur votre repository GitHub : `https://github.com/Sboussekeyt/cursor-ai-presentation`
2. Cliquez sur **Settings** (onglet en haut à droite)
3. Dans le menu de gauche, cliquez sur **Pages**
4. Sous "Build and deployment" :
   - **Source** : Sélectionnez **Deploy from a branch**
   - **Branch** : Sélectionnez **main** et **/root**
5. Cliquez sur **Save**

### Étape 6 : Vérifier le Déploiement

1. Attendez quelques minutes (GitHub Pages a besoin de temps pour construire le site)
2. Allez sur `https://Sboussekeyt.github.io/cursor-ai-presentation/`
3. Votre site devrait être en ligne !

## ✅ Vérification

Votre site est correctement déployé si :
- ✅ Vous pouvez accéder à `https://Sboussekeyt.github.io/cursor-ai-presentation/`
- ✅ Les images s'affichent correctement
- ✅ La navigation fonctionne
- ✅ Les styles CSS sont appliqués

## 🔄 Mises à Jour Futures

Pour mettre à jour votre site après des modifications :

1. Modifiez les fichiers localement
2. Committez les changements :
   ```bash
   git add .
   git commit -m "Description de vos changements"
   ```
3. Poussez vers GitHub :
   ```bash
   git push origin main
   ```
4. GitHub Pages se mettra à jour automatiquement en quelques minutes

## 🆘 Dépannage

### Le site n'apparaît pas après quelques minutes

- Vérifiez que vous êtes dans l'onglet **Pages** des settings
- Assurez-vous que la branche source est bien **main**
- Vérifiez les logs de GitHub Actions (onglet "Actions")

### Les images ne s'affichent pas

- Assurez-vous que le dossier `images/` est bien poussé sur GitHub
- Vérifiez les chemins des images dans les fichiers HTML

### Erreur lors du push

- Vérifiez votre connexion Internet
- Vérifiez que votre token d'accès personnel est valide
- Essayez de vous authentifier à nouveau

## 📚 Ressources Utiles

- [Documentation GitHub Pages](https://docs.github.com/en/pages)
- [Guide Git](https://git-scm.com/doc)
- [Créer un Token d'Accès Personnel](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)

## 🎉 Félicitations !

Votre présentation est maintenant en ligne et accessible à tous ! Vous pouvez partager le lien `https://Sboussekeyt.github.io/cursor-ai-presentation/` avec qui vous voulez.

---

**Besoin d'aide ?** Consultez la documentation GitHub Pages ou contactez le support GitHub.
