name: Build APK Bienpreter

on:
  push:
    branches: [ main, master ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout du code
        uses: actions/checkout@v4

      - name: Installation de Java 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Installation du SDK Android
        uses: android-actions/setup-android@v3

      - name: Installation des composants SDK
        run: |
          sdkmanager --install "platform-tools" "platforms;android-34" "build-tools;34.0.0" 2>/dev/null
          echo "SDK OK"

      - name: Téléchargement de Gradle 8.2
        run: |
          wget -q https://services.gradle.org/distributions/gradle-8.2-bin.zip
          unzip -q gradle-8.2-bin.zip
          sudo mv gradle-8.2 /opt/gradle
          echo "/opt/gradle/bin" >> $GITHUB_PATH
          gradle --version

      - name: Génération du Gradle Wrapper
        run: |
          gradle wrapper --gradle-version 8.2
          chmod +x gradlew

      - name: Build de l'APK
        run: |
          ./gradlew assembleDebug --no-daemon 2>&1
        env:
          ANDROID_HOME: ${{ env.ANDROID_SDK_ROOT }}
          GRADLE_OPTS: "-Dorg.gradle.daemon=false"

      - name: Upload de l'APK
        uses: actions/upload-artifact@v4
        with:
          name: Bienpreter
          path: app/build/outputs/apk/debug/app-debug.apk
          retention-days: 30
