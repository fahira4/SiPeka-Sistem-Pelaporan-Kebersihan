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
### 1. Encapsulation (Enkapsulasi)
Menyembunyikan detail internal suatu objek dan hanya menampilkan bagian yang dibutuhkan melalui getter/setter atau method publik. Tujuannya untuk menjaga keamanan data dan memisahkan implementasi dari antarmuka pengguna. Implementasinya ada pada semua class dalam *model, controller, dan beberapa di tampilan*
Contoh pada class:
- Pengguna.java
memiliki atribut seperti username, password, namaLengkap yang diakses melalui getter/setter.
- RT.java
menyimpan data namaRT, jumlahPoin, dan metode getter/setter untuk mengaksesnya.
- Gamifikasi.java, JadwalPengangkutan.java, NotifikasiJadwal.java, dll
semuanya menyembunyikan data dengan private dan menggunakan method publik untuk akses.
dan lain-lain (DashboardController, HalamanLogin, LayananNotifikasi, PengendaliForum, daftarJadwal, daftarPengguna, dan gamifikasi).

### 2. Inheritance (Pewarisan)
Konsep di mana sebuah class dapat mewarisi sifat dan perilaku (atribut dan method) dari class lain. Ini memungkinkan code reuse dan struktur hierarki antar class, contohnya: class Mobil extends Kendaraan.
Contoh pada class:
- FormLaporanSampah.java
*FormLaporanSampah extends VBox* ini adalah bentuk pewarisan dari class JavaFX VBox, sehingga FormLaporanSampah otomatis memiliki properti dan method layout milik VBox.

### 3. Polymorphism (Polimorfisme)
Kemampuan objek untuk merespons secara berbeda terhadap method yang sama, tergantung jenis objeknya. Misalnya, method bergerak() dapat memiliki implementasi berbeda di class Mobil dan Motor.
Contoh pada class:
- LayananNotifikasi.java
Method overload atau pengiriman notifikasi melalui method yang sama (tampilkanInfo vs tampilkanError) menunjukkan polimorfisme melalui nama method yang sama dengan perilaku berbeda.
- FormLaporanSampah.java dan FormRegistrasi.java
Event handler tombol seperti:
*simpanButton.setOnAction(e -> { ... });*
Di sini, setOnAction menerima objek dari interface EventHandler, dan lambda expression e -> { ... } adalah bentuk polimorfik dari handler.

Pada method tambahKomentar, hapusPost, dll, 
### 4. Abstraction (Abstraksi)
Menyembunyikan kompleksitas dan hanya menampilkan fitur penting suatu objek. Biasanya digunakan dengan abstract class atau interface untuk mendefinisikan kerangka umum tanpa implementasi detail.
Contoh pada class:
- LayananNotifikasi.java
Menyediakan metode tampilkanInfo, tampilkanError tanpa perlu tahu cara Alert JavaFX bekerja.
- PengendaliForum.java
Method tambahPost, hapusPost, dll, menyederhanakan cara kerja forum ke tingkat yang mudah digunakan komponen UI.
- DashboardController.java dan HalamanLogin.java
Hanya menyajikan logika visual tanpa detail model-data (abstraksi UI-logic).

---
## ⚙️ Cara Menjalankan Aplikasi
1. Pastikan perangkat sudah terpasang : Java, JavaFX SDK, Gradle
2. Clone repositori
   ```sh
   git clone https://github.com/fahira4/SiPeka-Sistem-Pelaporan-Kebersihan
   ```
   Masuk ke direktori
   ```sh
   cd SiPeka-Sistem-Pelaporan-Kebersihan
   ```
3. Build dan jalankan dengan gradle
   Jalankan aplikasi dengan perintah berikut
   ```sh
   ./gradlew run
   ```
## Author
1. Github: [fahira4](https://github.com/fahira4)
2. Github: [rnratika](https://github.com/rnratika)
3. Github: [izzasyathra](https://github.com/izzasyathra)

## MIT License
Copyright (c) 2025 Tim SiPeka
