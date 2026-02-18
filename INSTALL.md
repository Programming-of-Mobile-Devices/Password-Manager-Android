<p align="center">
  <img src="https://www.especial.gr/wp-content/uploads/2019/03/panepisthmio-dut-attikhs.png" alt="University of West Attica" width="150"/>
</p>

<p align="center">
  <strong>UNIVERSITY OF WEST ATTICA</strong><br/>
  SCHOOL OF ENGINEERING<br/>
  DEPARTMENT OF COMPUTER ENGINEERING AND INFORMATICS
</p>

<p align="center">
  <a href="https://www.uniwa.gr" target="_blank">University of West Attica</a> ·
  <a href="https://ice.uniwa.gr" target="_blank">Department of Computer Engineering and Informatics</a>
</p>

<hr/>

<p align="center">
  <strong>Technology and Programming of Mobile Devices</strong>
</p>

<h1 align="center" style="letter-spacing: 1px;">
  Password Manager on Java Android
</h1>

<p align="center">
  <strong>Vasileios Evangelos Athanasiou</strong><br/>
  Student ID: 19390005
</p>

<p align="center">
  <a href="https://github.com/Ath21" target="_blank">GitHub</a> ·
  <a href="https://www.linkedin.com/in/vasilis-athanasiou-7036b53a4/" target="_blank">LinkedIn</a>
</p>

<p align="center">
  <strong>Panagiotis Petropoulos</strong><br>
  Student ID: 20390188
</p>

<p align="center">
  <a href="https://github.com/PanosPetrop" target="_blank">GitHub</a> 
</p>

<hr/>

<p align="center">
  <strong>Supervision</strong>
</p>

<p align="center">
  Supervisor: Nikitas Karanikolas, Profesor<br>
</p>

<p align="center">
  <a href="https://ice.uniwa.gr/en/emd_person/nikitas-karanikolas/" target="_blank">UNIWA Profile</a>
</p>

<p align="center">
  Co-supervisor: Georgios Meletiou, Laboratory Teaching Staff<br>
</p>

<p align="center">
  <a href="https://ice.uniwa.gr/en/emd_person/georgios-meletiou/" target="_blank">UNIWA Profile</a>
</p>

</hr>

---

<p align="center">
  Athens, September 2024
</p>

---

<p align="center">
  <img src="https://i.ytimg.com/vi/OXpBCAmhfWg/maxresdefault.jpg" width="250"/>
</p>

---

# INSTALL

## Password Manager on Java Android

This guide will help you set up, build, and run the **Android Password Manager** on your local machine or device.

---

## 1. Prerequisites

### 1.1 Required Software

- **Android Studio** (latest stable version)  
  Download: [https://developer.android.com/studio](https://developer.android.com/studio)
- **Java JDK** (≥ 11 recommended)  
  Android Studio usually comes with an embedded JDK.
- **Android SDK & Emulator / Device**  
  Ensure the required SDK versions are installed via Android Studio SDK Manager.
- **Git** (optional, for cloning the repository)

### 1.2 Optional Tools

- Physical Android device (with developer mode enabled)
- USB debugging enabled on the device
- Code editor for Java source inspection (VSCode, IntelliJ IDEA)

---

## 2. Clone the Repository

```bash
git clone https://github.com/Programming-of-Mobile-Devices/Password-Manager-Android.git
cd Password-Manager-Android
```

This will create a local copy of the project with the standard Android project structure:

```bash
Safe/
├─ app/
├─ build.gradle
├─ gradle/
├─ gradlew
├─ gradlew.bat
└─ settings.gradle
```

## 3. Open Project in Android Studio

1. Launch Android Studio.
2. Click Open an Existing Project.
3. Navigate to the `Safe/` directory and select it.
4. Android Studio will automatically sync the Gradle files and configure the project.
   > Ensure that Gradle sync completes successfully. Resolve any missing SDK or dependency prompts.

## 4. Configure SDK & Emulator

1. Open **File → Project Structure → Modules → app → SDK Location** and ensure the SDK path is correct.
2. Use AVD Manager to create an Android Virtual Device (emulator) or connect a physical device.
3. Enable USB Debugging on your physical device if used.
4. **Important**: Disable Google Play Protect on your device, because the app uses an old SDK which may be flagged as unsafe by Google.

## 5. Build the Project

1. Select **Build → Make** Project or press Ctrl+F9 (Windows/Linux) or Cmd+F9 (macOS).
2. Wait until Gradle finishes compiling and generating the APK.
3. Verify that `app/build/outputs/apk/debug/app-debug.apk` is created.

## 6. Run the Application

### 6.1 Using Emulator

1. Select the emulator from the device dropdown in Android Studio.
2. Click **Run → Run 'app'** or press **Shift+F10**.
3. The app will install and launch automatically on the emulator.

### 6.2 Using Physical Device

1. Connect your device via USB and enable debugging.
2. Select the device in Android Studio.
3. Click **Run → Run 'app'** to install and launch.

## 7. Permissions & Storage

The app requires external storage permissions to read/write encrypted password files:

- On Android 6.0+ (API ≥ 23), permissions are requested at runtime.
- Allow storage permissions when prompted.
  > If denied, the app cannot access saved password files.

## 8. Using the App

- **Add Entries**: Create password records with owner, system, username, and notes.
- **Search**: Use the real-time search bar or advanced filter dialog.
- **Sort**: Apply sorting on any field via the sorting dialog.
- **File Handling**: Open, save, or encrypt password files using the app interface.

## 9. Troubleshooting

- **Gradle Sync Errors**: Update Gradle and Android Studio to the latest versions.
- **Emulator Not Launching**: Ensure HAXM or Intel VT-x / AMD-V is enabled in BIOS.
- **Permission Issues**: Check Android settings → Apps → Password Manager → Permissions.
- App Crashes on Older Android Versions: Minimum SDK version supported: check `app/build.gradle`.
- **Google Play Protect**: If installation is blocked, temporarily disable Google Play Protect as described in Step 4.
