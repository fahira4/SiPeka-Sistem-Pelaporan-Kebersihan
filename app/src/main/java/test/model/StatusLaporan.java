package test.model;

public enum StatusLaporan {
    PENDING("Menunggu"),
    SEDANG_DIPROSES("Sedang_Diproses"),
    SELESAI("Selesai"),
    DITOLAK("Ditolak");

    private String deskripsi;

    StatusLaporan(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public static StatusLaporan fromDatabaseValue(String dbValue) {
        return switch (dbValue.toUpperCase()) {
            case "PENDING" -> PENDING;
            case "SEDANG DIPROSES" -> SEDANG_DIPROSES;
            case "SELESAI" -> SELESAI;
            case "DITOLAK" -> DITOLAK;
            default -> throw new IllegalArgumentException("Status tidak dikenali: " + dbValue);
        };
    }

}