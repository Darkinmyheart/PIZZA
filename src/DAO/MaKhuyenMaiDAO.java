package DAO;

import Conn.Conn;
import Model.MaKhuyenMai;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class MaKhuyenMaiDAO implements DAO<MaKhuyenMai> {

    @Override
    public ArrayList<MaKhuyenMai> getArrayListAll() {
        ArrayList<MaKhuyenMai> ds = new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from MaKhuyenMai");
            while (rs.next()) {

                java.sql.Date NgaySuDung = rs.getDate("NgaySuDung");
                LocalDate ngaysudung = null;
                if (NgaySuDung != null) {
                    ngaysudung = rs.getDate("NgaySuDung").toLocalDate();
                }
                ds.add(new MaKhuyenMai(rs.getInt("ID"), rs.getString("IDCTKM"), rs.getString("code"),
                        ngaysudung));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    @Override
    public MaKhuyenMai getObjectById(String x) {
        MaKhuyenMai ma = null;
        try {
            Connection conn = new Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from MaKhuyenMai");
            while (rs.next()) {

                java.sql.Date NgaySuDung = rs.getDate("NgaySuDung");
                LocalDate ngaysudung = null;
                if (NgaySuDung != null) {
                    ngaysudung = rs.getDate("NgaySuDung").toLocalDate();
                }
                ma = new MaKhuyenMai(rs.getInt("ID"), rs.getString("IDCTKM"), rs.getString("code"),
                        ngaysudung);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ma;
    }

    public MaKhuyenMai getMaKhuyenMaiByCode(String Code) {
        MaKhuyenMai ma = null;
        try {
            Connection conn = new Conn().getConnection();
            PreparedStatement prep = conn.prepareStatement("select * from MaKhuyenMai where code like ?");
            prep.setString(1, Code);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                java.sql.Date NgaySuDung = rs.getDate("NgaySuDung");
                LocalDate ngaysudung = null;
                if (NgaySuDung != null) {
                    ngaysudung = rs.getDate("NgaySuDung").toLocalDate();
                }
                ma = new MaKhuyenMai(rs.getInt("ID"), rs.getString("IDCTKM"), rs.getString("code"),
                        ngaysudung);
            }
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ma;
    }

    @Override
    public int UpdateSQL(MaKhuyenMai object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int InsertSQL(MaKhuyenMai o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int InsertSQL(String idctkm, int soluong) {
//        private int id;
//    private String idCTKM;
//    private String code;
//    private LocalDate ngaySuDung;
//    idctkm+random(alphe+number)
        int check = 0;
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String sql = "insert into makhuyenmai values(?,?,?,?)";
        String format = "%0"+String.valueOf(soluong).length()+"d";
        try {
            Connection conn = new Conn().getConnection();
            Statement st = conn.createStatement();
            double dodaichuoi = Double.parseDouble(String.valueOf(alphabet.length));
            for (int q = 0; q < soluong; q++) {
                String x = "";
                for (int i = 0; i < String.valueOf(soluong).length(); i++) {
                        double a = Math.random() * (dodaichuoi-1);
                        x = x + alphabet[Integer.parseInt(String.valueOf(Math.round(a)))];
                }
                st.addBatch("insert into makhuyenmai values('" + idctkm + "','" +idctkm.toUpperCase()+ x.toUpperCase() + String.format(format, q)+ "',null)");
            }
            st.executeBatch();
            SQLWarning kq = st.getWarnings();
            
            if (kq != null) {
                check = 1;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    
     public ArrayList<MaKhuyenMai> getArrayListByCTKM(String maCTKM) {
        ArrayList<MaKhuyenMai> ds = new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from MaKhuyenMai where idCTKM = '"+maCTKM+"'");
            while (rs.next()) {

                java.sql.Date NgaySuDung = rs.getDate("NgaySuDung");
                LocalDate ngaysudung = null;
                if (NgaySuDung != null) {
                    ngaysudung = rs.getDate("NgaySuDung").toLocalDate();
                }
                ds.add(new MaKhuyenMai(rs.getInt("ID"), rs.getString("IDCTKM"), rs.getString("code"),
                        ngaysudung));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChuongTrinhKMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }
    
}
