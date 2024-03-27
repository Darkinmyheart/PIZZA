package Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SanPham {
    private int id;
    private String tenSP;
    private int idLoai;
    private String dvt;
    private int SoLuong;
    private BigDecimal donGia; 
    private LocalDate ngayTao;
    private String moTa;
    private LocalDate ngayKetThuc;

    public SanPham(int id, String tenSP, int idLoai, String dvt, int SoLuong, BigDecimal donGia, LocalDate ngayTao, String moTa, LocalDate ngayKetThuc) {
        this.id = id;
        this.tenSP = tenSP;
        this.idLoai = idLoai;
        this.dvt = dvt;
        this.SoLuong = SoLuong;
        this.donGia = donGia;
        this.ngayTao = ngayTao;
        this.moTa = moTa;
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

}
