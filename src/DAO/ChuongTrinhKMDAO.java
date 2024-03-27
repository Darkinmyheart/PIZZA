package DAO;

import Conn.Conn;
import Model.ChuongTrinhKM;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class ChuongTrinhKMDAO implements DAO<ChuongTrinhKM> {

    @Override
    public ArrayList<ChuongTrinhKM> getArrayListAll() {
        ArrayList<ChuongTrinhKM> ds = new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from chuongtrinhkm");
            while (rs.next()) {

                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate NgayKetThuc = null;
                if (ngayketthuc != null) {
                    NgayKetThuc = ngayketthuc.toLocalDate();
                }
                ds.add(new ChuongTrinhKM(rs.getString("ID"), rs.getString("tenct"), rs.getString("mota"), rs.getDate("ngaybatdau").toLocalDate(),
                        NgayKetThuc, rs.getInt("phantramgiam")));

            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    @Override
    public ChuongTrinhKM getObjectById(String id) {
        ChuongTrinhKM ct = new ChuongTrinhKM();
        try {
            Connection conn = new Conn().getConnection();
            PreparedStatement prep = conn.prepareStatement("select * from ChuongTrinhKM where id like ?");
            prep.setString(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate NgayKetThuc = null;
                if (ngayketthuc != null) {
                    NgayKetThuc = ngayketthuc.toLocalDate();
                }
                ct = new ChuongTrinhKM(rs.getString("ID"), rs.getString("tenct"), rs.getString("mota"), rs.getDate("ngaybatdau").toLocalDate(),
                        NgayKetThuc, rs.getInt("phantramgiam"));

            }
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ct;
    }

    @Override
    public int UpdateSQL(ChuongTrinhKM object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int InsertSQL(ChuongTrinhKM o) {
        int check = 0;
//            private String id;
//    private String tenCT;
//    private String moTa;
//    private LocalDate ngayBatDau;
//    private LocalDate ngayKetThuc;
//    private int phanTramGiam;
        try {
            Connection conn = new Conn().getConnection();
            String sql = "insert into chuongtrinhkm values(?,?,?,?,?,?)";
            PreparedStatement prep = conn.prepareStatement(sql);

            prep.setString(1, o.getId());
            prep.setString(2, o.getTenCT());
            prep.setString(3, o.getMoTa());
            prep.setDate(4, Date.valueOf(o.getNgayBatDau()));
            prep.setDate(5, Date.valueOf(o.getNgayKetThuc()));
            prep.setInt(6, o.getPhanTramGiam());

            check = prep.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

}
