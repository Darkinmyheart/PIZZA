package Model;

import java.time.LocalDate;

public class KhachHang {
    private int id;
    private String ten;
    private String gioiTinh;
    private String sdt;
    private int diemTichLuy;
    private LocalDate ngayTao;
    private LocalDate ngayKetThuc;

    public KhachHang(int id, String ten, String gioiTinh, String sdt, int diemTichLuy, LocalDate ngayTao, LocalDate ngayKetThuc) {
        this.id = id;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.diemTichLuy = diemTichLuy;
        this.ngayTao = ngayTao;
        this.ngayKetThuc = ngayKetThuc;
    }

    // Getter và setter cho các trường

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}
