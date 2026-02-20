# 📱 BienPrêter Android App

Application Android pour [bienpreter.com/projets](https://www.bienpreter.com/projets)

## 🚀 Compiler l'APK automatiquement

### Via GitHub Actions (recommandé)
1. Uploadez ce projet sur GitHub
2. GitHub Actions compile l'APK automatiquement
3. Téléchargez l'APK dans l'onglet **Actions → votre workflow → Artifacts → Bienpreter**

### En local (Android Studio)
1. Ouvrir le projet dans Android Studio
2. **Build → Build APK(s)**

## 📋 Fonctionnalités
- Liste des projets en cours de financement
- Filtres par catégorie (Immobilier, BTP, etc.)
- Statistiques (706 projets en remboursement, 3 906 remboursés)
- Détail de chaque projet
- Pull-to-refresh
- Écran de connexion

## 🔧 Stack technique
- Kotlin + MVVM
- Retrofit 2 + OkHttp
- Coil (images)
- Material Design 3
- minSdk 24 (Android 7+)
