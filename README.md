# BelajarYok

BelajarYok adalah aplikasi pembelajaran bahasa interaktif untuk platform Android yang membantu pengguna belajar bahasa dengan cara yang menyenangkan melalui permainan kata, latihan pengucapan, dan sistem level yang progressif.

**Fitur Utama**
- Multi-bahasa: Dukungan untuk Bahasa Indonesia dan Bahasa Inggris
- Game Pembelajaran: Permainan terjemahan kata untuk meningkatkan kosakata
- Fitur Pengucapan: Dengarkan pengucapan kata yang benar dengan Text-to-Speech
- Sistem Level & Poin: Progres belajar yang terukur dengan sistem level dan poin
- Mode Gelap/Terang: Antarmuka yang dapat disesuaikan untuk kenyamanan pengguna
- Penyimpanan Data Lokal: Menyimpan progress pengguna secara lokal

**Cara Penggunaan**

1. Registrasi/Login
  - Saat pertama kali membuka aplikasi, masukkan nama pengguna untuk membuat profil
  - Pilih bahasa yang ingin dipelajari (Indonesia atau Inggris)

2. Halaman Utama

  - Lihat jalur pembelajaran dengan level yang tersedia
  - Tap pada tombol level untuk memulai permainan pembelajaran
  - Pantau jumlah poin dan hati yang dimiliki di bagian atas layar

3. Permainan Kata
  - Baca soal terjemahan yang diberikan
  - Pilih kata-kata yang tersedia untuk menyusun jawaban yang benar
  - Tekan "PERIKSA" untuk mengecek jawaban
  - Dapatkan poin untuk jawaban yang benar
  - Kehilangan hati untuk jawaban yang salah

4. Fitur Ucapan

  - Navigasi ke tab "Ucap" menggunakan bottom navigation
  - Pilih kata atau frasa untuk mendengar pengucapan yang benar

5. Mode Gelap/Terang
  - Gunakan tombol toggle di pojok kanan atas untuk mengubah mode tampilan

**Implementasi Teknis**
1. Teknologi dan Library yang Digunakan
  - Bahasa Pemrograman: Java
  - Database Lokal: SQLite untuk penyimpanan data pengguna dan progress
  - Komunikasi API: Retrofit untuk mengambil data dari server
  - Text-to-Speech: Android TextToSpeech API untuk fitur pengucapan

2. UI Components:
  - NafisBottomNavigation untuk navigasi bawah
  - Material Design Components untuk UI yang konsisten
  - RecyclerView untuk menampilkan daftar data
    
3. Arsitektur Aplikasi
  - Model: Kelas seperti Game, Ucap, Bahasa untuk representasi data
  - View: Layout XML untuk tampilan seperti activity_main.xml, playgameactivity.xml
  - Controller/Presenter: Activity dan Fragment seperti MainActivity, HomeFragment, UcapFragment

4. Data Source:
  - SqlHelper untuk akses database lokal
  - RetrofitClient dan GameService untuk akses API

5. API Endpoint
Aplikasi menggunakan API dari api.chaerul.biz.id dengan endpoint:

  - /games - Mendapatkan data permainan
  - /bahasa - Mendapatkan data untuk fitur pengucapan
    

**Catatan Pengembangan**
Aplikasi ini dikembangkan sebagai proyek final lab dan masih dalam pengembangan aktif. Fitur-fitur baru dan perbaikan bug akan terus ditambahkan dalam rilis mendatang.


Dokumentasi ini akan terus diperbarui seiring dengan perkembangan aplikasi.
