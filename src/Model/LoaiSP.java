package Model;

import java.time.LocalDate;

public class LoaiSP {
    private int id;
    private String tenLoai;
    private LocalDate ngayTao;

    public LoaiSP(int id, String tenLoai, LocalDate ngayTao) {
        this.id = id;
        this.tenLoai = tenLoai;
        this.ngayTao = ngayTao;
    }

    // Getter và setter cho các trường

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }
}
