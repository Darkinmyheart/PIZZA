package DAO;

import java.sql.*;
import Model.*;
import Conn.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class SanPhamDAO implements DAO<SanPham> {

    //new SanPham(rs.getInt("id"), rs.getInt("IDLoai"), rs.getString("tenSP"), rs.getInt("soluong"), rs.getFloat("dongia"), rs.getString("dvt"),rs.getDate("ngaytao"))
    @Override
    public ArrayList<SanPham> getArrayListAll() {
        ArrayList<SanPham> ds = new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select *,tenloai from sanpham inner join loaisp on sanpham.idloai = loaisp.id ");
            while (rs.next()) {
                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate ngayketthucLocalDate = null;

                if (ngayketthuc != null) {
                    ngayketthucLocalDate = ngayketthuc.toLocalDate();
                }
                ds.add(new SanPham(
                        rs.getInt("id"),
                        rs.getString("tensp"),
                        rs.getInt("idloai"),
                        rs.getString("dvt"),
                        rs.getInt("soluong"),
                        rs.getBigDecimal("dongia"),
                        rs.getDate("ngaytao").toLocalDate(),
                        rs.getString("mota"),
                        ngayketthucLocalDate)
                );
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    @Override
    public SanPham getObjectById(String x) {
        int a = Integer.parseInt(x);
        SanPham sp = null;
        try {
            Connection conn = new Conn().getConnection();
            String prep = "select * from sanpham where id = ?";
            PreparedStatement sqlprep = conn.prepareStatement(prep);
            sqlprep.setInt(1, a);
            ResultSet rs = sqlprep.executeQuery();
            while (rs.next()) {

                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate ngayketthucLocalDate = null;

                if (ngayketthuc != null) {
                    ngayketthucLocalDate = ngayketthuc.toLocalDate();
                }
                sp = new SanPham(
                        rs.getInt("id"),
                        rs.getString("tensp"),
                        rs.getInt("idloai"),
                        rs.getString("dvt"),
                        rs.getInt("soluong"),
                        rs.getBigDecimal("dongia"),
                        rs.getDate("ngaytao").toLocalDate(),
                        rs.getString("mota"),
                        ngayketthucLocalDate);

            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sp;
    }

    public ArrayList<SanPham> getArrayListByTensp(String x) {
        ArrayList<SanPham> ds = new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            String prep = "select *,tenloai from sanpham inner join loaisp on sanpham.idloai = loaisp.id where tensp like N'%" + x + "%'";

            ResultSet rs = conn.createStatement().executeQuery(prep);
            while (rs.next()) {
                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate ngayketthucLocalDate = null;

                if (ngayketthuc != null) {
                    ngayketthucLocalDate = ngayketthuc.toLocalDate();
                }
                ds.add(new SanPham(
                        rs.getInt("id"),
                        rs.getString("tensp"),
                        rs.getInt("idloai"),
                        rs.getString("dvt"),
                        rs.getInt("soluong"),
                        rs.getBigDecimal("dongia"),
                        rs.getDate("ngaytao").toLocalDate(),
                        rs.getString("mota"),
                        ngayketthucLocalDate)
                );
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    @Override
    public int UpdateSQL(SanPham sp) {
        int i = 0;
        try {
            Connection conn = new Conn().getConnection();
            String sql = "UPDATE SanPham SET TenSP = ?, IDLoai = ?, DVT = ?, SoLuong = ?, DonGia = ?, NgayTao = ?, MoTa = ?, ngayketthuc = ? WHERE ID = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, sp.getTenSP());
            st.setInt(2, sp.getIdLoai());
            st.setString(3, sp.getDvt());
            st.setInt(4, sp.getSoLuong());
            st.setBigDecimal(5, sp.getDonGia());
            st.setDate(6, Date.valueOf(sp.getNgayTao()));
            st.setString(7, sp.getMoTa());
            st.setDate(8, Date.valueOf(sp.getNgayKetThuc()));
            st.setInt(9, sp.getId());
            i = st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }

    @Override
    public int InsertSQL(SanPham o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int themSoLuong(int idsp, int soluongthem) {
        int i = 0;
        try {
            Connection conn = new Conn().getConnection();
            String sql = "{Call Them_SoLuong_SanPham(?,?)}";
            java.sql.CallableStatement st = conn.prepareCall(sql);

            st.setInt(1, idsp);
            st.setInt(2, soluongthem);
            st.execute();

            SQLWarning warning = st.getWarnings();
            if (warning == null) {
                i=1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }

}
