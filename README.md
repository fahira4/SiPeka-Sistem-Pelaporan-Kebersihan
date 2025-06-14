# 📑 SiPeka - Sistem Pelaporan Kebersihan
Masalah sampah yang tidak terkelola dengan baik, seperti penumpukan sampah liar dan keterlambatan pengangkutan, masih sering terjadi di lingkungan masyarakat. Hal ini disebabkan oleh kurangnya sistem pelaporan yang efektif antara warga dan petugas kebersihan. Untuk mengatasi hal tersebut, dikembangkanlah SiPeka (Sistem Pelaporan Kebersihan). SiPeka (Sistem Pelaporan Kebersihan) adalah aplikasi desktop berbasis JavaFX yang memungkinkan warga untuk melaporkan masalah kebersihan dan pengelolaan sampah di lingkungan mereka. Aplikasi ini menyediakan antarmuka yang mudah digunakan untuk mengelola pengaduan sampah secara efektif. SiPeka bertujuan untuk efisiensi pengelolaan kebersihan lingkungan,diantaranya :
1.	Meningkatkan Partisipasi Masyarakat dalam Pengelolaan Kebersihan
2.	Mempermudah Pengelolaan Jadwal Pengangkutan Sampah
3.	Mempercepat Proses Tindak Lanjut Laporan Sampah
4.	Mendorong Budaya Bersih Melalui Gamifikasi
5.	Menyediakan Forum Diskusi Warga
---
## 📂 Struktur Kode
```
SIPEKA/
├───main
│   ├───java
│   │   └───test
│   │       │   Main.java
│   │       │
│   │       ├───connector
│   │       │       dbConnector.java
│   │       │
│   │       ├───controller
│   │       │       DashboardController.java
│   │       │       HalamanLogin.java
│   │       │       LayananNotifikasi.java
│   │       │       PengendaliForum.java
│   │       │       PengendaliGamifikasi.java
│   │       │       PengendaliJadwal.java
│   │       │       PengendaliLogin.java
│   │       │
│   │       ├───database
│   │       │       RankingRTDAO.java
│   │       │       SiPeka.db
│   │       │
│   │       ├───helper
│   │       │       session.java
│   │       │
│   │       ├───model
│   │       │       AcaraEdukasi.java
│   │       │       ForumDiskusi.java
│   │       │       Gamifikasi.java
│   │       │       JadwalPengangkutan.java
│   │       │       NotifikasiJadwal.java
│   │       │       Pengguna.java
│   │       │       RT.java
│   │       │       StatusLaporan.java
│   │       │
│   │       └───tampilan
│   │               FormLaporanSampah.java
│   │               FormRegistrasi.java
│   │               ForumWarga.java
│   │               GamifikasiView.java
│   │               HalamanLogin.java
│   │               HalamanUtama.java
│   │               NotifikasiJadwal.java
│   │               PointRTView.java
│   │               TampilanJadwalPengangkutan.java
│   │
│   └───resources
│       └───test
│           └───fxml
│                   dashboard.fxml
│                   login.fxml
│
└───test
    ├───java
    │   └───test
    │           AppTest.java
    │
    └───resources
```
---
## 🔎 Implementasi OOP
### 1. Encapsulation
Setiap class model seperti Pengguna, LaporanSampah, dan StatistikLaporan membungkus data dalam atribut privat dan menyediakan akses melalui method. Ini menjaga keamanan dan integritas data.

### 2. Inheritance
Struktur class mendukung pewarisan sifat dari class umum seperti Pengguna.

### 3. Polymorphism
Beberapa class GUI seperti MenuUtama, PanelAdmin, dan ForumWarga memiliki method yang sama namun menampilkan hasil atau tampilan yang disesuaikan dengan pengguna masing-masing.

### 4. Abstraction
Fungsi kompleks seperti penyimpanan data (PenyimpananData.java) dan layanan notifikasi (LayananNotifikasi.java) disederhanakan melalui interface method yang mudah digunakan tanpa perlu memahami logika internalnya.

---
## ⚙️ Cara Menjalankan Aplikasi
1. Pastikan perangkat sudah terpasang : Java, JavaFX SDK, Gradle
2. Clone repositori
   ```sh
   git clone https://github.com/fahira4/SiPeka-Sistem-Pelaporan-Kebersihan
   ```
   Masuk ke direktori
   ```sh
   cd SiPeka
   ```
3. Build dan jalankan dengan gradle
   Jalankan aplikasi dengan perintah berikut
   ```sh
   ./gradlew run
   ```
## Author
1. Github: fahira4
2. Github: rnratika
3. Github: izzasyathra

## MIT License
Copyright (c) 2025 Tim SiPeka
