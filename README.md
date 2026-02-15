<p align="center">
  <img src="https://www.especial.gr/wp-content/uploads/2019/03/panepisthmio-dut-attikhs.png" alt="University of West Attica" width="150"/>
</p>

<p align="center">
  <strong>UNIVERSITY OF WEST ATTICA</strong><br/>
  SCHOOL OF ENGINEERING<br/>
  DEPARTMENT OF COMPUTER ENGINEERING AND INFORMATICS
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

<hr/>

<p align="center">
  Athens, September 2024
</p>

---

# Password Manager – Android App

This project is based on an existing **Java Android Password Manager** application that was provided as course material.  
The goal of the assignment was to **improve the application**, add new functionality, and fix usability issues.

The final result is an enhanced password management application with search, sorting, and improved file access handling.

---

## Table of Contents

| Section | Folder/File | Description |
|------:|-------------|-------------|
| 1 | `Safe/` | Android project root directory |
| 1.1 | `.gradle/` | Gradle cache and build system data |
| 1.2 | `.idea/` | Android Studio project configuration files |
| 1.3 | `app/` | Main Android application module |
| 1.3.1 | `app/src/` | Application source code and resources |
| 1.3.1.1 | `main/java/` | Core Java source files |
| 1.3.1.2 | `main/res/` | UI layouts, images, menus, and resources |
| 1.3.1.3 | `AndroidManifest.xml` | Application configuration |
| 1.3.2 | `app/build/` | Generated build outputs and intermediates |
| 1.3.3 | `build.gradle` | Module build configuration |
| 1.4 | `gradle/wrapper/` | Gradle wrapper binaries and configuration |
| 1.5 | `build.gradle` | Project-level Gradle configuration |
| 1.6 | `gradle.properties` | Gradle environment properties |
| 1.7 | `settings.gradle` | Project module configuration |
| 1.8 | `gradlew` / `gradlew.bat` | Gradle wrapper executables |
| 1.9 | `local.properties` | Local SDK configuration (machine-specific) |
| 2 | `README.md` | Project documentation and usage instructions |

---

## Project Overview

The application allows users to securely store and manage passwords and account information.  
Our work focused on improving usability and extending functionality without changing the original architecture.

Enhancements include:

- Real-time search functionality
- Advanced search with filters
- Record sorting options
- Improved storage permissions handling
- UI interaction improvements and bug fixes

---

## Project Goal

The assignment required students to:

- Work on an existing Android application
- Understand and extend an unfamiliar codebase
- Implement new features
- Improve user interaction
- Fix functional or UI problems

---

## Features Added / Improved

### 1. Search Bar Filtering
A real-time search bar was added to filter stored records dynamically while the user types.

Implemented in:
- `MainActivity`
- `MyAdapter` filtering logic

---

### 2. Advanced Search Dialog
A search dialog was introduced allowing searches using filters such as:

- Owner
- Category
- System
- Account
- Username
- Password
- Notes

This allows more precise record lookup.

---

### 3. Sorting Functionality
A sorting dialog was implemented so users can sort entries based on selected fields.

Sorting can be applied to:

- Owner
- System
- Account
- Username
- Notes
- and other stored fields

Sorting is handled directly inside the adapter dataset.

---

### 4. Storage Permission Handling
The app now properly requests and verifies **external storage permissions**, ensuring correct file access for:

- Opening password files
- Saving encrypted data
- File encryption/decryption

---

### 5. Adapter Improvements
Enhancements were added in `MyAdapter`:

- Dataset filtering support
- Sorting operations
- View refresh control
- Better selection handling

---

### 6. UI and Interaction Fixes
Improvements include:

- Better dialog interaction handling
- Improved list selection behavior
- Stability improvements during dataset updates

---

## Technologies Used

- Java
- Android SDK
- Android Studio
- ListView & Custom Adapter
- Dialog Interfaces
- File Handling & Permissions

---

## Learning Outcomes

Through this project we practiced:

- Working with legacy codebases
- Extending Android applications
- Implementing adapters and filtering logic
- Handling permissions and file operations
- UI dialog interaction design

---

## Conclusion

The project demonstrates how an existing Android application can be extended with modern usability features while maintaining its original functionality.

The resulting application provides improved data management, search, and interaction for users handling sensitive password information.

---

# Installation & Setup Guide 

This guide will help you set up, build, and run the **Android Password Manager** on your local machine or device.  

---

## 1. Prerequisites

### Required Software

- **Android Studio** (latest stable version)  
  Download: [https://developer.android.com/studio](https://developer.android.com/studio)
- **Java JDK** (≥ 11 recommended)  
  Android Studio usually comes with an embedded JDK.
- **Android SDK & Emulator / Device**  
  Ensure the required SDK versions are installed via Android Studio SDK Manager.
- **Git** (optional, for cloning the repository)

### Optional Tools

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
### Using Emulator
1. Select the emulator from the device dropdown in Android Studio.
2. Click **Run → Run 'app'** or press **Shift+F10**.
3. The app will install and launch automatically on the emulator.

### Using Physical Device
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