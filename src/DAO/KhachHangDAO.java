/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.KhachHang;
import java.util.ArrayList;
import Conn.Conn;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class KhachHangDAO implements DAO<KhachHang> {

    @Override
    public ArrayList<KhachHang> getArrayListAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public KhachHang getkhachHangFromSDT(String sdt) {
        KhachHang kh = null;
        try {

            Connection conn = new Conn().getConnection();
            String prep = "select * from khachhang where sdt like ?";
            PreparedStatement prepst = conn.prepareStatement(prep);
            prepst.setString(1, sdt);
            ResultSet rs = prepst.executeQuery();
            while (rs.next()) {
                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate NgayKetThuc = null;
                if (ngayketthuc != null) {
                    NgayKetThuc = rs.getDate("ngayketthuc").toLocalDate();
                }
                kh = new KhachHang(rs.getInt("id"), rs.getString("ten"), rs.getString("gioitinh"), rs.getString("Sdt"),
                         rs.getInt("diemtichluy"), rs.getDate("ngayTao").toLocalDate(), NgayKetThuc);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return kh;
    }

    @Override
    public KhachHang getObjectById(String x) {
        KhachHang kh = null;
        try {

            Connection conn = new Conn().getConnection();
            String prep = "select * from khachhang where id like ?";
            PreparedStatement prepst = conn.prepareStatement(prep);
            prepst.setString(1, x);
            ResultSet rs = prepst.executeQuery();
            while (rs.next()) {

                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate NgayKetThuc = null;
                if (ngayketthuc != null) {
                    NgayKetThuc = rs.getDate("ngayketthuc").toLocalDate();
                }
                kh = new KhachHang(rs.getInt("id"), rs.getString("ten"), rs.getString("gioitinh"), rs.getString("Sdt"),
                         rs.getInt("diemtichluy"), rs.getDate("ngayTao").toLocalDate(), NgayKetThuc);

            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kh;
    }

    @Override
    public int UpdateSQL(KhachHang o) {
        int check = 0;
        try {
            Connection conn = new Conn().getConnection();
            PreparedStatement prep = conn.prepareStatement("Update khachhang set ten=?"
                    + " , gioitinh=?"
                    + ", sdt=?"
                    + ", diemtichluy=?"
                    + ", ngaytao=?"
                    + ", ngayketthuc=?"
                    + " where id=?");

            prep.setString(1, o.getTen());
            prep.setString(2, o.getGioiTinh());
            prep.setString(3, o.getSdt());
            prep.setInt(4, o.getDiemTichLuy());
            prep.setDate(5, Date.valueOf(o.getNgayTao()));
            if (o.getNgayKetThuc() != null) {
                prep.setDate(6, Date.valueOf(o.getNgayKetThuc()));
            } else {
                prep.setDate(6, null);
            }
            prep.setInt(7, o.getId());
            check = prep.executeUpdate();

            prep.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    @Override
    public int InsertSQL(KhachHang o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
