package UI;

import DAO.SanPhamDAO;
import DAO.loaiSPDAO;
import DeskHelp.Decimal;
import Model.LoaiSP;
import Model.NhanVien;
import Model.SanPham;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mr.YI
 */
public class SanPhamPanel extends javax.swing.JPanel {
    NhanVien nv;
    Connection con ;
    private final String header[] = {"Mã SP", "Tên SP", "Loại", "Đơn vị tính", "So luong", "Đơn giá", "Ngày tạo", "Mô tả", "Ngày kết thúc"};
    private final DefaultTableModel tblModel = new DefaultTableModel(header, 0);
    ArrayList<LoaiSP> ds1 = new ArrayList<>();

    public SanPhamPanel() {
        initComponents();
        try {
            con= new Conn.Conn().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ds1 = new loaiSPDAO().getArrayListAll();
        LoadCCB();
        LoadData();
        tbltable.setRowSelectionInterval(0, 0);
        btnluu.setEnabled(false);
        showDetails(0);
    }
    
    public void setNv(NhanVien nv) {
        this.nv = nv;
        if(nv.getChucVu().equalsIgnoreCase("nhân viên")){
            btnNgungBan.setVisible(false);
            txtsoluong.setEditable(false);
            txtsoluong.setEditable(false);
        }
    }
    
    public void LoadData() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            PreparedStatement ps = con.prepareStatement("select * from SanPham");
            ResultSet rs = ps.executeQuery();

            tblModel.setRowCount(0);
            Vector vector = null;
            while (rs.next()) {
                vector = new Vector();

                vector.add(rs.getString("ID"));
                vector.add(rs.getString("TenSP"));
                vector.add(new loaiSPDAO().getObjectById(rs.getString("idloai")).getTenLoai());
                vector.add(rs.getString("DVT"));
                vector.add(rs.getString("Soluong"));
                vector.add(Decimal.ConvertBigDecimalToStringFormat(rs.getBigDecimal("DonGia")));
                vector.add(rs.getString("NgayTao"));
                vector.add(rs.getString("MoTa"));
                vector.add(rs.getString("Ngayketthuc") == null ? "Còn bán" : rs.getString("ngayketthuc"));

                tblModel.addRow(vector);

                tbltable.setModel((tblModel));

                txtma.setEditable(false);
                txtdate.setEditable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void LoadCCB() {

        if (ds1.size() > 0) {
            cboloai.removeAllItems();
            for (LoaiSP loai : ds1) {
                cboloai.addItem(loai.getTenLoai());
            }
        }
    }

    public void showDetails(int index) {
        txtma.setText(tbltable.getValueAt(index, 0).toString());
        txtten.setText(tbltable.getValueAt(index, 1).toString());
        cboloai.setSelectedItem(tbltable.getValueAt(index, 2).toString());
        txtdvt.setText(tbltable.getValueAt(index, 3).toString());
        txtsoluong.setText(tbltable.getValueAt(index, 4).toString());
        txtgia.setText(tbltable.getValueAt(index, 5).toString());
        txtngayketthuc.setText(tbltable.getValueAt(index, 8).toString());
        txtmota.setText(tbltable.getValueAt(index, 7).toString());
        txtdate.setText(tbltable.getValueAt(index, 6).toString());
        btnluu.setEnabled(false);

    }

    public void cleanForm() {
        txtma.setText("");
        txtten.setText("");
        cboloai.setSelectedIndex(0);
        txtdvt.setText("");
        txtgia.setText("");
        buttonGroup1.clearSelection();
        txtmota.setText("");
        txtdate.setText("");
        txtsoluong.setText("");
    }

    public void check() {

    }

    public void Save() {
        try {
//            new SanPham(id, tenSP, idLoai, dvt, SoLuong, donGia, ngayTao, moTa, ngayKetThuc)
            PreparedStatement ps = con.prepareStatement("insert into SanPham values(?,?,?,?,?,?,getdate(),?,null)");
            ps.setString(1, txtma.getText());
            ps.setString(2, txtten.getText());
            int idloai = -1;

            for (int i = 0; i < ds1.size(); i++) {
                if (ds1.get(i).getTenLoai().equalsIgnoreCase((String) cboloai.getSelectedItem())) {
                    idloai = ds1.get(i).getId();
                    break;
                }
            }

            ps.setInt(3, idloai);
            ps.setString(4, txtdvt.getText());
            ps.setString(5, txtsoluong.getText());
            ps.setString(6, txtgia.getText());
            ps.setString(7, txtmota.getText());

            int kq = ps.executeUpdate();
            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Save Thành Công!");
                btnluu.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Save Thất Bại!");
            }

            ps.close();
            this.LoadData();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Save Thất Bại!");
            e.printStackTrace();
        }
    }

    public void Update() {
        try {
            
            PreparedStatement ps = con.prepareStatement("update SanPham set TenSP= ?, idloai=?, DVT= ?, DonGia= ?, MoTa=?,soluong = ? where ID =?");
            int idloai = -1;
            for (int i = 0; i < ds1.size(); i++) {
                if (ds1.get(i).getTenLoai().equalsIgnoreCase((String) cboloai.getSelectedItem())) {
                    idloai = ds1.get(i).getId();
                    break;
                }
            }
            ps.setString(1, txtten.getText());
            ps.setInt(2, idloai);
            ps.setString(3, txtdvt.getText());
            ps.setString(4, txtgia.getText().replaceAll(",", ""));
            ps.setString(5, txtmota.getText());
            ps.setInt(6, Integer.valueOf(txtsoluong.getText()));
            ps.setString(7, txtma.getText());

            int kq = ps.executeUpdate();
            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Update Thành Công!");
            } else {
                JOptionPane.showMessageDialog(this, "Update Thất Bại!");
            }

            ps.close();
            
            this.LoadData();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Update Thất Bại!");
            e.printStackTrace();
        }
    }

    public void fillter() {
        try {
            String x = CbbFillter.getSelectedItem().toString();
            String sql = "";
            if (x.equalsIgnoreCase("Tất cả")) {
                sql = "select * from sanpham where TenSP like N'%" + txtsearch.getText() + "%'";
            } else if (x.equalsIgnoreCase("Còn bán")) {
                sql = "select * from sanpham where TenSP like N'%" + txtsearch.getText() + "%' and ngayketthuc is null";
            } else if (x.equalsIgnoreCase("hết bán")) {
                sql = "select * from sanpham where TenSP like N'%" + txtsearch.getText() + "%' and ngayketthuc is not null";
            }
            
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            tblModel.setRowCount(0);
            Vector vector = null;
            while (rs.next()) {
                vector = new Vector();

                vector.add(rs.getString("ID"));
                vector.add(rs.getString("TenSP"));
                vector.add(new loaiSPDAO().getObjectById(rs.getString("idloai")).getTenLoai());
                vector.add(rs.getString("DVT"));
                vector.add(rs.getString("Soluong"));
                vector.add(Decimal.ConvertBigDecimalToStringFormat(rs.getBigDecimal("DonGia")));
                vector.add(rs.getString("NgayTao"));
                vector.add(rs.getString("MoTa"));
                vector.add(rs.getString("Ngayketthuc") == null ? "Còn bán" : rs.getString("ngayketthuc"));

                tblModel.addRow(vector);

                tbltable.setModel((tblModel));
                cleanForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtten = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtdvt = new javax.swing.JTextField();
        txtdate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtgia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtsearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltable = new javax.swing.JTable();
        btnluu = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        btnnew = new javax.swing.JButton();
        cboloai = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtmota = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtma = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtngayketthuc = new javax.swing.JTextField();
        txtsoluong = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnNgungBan = new javax.swing.JButton();
        CbbFillter = new javax.swing.JComboBox<>();
        btnsua2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(910, 650));

        txtten.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Tên SP");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setText("Ngày tạo");

        txtdvt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtdate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdateActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Đơn vị tính");

        txtgia.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Đơn giá");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Loại");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("Tìm sản phẩm");

        txtsearch.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchActionPerformed(evt);
            }
        });
        txtsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsearchKeyReleased(evt);
            }
        });

        tbltable.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tbltable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Loại", "Số lượng", "Đơn vị tính", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbltable.setRowHeight(35);
        tbltable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbltable);
        if (tbltable.getColumnModel().getColumnCount() > 0) {
            tbltable.getColumnModel().getColumn(0).setResizable(false);
            tbltable.getColumnModel().getColumn(1).setResizable(false);
            tbltable.getColumnModel().getColumn(2).setResizable(false);
            tbltable.getColumnModel().getColumn(3).setResizable(false);
            tbltable.getColumnModel().getColumn(4).setResizable(false);
            tbltable.getColumnModel().getColumn(5).setResizable(false);
        }

        btnluu.setBackground(new java.awt.Color(153, 204, 255));
        btnluu.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnluu.setText("Lưu");
        btnluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnluuActionPerformed(evt);
            }
        });

        btnsua.setBackground(new java.awt.Color(153, 204, 255));
        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnsua.setText("Sửa");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        btnnew.setBackground(new java.awt.Color(153, 204, 255));
        btnnew.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnnew.setText("Làm mới");
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        cboloai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cboloai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chese", "Hải sản", "Thịt", "Rau củ" }));

        txtmota.setColumns(20);
        txtmota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtmota.setRows(5);
        jScrollPane2.setViewportView(txtmota);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Mô tả");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("DANH MỤC SẢN PHẨM");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Mã SP");

        txtma.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setText("Ngày kết thúc:");

        txtngayketthuc.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtngayketthuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtngayketthucActionPerformed(evt);
            }
        });

        txtsoluong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Số lượng");

        btnNgungBan.setBackground(new java.awt.Color(153, 204, 255));
        btnNgungBan.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnNgungBan.setForeground(new java.awt.Color(255, 0, 51));
        btnNgungBan.setText("NGỪNG BÁN");
        btnNgungBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgungBanActionPerformed(evt);
            }
        });

        CbbFillter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Còn bán", "Hết bán" }));
        CbbFillter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbFillterActionPerformed(evt);
            }
        });

        btnsua2.setBackground(new java.awt.Color(153, 204, 255));
        btnsua2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnsua2.setForeground(new java.awt.Color(255, 255, 255));
        btnsua2.setText("Thêm số lượng");
        btnsua2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsua2ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Hiển thị");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtma)
                                                .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(12, 12, 12)
                                            .addComponent(cboloai, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtdvt)
                                            .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(351, 351, 351))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNgungBan)
                                .addGap(71, 71, 71)
                                .addComponent(btnnew)
                                .addGap(18, 18, 18)
                                .addComponent(btnluu, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnsua2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(CbbFillter, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(39, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(3, 3, 3)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cboloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtdvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CbbFillter, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnnew)
                    .addComponent(btnluu)
                    .addComponent(btnsua)
                    .addComponent(btnNgungBan)
                    .addComponent(btnsua2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdateActionPerformed

    private void txtsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchActionPerformed

    private void txtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyReleased
        // TODO add your handling code here:
        fillter();
    }//GEN-LAST:event_txtsearchKeyReleased

    private void tbltableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltableMouseClicked
        showDetails(tbltable.getSelectedRow());
    }//GEN-LAST:event_tbltableMouseClicked

    private void btnluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuActionPerformed
        int row = 0;
        if (txtten.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm đang để trống!");
        } else if (txtdvt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Đơn vị tính sản phẩm đang trống");
        } else if (txtgia.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập giá sản phẩm");
        } else if (!txtgia.getText().matches("^(-?)(0|([1-9][0-9]*))(\\\\.[0-9]+)?$")) {
            JOptionPane.showMessageDialog(this, "Giá sản phẩm nhập không đúng định dạng");
        } else if (Double.parseDouble(txtgia.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Giá sản phẩm không được giá âm");
        } else if (txtmota.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nhập thêm phần mô tả cho sản phẩm");
        } else {

            Save();
            LoadData();
        }

    }//GEN-LAST:event_btnluuActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        int row = 0;
        if (txtten.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm đang để trống!");
        } else if (txtdvt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Đơn vị tính sản phẩm đang trống");
        } else if (txtgia.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập giá sản phẩm");
        } else if (!txtgia.getText().matches("^(-?)(0|([1-9][0-9]*))(\\\\.[0-9]+)?$")) {
            JOptionPane.showMessageDialog(this, "Giá sản phẩm nhập không đúng định dạng");
        } else if (Double.parseDouble(txtgia.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Giá sản phẩm không được giá âm");
        } else if (txtmota.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nhập thêm phần mô tả cho sản phẩm");
        } else {
            Update();
        }
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        cleanForm();
        btnluu.setEnabled(true);
        String x = "";
        try {
            Connection conn = new Conn.Conn().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select top 1 id from sanpham order by id desc");
            while (rs.next()) {
                x = String.valueOf(rs.getInt(1) + 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtma.setText(x);
        txtdate.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        LoadData();

    }//GEN-LAST:event_btnnewActionPerformed

    private void txtngayketthucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtngayketthucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtngayketthucActionPerformed

    private void btnNgungBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgungBanActionPerformed
        // TODO add your handling code here:
        if (tbltable.getSelectedRow() != -1) {
            int a = JOptionPane.showConfirmDialog(this, "Ngừng kinh doanh sản phẩm: " + txtten.getText(), "Ngừng kinh doanh", JOptionPane.YES_NO_OPTION);
            if (a == JOptionPane.YES_OPTION) {
                try {
                    
                    PreparedStatement ps = con.prepareStatement("update SanPham set ngayketthuc = getdate() where ID =?");

                    ps.setString(1, txtma.getText());

                    int kq = ps.executeUpdate();
                    if (kq == 1) {
                        JOptionPane.showMessageDialog(this, "Ngừng kinh doanh: " + txtten.getText() + "vào ngày " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                        
                        for (int i = 0; i < tbltable.getRowCount(); i++) {
                            if (tbltable.getValueAt(i, 0).toString().equalsIgnoreCase(txtma.getText())) {
                                tbltable.setRowSelectionInterval(i, i);
                                showDetails(i);
                                break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Update Thất Bại!");
                    }

                    ps.close();
                    
                    this.LoadData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Update Thất Bại!");
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm");
        }

    }//GEN-LAST:event_btnNgungBanActionPerformed

    private void CbbFillterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbFillterActionPerformed
        // TODO add your handling code here:
        fillter();
    }//GEN-LAST:event_CbbFillterActionPerformed

    private void btnsua2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsua2ActionPerformed
        // TODO add your handling code here:
        if (tbltable.getSelectedRow() != -1) {
            String a = JOptionPane.showInputDialog(this, "Nhập số lượng muốn thêm : \n" + txtten.getText(), "Nhập số lượng", JOptionPane.PLAIN_MESSAGE);
            if (a != null && !a.isEmpty()) {
                if (a.matches("[0-9]+")) {
                    int check = new SanPhamDAO().themSoLuong(Integer.parseInt(txtma.getText()), Integer.parseInt(a));
                    if (check == 1) {
                        JOptionPane.showMessageDialog(this, "Đã thêm " + a + " " + txtten.getText());
                        LoadData();
                        for (int i = 0; i < tbltable.getRowCount(); i++) {
                            if (tbltable.getValueAt(i, 0).toString().equalsIgnoreCase(txtma.getText())) {
                                tbltable.setRowSelectionInterval(i, i);
                                showDetails(i);
                                break;
                            }
                        }
                    } else if (check == 0) {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập số", "Nhập sai định dạng", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm muốn thêm");
        }
    }//GEN-LAST:event_btnsua2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CbbFillter;
    private javax.swing.JButton btnNgungBan;
    private javax.swing.JButton btnluu;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnsua2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboloai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbltable;
    private javax.swing.JTextField txtdate;
    private javax.swing.JTextField txtdvt;
    private javax.swing.JTextField txtgia;
    private javax.swing.JTextField txtma;
    private javax.swing.JTextArea txtmota;
    private javax.swing.JTextField txtngayketthuc;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextField txtsoluong;
    private javax.swing.JTextField txtten;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
