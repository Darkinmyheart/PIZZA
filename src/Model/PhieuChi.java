package Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PhieuChi {
    private String id;
    private int idNguoiLap;
    private int idNguoiNhan;
    private LocalDate ngayLap;
    private String lyDo;
    private BigDecimal tongTien;

    public PhieuChi(String id, int idNguoiLap, int idNguoiNhan, LocalDate ngayLap, String lyDo, BigDecimal tongTien) {
        this.id = id;
        this.idNguoiLap = idNguoiLap;
        this.idNguoiNhan = idNguoiNhan;
        this.ngayLap = ngayLap;
        this.lyDo = lyDo;
        this.tongTien = tongTien;
    }

    public PhieuChi() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    public int getIdNguoiNhan() {
        return idNguoiNhan;
    }

    public void setIdNguoiNhan(int idNguoiNhan) {
        this.idNguoiNhan = idNguoiNhan;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
}
