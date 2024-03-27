package Model;

public class HoaDonCT {
    private String idHoaDon;
    private int idSp;
    private int soLuong;

    public HoaDonCT(String idHoaDon, int idSp, int soLuong) {
        this.idHoaDon = idHoaDon;
        this.idSp = idSp;
        this.soLuong = soLuong;
    }

    // Getter và setter cho các trường

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public int getIdSp() {
        return idSp;
    }

    public void setIdSp(int idSp) {
        this.idSp = idSp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
