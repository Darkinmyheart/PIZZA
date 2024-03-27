/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.HoaDonCT;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */
public class HoaDonCTDAO{

    
    public void InsertSQL(ArrayList<HoaDonCT> ds) {
        try {
            Connection conn = new Conn.Conn().getConnection();
            CallableStatement prep = conn.prepareCall("{CALL Insert_HoaDonCT(?, ?, ?)}");
            CallableStatement prep1 = conn.prepareCall("{CALL Update_SoLuong_SanPham(?, ?)}");
            for (HoaDonCT hdct : ds){
                
                prep.setString(1,hdct.getIdHoaDon());
                prep.setInt(2, hdct.getIdSp());
                prep.setInt(3, hdct.getSoLuong());
                prep.addBatch();
                
                prep1.setInt(1, hdct.getIdSp());
                prep1.setInt(2, hdct.getSoLuong()*(-1));
                prep1.addBatch();
            }
            prep.executeBatch();
            prep1.executeBatch();
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonCTDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ArrayList<HoaDonCT> GetArrayListByIDHoaDon(String idhoadon) {
        ArrayList<HoaDonCT> ds = new ArrayList();
        try {
            Connection conn = new Conn.Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from hoadonct where IDHoaDon like N'"+idhoadon+"'");
            while(rs.next()){
                ds.add(new HoaDonCT(rs.getString("idhoadon"),rs.getInt("idsp"),rs.getInt("soluong")));
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonCTDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }
    
}
