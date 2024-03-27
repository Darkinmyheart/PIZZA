/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.HoaDon;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class HoaDonDAO implements DAO<HoaDon> {

    @Override
    public ArrayList<HoaDon> getArrayListAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public HoaDon getObjectById(String x) {
        HoaDon hd = null;

        try {
            Connection conn = new Conn.Conn().getConnection();
            PreparedStatement prep = conn.prepareStatement("select * from hoadon where id= ?");
            prep.setString(1, x);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                hd = new HoaDon(rs.getString("id"), rs.getInt("idnguoilap"), rs.getInt("idkhachhang"),
                         rs.getDate("ngaylap").toLocalDate(), rs.getBigDecimal("thanhtien"), rs.getInt("diemtichluy"),
                         rs.getString("mactkm"), rs.getDate("ngayketthuc") == null ? null : rs.getDate("ngayketthuc").toLocalDate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hd;

    }

    @Override
    public int UpdateSQL(HoaDon object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int InsertSQL(HoaDon o) {
        int check = 0;
        try {
            Connection conn = new Conn.Conn().getConnection();
            String prep = "insert into hoadon values (?,?,?,?,?,?,?,?)";
            PreparedStatement prepsql = conn.prepareStatement(prep);
            prepsql.setString(1, o.getId());
            prepsql.setInt(2, o.getIdNguoiLap());
            if (o.getIdKhachHang() != -1) {
                prepsql.setInt(3,o.getIdKhachHang());
            }else{
                prepsql.setNull(3,Types.INTEGER);
            }
            prepsql.setDate(4, java.sql.Date.valueOf(o.getNgayLap()));
            prepsql.setBigDecimal(5, o.getThanhTien());
            prepsql.setInt(6, o.getDiemTichLuy());
            prepsql.setString(7, o.getMaCTKM());
            if (o.getNgayKetThuc() != null) {
                prepsql.setDate(8, java.sql.Date.valueOf(o.getNgayKetThuc()));
            } else {
                prepsql.setDate(8, null);
            }
            check = prepsql.executeUpdate();
            prepsql.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public ArrayList<HoaDon> getArrayListByTime(Date From, Date To) {
        ArrayList<HoaDon> ds = new ArrayList<>();
        try {
            Connection conn = new Conn.Conn().getConnection();
            CallableStatement prep = conn.prepareCall("{CALL TimKiem_HoaDon_TheoNgay (?,?)}");
            prep.setDate(1, From);
            prep.setDate(2, To);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(rs.getString("id"), rs.getInt("idnguoilap"), rs.getInt("idkhachhang"),
                         rs.getDate("ngaylap").toLocalDate(), rs.getBigDecimal("thanhtien"), rs.getInt("diemtichluy"),
                         rs.getString("mactkm"), rs.getDate("ngayketthuc") == null ? null : rs.getDate("ngayketthuc").toLocalDate());
                ds.add(hd);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    
}
