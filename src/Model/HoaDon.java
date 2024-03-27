package Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HoaDon {
    private String id;
    private int idNguoiLap;
    private int idKhachHang;
    private LocalDate ngayLap;
    private BigDecimal thanhTien;
    private int diemTichLuy;
    private String maCTKM;
    private LocalDate ngayKetThuc;

    public HoaDon(String id, int idNguoiLap, int idKhachHang, LocalDate ngayLap, BigDecimal thanhTien, int diemTichLuy, String maCTKM, LocalDate ngayKetThuc) {
        this.id = id;
        this.idNguoiLap = idNguoiLap;
        this.idKhachHang = idKhachHang;
        this.ngayLap = ngayLap;
        this.thanhTien = thanhTien;
        this.diemTichLuy = diemTichLuy;
        this.maCTKM = maCTKM;
        this.ngayKetThuc = ngayKetThuc;
    }

    public HoaDon() {
    }

    // Getter và setter cho các trường

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdNguoiLap() {
        return idNguoiLap;
    }

    public void setIdNguoiLap(int idNguoiLap) {
        this.idNguoiLap = idNguoiLap;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public String getMaCTKM() {
        return maCTKM;
    }

    public void setMaCTKM(String maCTKM) {
        this.maCTKM = maCTKM;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    @Override
    public String toString() {
        return "HoaDon{" +
                "id='" + id + '\'' +
                ", idNguoiLap=" + idNguoiLap +
                ", idKhachHang=" + idKhachHang +
                ", ngayLap=" + ngayLap +
                ", thanhTien=" + thanhTien +
                ", diemTichLuy=" + diemTichLuy +
                ", maCTKM='" + maCTKM + '\'' +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
