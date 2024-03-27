/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conn.Conn;
import java.math.BigDecimal;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDAO {

    public BigDecimal DoanhThuByLoaiTime(String loai) {
        BigDecimal total = BigDecimal.ZERO;
        try {
            Connection conn = new Conn().getConnection();
            String sql = "";
            if (loai.equalsIgnoreCase("ngày")) {
                sql = "SELECT SUM(ThanhTien) AS DoanhThu FROM HoaDon WHERE NgayLap = CONVERT(DATE, GETDATE())";
            } else if (loai.equalsIgnoreCase("tháng")) {
                sql = "SELECT SUM(ThanhTien) AS DoanhThu FROM HoaDon WHERE Month(NgayLap) = month(getdate())";
            } else if (loai.equalsIgnoreCase("năm")) {
                sql = "SELECT SUM(ThanhTien) AS DoanhThu FROM HoaDon WHERE year(NgayLap) = year(getdate())";
            }

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                total = rs.getBigDecimal("doanhthu");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (total == null) {
            return BigDecimal.ZERO;
        } else {
            return total;
        }
    }

    public BigDecimal ChiPhiByLoaiTime(String loai) {
        BigDecimal total = new BigDecimal("0.00");

        try {
            Connection conn = new Conn().getConnection();
            String sql = "";
            if (loai.equalsIgnoreCase("ngày")) {
                sql = "SELECT SUM(tongtien) AS ChiPhi FROM PhieuChi WHERE NgayLap = CONVERT(DATE, GETDATE())";
            } else if (loai.equalsIgnoreCase("tháng")) {
                sql = "SELECT SUM(tongtien) AS ChiPhi FROM PhieuChi WHERE Month(NgayLap) = month(getdate())";
            } else if (loai.equalsIgnoreCase("năm")) {
                sql = "SELECT SUM(tongtien) AS ChiPhi FROM PhieuChi WHERE year(NgayLap) = year(getdate())";
            }

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                total = rs.getBigDecimal("ChiPhi");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (total == null) {
            return BigDecimal.ZERO;
        } else {
            return total;
        }
    }

    public int SoHoaDonLoaiTime(String loai) {
        int total = 0;

        try {
            Connection conn = new Conn().getConnection();
            String sql = "";
            if (loai.equalsIgnoreCase("ngày")) {
                sql = "SELECT count(id) AS dem FROM hoadon WHERE NgayLap = CONVERT(DATE, GETDATE())";
            } else if (loai.equalsIgnoreCase("tháng")) {
                sql = "SELECT count(id) AS dem FROM hoadon WHERE Month(NgayLap) = month(getdate())";
            } else if (loai.equalsIgnoreCase("năm")) {
                sql = "SELECT count(id) AS dem FROM hoadon WHERE year(NgayLap) = year(getdate())";
            }

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt("dem");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return total;
    }

    public int SoPhieuChiLoaiTime(String loai) {
        int total = 0;

        try {
            Connection conn = new Conn().getConnection();
            String sql = "";
            if (loai.equalsIgnoreCase("ngày")) {
                sql = "SELECT count(id) AS dem FROM phieuchi WHERE NgayLap = CONVERT(DATE, GETDATE())";
            } else if (loai.equalsIgnoreCase("tháng")) {
                sql = "SELECT count(id) AS dem FROM phieuchi WHERE Month(NgayLap) = month(getdate())";
            } else if (loai.equalsIgnoreCase("năm")) {
                sql = "SELECT count(id) AS dem FROM phieuchi WHERE year(NgayLap) = year(getdate())";
            }

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt("dem");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return total;
    }

}
