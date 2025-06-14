package test.database;

import test.model.RT;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RankingRTDAO {
    private Connection connect() {
        // Ubah path sesuai dengan lokasi database kamu
        String url = "jdbc:sqlite:C:\\Users\\LENOVO\\Documents\\SISTEM INFORMASI\\SEMESTER 2\\PBO\\FINAL\\SiPeka-Sistem-Pelaporan-Kebersihan\\app\\src\\main\\java\\test\\database\\SiPeka.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("Gagal konek DB: " + e.getMessage());
        }
        return conn;
    }

    public ObservableList<RT> getRanking() {
        ObservableList<RT> listRanking = FXCollections.observableArrayList();
String sql = "SELECT `rt`, SUM(`poin`) as total_poin " +
             "FROM `pengguna` " +
             "GROUP BY `rt` " +
             "ORDER BY total_poin DESC";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String rt = rs.getString("RT");
                int poin = rs.getInt("total_poin");

                RT dataRT = new RT(rt, poin); // level dihitung otomatis di konstruktor RT
                listRanking.add(dataRT);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil ranking RT: " + e.getMessage());
        }

        return listRanking;
    }
}
