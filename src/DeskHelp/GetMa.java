package DeskHelp;

import java.sql.*;
import Conn.Conn;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class GetMa {

    public String getMa(String tenbang, String chucaidau, String maID) {
        String ma2="";
        try {
            Connection conn = new Conn().getConnection();
            Statement st = conn.createStatement();
            Statement st1 = conn.createStatement();
            String sql = "select count(*) from " + tenbang;
            String sql1 = "select top 1 * from " + tenbang + " order by " + maID + " desc";
            ResultSet rs1 = st.executeQuery(sql);
            ResultSet rs2 = st1.executeQuery(sql1);
            rs1.next();
            rs2.next();
            int i = rs1.getInt(1);

            if (i > 0) {
                String ma1 = rs2.getString(maID);
                System.out.println(ma1);
                String so = ma1.substring(chucaidau.length(), ma1.length());
                System.out.println(so);
                int so1 = Integer.parseInt(so) + 1;
                ma2 = chucaidau + String.format("%04d", so1);
            } else {
                ma2 = chucaidau + String.format("%04d", 0);
            }
            conn.close();
            return ma2.toUpperCase();
        } catch (SQLException ex) {
            Logger.getLogger(GetMa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ma2.toUpperCase();
    }

    public String getMaHD() {
        String ma = null;
        try {
            //HD-DATE-0000
            java.util.Date date = new java.util.Date();
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
            String dates = format.format(date);
            Connection conn = new Conn().getConnection();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("select count(id) as 'dem' from hoadon where DATEDIFF(DAY,NgayLap,GETDATE())=0");
            rs.next();
            int dem = rs.getInt("dem") + 1;
            conn.close();
            ma = "HD-" + dates + "-" + String.format("%04d", dem);
        } catch (SQLException ex) {
            Logger.getLogger(GetMa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ma;
    }
    
    public String getMaPC() {
        String ma = null;
        try {
            //PC-DATE-0000
            java.util.Date date = new java.util.Date();
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
            String dates = format.format(date);
            Connection conn = new Conn().getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(id) as 'dem' from PhieuChi where DATEDIFF(DAY,NgayLap,GETDATE())=0");
            rs.next();
            int dem = rs.getInt("dem") + 1;
            conn.close();
            ma = "PC-" + dates + "-" + String.format("%04d", dem);
        } catch (SQLException ex) {
            Logger.getLogger(GetMa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ma;
    }
}
