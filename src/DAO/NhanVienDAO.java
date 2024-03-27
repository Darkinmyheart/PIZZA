package DAO;

import Model.NhanVien;
import java.util.ArrayList;
import java.sql.*;
import Conn.Conn;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class NhanVienDAO implements DAO<NhanVien> {

    @Override
    public ArrayList<NhanVien> getArrayListAll() {
        ArrayList<NhanVien> ds = new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            PreparedStatement prep = conn.prepareStatement("select * from NhanVien where id!=1");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate NgayKetThuc = null;
                if (ngayketthuc != null) {
                    NgayKetThuc = ngayketthuc.toLocalDate();
                }
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSdt(rs.getString("sdt"));
                nv.setEmail(rs.getString("email"));
                nv.setDiaChi(rs.getString("diachi"));
                nv.setChucVu(rs.getString("chucvu"));
                nv.setLuong(rs.getBigDecimal("luong"));
                nv.setNgayTao(rs.getDate("ngaytao").toLocalDate());
                nv.setPass(rs.getString("pass"));
                nv.setNgayKetThuc(NgayKetThuc);
                ds.add(nv);
            }
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    @Override
    public NhanVien getObjectById(String x) {
        NhanVien nv = null;
        try {
            Connection conn = new Conn().getConnection();
            PreparedStatement prep = conn.prepareStatement("select * from nhanvien where ID=?");
            prep.setInt(1, Integer.parseInt(x));
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate NgayKetThuc = null;
                if (ngayketthuc != null) {
                    NgayKetThuc = ngayketthuc.toLocalDate();
                }
                nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSdt(rs.getString("sdt"));
                nv.setEmail(rs.getString("email"));
                nv.setDiaChi(rs.getString("diachi"));
                nv.setChucVu(rs.getString("chucvu"));
                nv.setLuong(rs.getBigDecimal("luong"));
                nv.setNgayTao(rs.getDate("ngaytao").toLocalDate());
                nv.setPass(rs.getString("pass"));
                nv.setNgayKetThuc(NgayKetThuc);
            }
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return nv;
    }

    @Override
    public int UpdateSQL(NhanVien object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int InsertSQL(NhanVien o) {
        int i = 0;

        try {
            Connection conn = new Conn().getConnection();
            String prep = "INSERT INTO [dbo].[NhanVien]\n"
                    + "           ([TenNV]\n"
                    + "           ,[GioiTinh]\n"
                    + "           ,[SDT]\n"
                    + "           ,[Email]\n"
                    + "           ,[DiaChi]\n"
                    + "           ,[ChucVu]\n"
                    + "           ,[Luong]\n"
                    + "           ,[NgayTao]\n"
                    + "           ,[Pass]\n"
                    + "           ,[Ngayketthuc])\n"
                    + "     VALUES(?,?,?,?,?,?,?,getdate(),?,null)";
            PreparedStatement prepsql = conn.prepareStatement(prep);
            prepsql.setString(1, o.getTenNV());
            prepsql.setString(2, o.getGioiTinh());
            prepsql.setString(3, o.getSdt());
            prepsql.setString(4, o.getEmail());
            prepsql.setString(5, o.getDiaChi());
            prepsql.setString(6, o.getChucVu());
            prepsql.setBigDecimal(7, o.getLuong());
            prepsql.setString(8, o.getPass());
            i = prepsql.executeUpdate();
            prepsql.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public NhanVien getNhanVienBySDT(String Sdt) {
        NhanVien nv = null;
        try {
            Connection conn = new Conn().getConnection();
            String sql = "select * from NhanVien where sdt like N'"+Sdt+"'";
            
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                java.sql.Date ngayketthuc = rs.getDate("ngayketthuc");
                LocalDate NgayKetThuc = null;
                if (ngayketthuc != null) {
                    NgayKetThuc = ngayketthuc.toLocalDate();
                }
                nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSdt(rs.getString("sdt"));
                nv.setEmail(rs.getString("email"));
                nv.setDiaChi(rs.getString("diachi"));
                nv.setChucVu(rs.getString("chucvu"));
                nv.setLuong(rs.getBigDecimal("luong"));
                nv.setNgayTao(rs.getDate("ngaytao").toLocalDate());
                nv.setPass(rs.getString("pass"));
                nv.setNgayKetThuc(NgayKetThuc);
                
            }
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nv;
    }

    public int UpdateNgayKetThuc(int idsp){
        int i = 0;
        try {
            Connection conn = new Conn().getConnection();
            String sql = "update nhanvien set ngayketthuc = getdate() where id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,idsp);
            i = st.executeUpdate();
            

        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
}
