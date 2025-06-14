package test.controller;

import test.model.Pengguna;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PengendaliLogin {
    private Map<String, Pengguna> daftarPengguna;
    private final String FILE_PATH = "data_pengguna.txt";

    public PengendaliLogin() {
        daftarPengguna = new HashMap<>();
        muatDariFile(); // baca dari file saat inisialisasi
    }

    public Pengguna autentikasi(String username, String password) {
        Pengguna pengguna = daftarPengguna.get(username);
        if (pengguna != null && pengguna.login(username, password)) {
            return pengguna;
        }
        return null;
    }

    public boolean daftar(String username, String password, String rt) {
        if (daftarPengguna.containsKey(username)) {
            return false; // Username sudah ada
        }

        // Validasi RT: hanya boleh "RT 1", "RT 2", atau "RT 3"
        if (!rt.matches("RT [1-3]")) {
            throw new IllegalArgumentException("RT harus berupa RT 1, RT 2, atau RT 3");
        }

        Pengguna penggunaBaru = new Pengguna(username, password, rt);
        daftarPengguna.put(username, penggunaBaru);
        simpanKeFile();
        return true;
    }

    private void muatDariFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                String[] bagian = baris.split(",");
                if (bagian.length >= 3) {
                    String username = bagian[0];
                    String password = bagian[1];
                    String rt = bagian[2];
                    Pengguna pengguna = new Pengguna(username, password, rt);
                    daftarPengguna.put(username, pengguna);
                }
            }
        } catch (IOException e) {
            // file tidak ditemukan? abaikan saja
        }
    }

    private void simpanKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Pengguna pengguna : daftarPengguna.values()) {
                writer.write(pengguna.getUsername() + "," +
                             pengguna.getPassword() + "," +
                             pengguna.getRT());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
