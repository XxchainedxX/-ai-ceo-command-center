AI CEO Command Center — Android APK project
=============================================

What this package is
--------------------
This is the Android Studio source project for a small Android app that wraps the
existing AI CEO Command Center dashboard in a WebView. It includes an offline copy
of the dashboard and a native CSV-export bridge that saves exports to Downloads.

Important honesty note
----------------------
This ZIP is SOURCE CODE, not an already-compiled APK. The current chat runtime does
not have a configured Android SDK/Gradle build environment, so it has not compiled
or been tested as an installable APK here. Use the free GitHub Actions workflow below
to build a debug APK in the cloud, or use Android Studio on a computer.

This app is only a local dashboard prototype. It does not contact prospects, run an
AI model, sign documents, process customer payments, or connect to a bank.

Easiest free build route (using a Google/GitHub account and any browser)
-----------------------------------------------------------------------
1. Download and extract this ZIP on a computer if possible.
2. Create a new PUBLIC GitHub repository (public repos can use standard GitHub-hosted
   Actions runners for free, subject to GitHub's current terms/limits).
3. Upload the contents of this extracted project to the root of that repository,
   including the hidden .github folder and its workflows/build-apk.yml file.
4. Open the repository's Actions tab. If prompted, enable workflows.
5. Choose "Build AI CEO APK" and click "Run workflow". Or push/commit to the main branch.
6. Wait for the workflow to finish successfully.
7. Open the completed workflow run and download the artifact named
   ai-ceo-command-center-debug-apk.
8. Extract the downloaded artifact ZIP. It contains app-debug.apk.
9. Transfer app-debug.apk to your Android phone and tap it to install.
10. If Android asks, allow your browser or file manager to install unknown apps for
    this file, then continue. Only install APKs you built or trust.

GitHub Actions artifact downloads are temporary (workflow sets 14-day retention).
The workflow builds a DEBUG APK for personal testing. Do not publish this debug build
as a public production app. A release APK should be signed with a private signing key.

Build on a computer with Android Studio
---------------------------------------
Open this project folder in Android Studio, let Gradle sync, then choose:
Build > Build Bundle(s) / APK(s) > Build APK(s).
The APK will be under app/build/outputs/apk/debug/app-debug.apk.

Data storage and privacy
------------------------
Dashboard records are stored in WebView local storage on this device/app installation.
Uninstalling the app or clearing its data may remove records. Use Export CSV regularly.
Do not enter bank passwords, payment card numbers, or highly sensitive client data.
