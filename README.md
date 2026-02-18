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

# README

## Password Manager on Java Android

This project is based on an existing **Java Android Password Manager** application that was provided as course material.  
The goal of the assignment was to **improve the application**, add new functionality, and fix usability issues.

The final result is an enhanced password management application with search, sorting, and improved file access handling.

---

## Table of Contents

|     Section | Folder/File           | Description                                |
| ----------: | --------------------- | ------------------------------------------ |
|           1 | `Safe/`               | Android project root directory             |
|         1.2 | `app/`                | Main Android application module            |
|       1.2.1 | `app/src/`            | Application source code and resources      |
|     1.2.1.1 | `main/java/`          | Core Java source files                     |
|     1.2.1.2 | `main/res/`           | UI layouts, images, menus, and resources   |
|     1.2.1.3 | `AndroidManifest.xml` | Application configuration                  |
|       1.2.2 | `app/build/`          | Generated build outputs and intermediates  |
| executables |
|         1.3 | `local.properties`    | Local SDK configuration (machine-specific) |
|           2 | `README.md`           | Project documentation                      |
|           3 | `INSTALL.md`          | Usage instructions                         |

---

## 1. Project Overview

The application allows users to securely store and manage passwords and account information.  
Our work focused on improving usability and extending functionality without changing the original architecture.

Enhancements include:

- Real-time search functionality
- Advanced search with filters
- Record sorting options
- Improved storage permissions handling
- UI interaction improvements and bug fixes

---

## 2. Project Goal

The assignment required students to:

- Work on an existing Android application
- Understand and extend an unfamiliar codebase
- Implement new features
- Improve user interaction
- Fix functional or UI problems

---

## 3. Features Added / Improved

### 3.1 Search Bar Filtering

A real-time search bar was added to filter stored records dynamically while the user types.

Implemented in:

- `MainActivity`
- `MyAdapter` filtering logic

---

### 3.2 Advanced Search Dialog

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

### 3.3 Sorting Functionality

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

### 3.4 Storage Permission Handling

The app now properly requests and verifies **external storage permissions**, ensuring correct file access for:

- Opening password files
- Saving encrypted data
- File encryption/decryption

---

### 3.5 Adapter Improvements

Enhancements were added in `MyAdapter`:

- Dataset filtering support
- Sorting operations
- View refresh control
- Better selection handling

---

### 3.6 UI and Interaction Fixes

Improvements include:

- Better dialog interaction handling
- Improved list selection behavior
- Stability improvements during dataset updates

---

## 4. Technologies Used

- Java
- Android SDK
- Android Studio
- ListView & Custom Adapter
- Dialog Interfaces
- File Handling & Permissions

---

## 5. Learning Outcomes

Through this project we practiced:

- Working with legacy codebases
- Extending Android applications
- Implementing adapters and filtering logic
- Handling permissions and file operations
- UI dialog interaction design

---

## 6. Conclusion

The project demonstrates how an existing Android application can be extended with modern usability features while maintaining its original functionality.

The resulting application provides improved data management, search, and interaction for users handling sensitive password information.
