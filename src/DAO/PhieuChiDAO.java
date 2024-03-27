package DAO;

import Conn.Conn;
import Model.ChuongTrinhKM;
import Model.PhieuChi;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class PhieuChiDAO implements DAO<Model.PhieuChi> {

    @Override
    public ArrayList<PhieuChi> getArrayListAll() {
        ArrayList<PhieuChi> ds = new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from PhieuChi");
            while (rs.next()) {
                PhieuChi pc = new PhieuChi(rs.getString("ID"), rs.getInt("IDNguoiLap"), rs.getInt("IDNguoiNhan"),
                         rs.getDate("NgayLap").toLocalDate(), rs.getString("lydo"), rs.getBigDecimal("tongtien"));
                ds.add(pc);

            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    @Override
    public PhieuChi getObjectById(String x) {
        PhieuChi pc = new PhieuChi();
        try {
            Connection conn = new Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from PhieuChi");
            while (rs.next()) {
                pc = new PhieuChi(rs.getString("ID"), rs.getInt("IDNguoiLap"), rs.getInt("IDNguoiNhan"),
                         rs.getDate("NgayLap").toLocalDate(), rs.getString("lydo"), rs.getBigDecimal("tongtien"));

            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pc;
    }

    @Override
    public int UpdateSQL(PhieuChi object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int InsertSQL(PhieuChi o) {
        int check = 0;
        try {
            Connection conn = new Conn().getConnection();
            String sql = "INSERT INTO PhieuChi (ID, IDNguoiLap, IDNguoiNhan, NgayLap, LyDo, TongTien) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);

            prep.setString(1, o.getId());
            prep.setInt(2, o.getIdNguoiLap());
            prep.setInt(3, o.getIdNguoiNhan());
            prep.setDate(4, java.sql.Date.valueOf(o.getNgayLap()));
            prep.setString(5, o.getLyDo());
            prep.setBigDecimal(6, o.getTongTien());
            
            check = prep.executeUpdate();
            
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuChiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public ArrayList<PhieuChi> getArrayListBySdtNguoiLap(String sdt, java.sql.Date From, java.sql.Date To) {
        ArrayList<PhieuChi> ds = new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            CallableStatement prep = conn.prepareCall("{CALL TimKiem_PhieuChi (?,?,?)}");
            prep.setString(1, sdt);
            prep.setDate(2, From);
            prep.setDate(3, To);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                PhieuChi pc = new PhieuChi(rs.getString("ID"), rs.getInt("IDNguoiLap"), rs.getInt("IDNguoiNhan"),
                         rs.getDate("NgayLap").toLocalDate(), rs.getString("lydo"), rs.getBigDecimal("tongtien"));
                ds.add(pc);

            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

}
