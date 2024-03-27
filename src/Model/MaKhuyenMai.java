package Model;

import java.time.LocalDate;

public class MaKhuyenMai {
    private int id;
    private String idCTKM;
    private String code;
    private LocalDate ngaySuDung;

    public MaKhuyenMai(int id, String idCTKM, String code, LocalDate ngaySuDung) {
        this.id = id;
        this.idCTKM = idCTKM;
        this.code = code;
        this.ngaySuDung = ngaySuDung;
    }

    // Getter và setter cho các trường

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCTKM() {
        return idCTKM;
    }

    public void setIdCTKM(String idCTKM) {
        this.idCTKM = idCTKM;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(LocalDate ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }
}
