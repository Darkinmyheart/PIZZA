
package DAO;

import Conn.Conn;
import Model.SanPham;
import Model.LoaiSP;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class loaiSPDAO implements DAO<Model.LoaiSP>{
    
    @Override
    public ArrayList<LoaiSP> getArrayListAll() {
        ArrayList<LoaiSP> ds =new ArrayList<>();
        try {
            Connection conn = new Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from loaisp ");
            while(rs.next()){
                ds.add(new LoaiSP(rs.getInt("ID"), rs.getString("tenloai"),rs.getDate("ngayTao").toLocalDate()));
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ds;
    }

    @Override
    public LoaiSP getObjectById(String id) {
        LoaiSP loai = null;
        try {
            Connection conn = new Conn().getConnection();
           PreparedStatement prep = conn.prepareStatement("select * from loaisp where id = ?");
           prep.setInt(1, Integer.parseInt(id));
           ResultSet rs= prep.executeQuery();
           while (rs.next()){
               loai = new LoaiSP(rs.getInt("ID"), rs.getString("tenloai"),rs.getDate("ngayTao").toLocalDate());
           }
           prep.close();
           conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(loaiSPDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loai;
    }

    @Override
    public int UpdateSQL(LoaiSP object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int InsertSQL(LoaiSP o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
