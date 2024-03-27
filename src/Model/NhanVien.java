package Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NhanVien {
    private int id;
    private String tenNV;
    private String gioiTinh;
    private String sdt;
    private String email;
    private String diaChi;
    private String chucVu;
    private BigDecimal luong;
    private LocalDate ngayTao;
    private String pass;
    private LocalDate ngayKetThuc;

    public NhanVien(int id, String tenNV, String gioiTinh, String sdt, String email, String diaChi, String chucVu,
            BigDecimal luong, LocalDate ngayTao, String pass, LocalDate ngayKetThuc) {
        this.id = id;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.luong = luong;
        this.ngayTao = ngayTao;
        this.pass = pass;
        this.ngayKetThuc = ngayKetThuc;
    }

    public NhanVien() {
    }

    // Getter và setter cho các trường

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public BigDecimal getLuong() {
        return luong;
    }

    public void setLuong(BigDecimal luong) {
        this.luong = luong;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }



    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}
