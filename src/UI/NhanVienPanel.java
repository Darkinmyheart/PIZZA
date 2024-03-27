package UI;

import Conn.Conn;
import DAO.NhanVienDAO;
import Model.NhanVien;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
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
public class NhanVienPanel extends javax.swing.JPanel {

    ArrayList<NhanVien> ds;
    int current = 0;
    private DefaultTableModel tblModel = new DefaultTableModel();

    public NhanVienPanel() {
        initComponents();
        tblModel = (DefaultTableModel) tblNhanVien.getModel();
        ds = new NhanVienDAO().getArrayListAll();
        LoadData();
        tblNhanVien.setRowSelectionInterval(0, 0);
        btnLuu.setEnabled(false);
//        showDetails(0);
    }

    public void LoadData() {
        try {
            Connection con = new Conn().getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from nhanvien where chucvu != 'admin'");
            tblModel.setRowCount(0);
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(0, rs.getString("ID"));
                vector.add(1, rs.getString("TenNV"));
                vector.add(2, rs.getString("GioiTinh"));
                vector.add(3, rs.getString("SDT"));
                vector.add(4, rs.getString("Email"));
                vector.add(5, rs.getString("ChucVu"));
                vector.add(6, rs.getString("Luong"));
                vector.add(7, rs.getString("NgayTao"));
                vector.add(8, rs.getString("pass"));
                String x = rs.getString("Ngayketthuc");
                String ngayketthuc = "";
                if (x != null) {
                    ngayketthuc = x;
                }
                vector.add(9, ngayketthuc);
                vector.add(10, rs.getString("diachi"));
                tblModel.addRow(vector);
                rdoNam.setSelected(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showDetails(int indexRow) {
        txtID.setText(tblNhanVien.getValueAt(indexRow, 0).toString());
        txtTenNV.setText(tblNhanVien.getValueAt(indexRow, 1).toString());
        String gioitinh = tblNhanVien.getValueAt(indexRow, 2).toString();
        if (gioitinh.equals("Nam")) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtSDT.setText(tblNhanVien.getValueAt(indexRow, 3).toString());
        txtEmail.setText(tblNhanVien.getValueAt(indexRow, 4).toString());
        cbbChucVu.setSelectedItem(tblNhanVien.getValueAt(indexRow, 5).toString());
        txtluong1.setText(tblNhanVien.getValueAt(indexRow, 6).toString());
        txtNgayTao.setText(tblNhanVien.getValueAt(indexRow, 7).toString());
        txtMatKhau.setText(tblNhanVien.getValueAt(indexRow, 8).toString());
        txtngayketthuc.setText(tblNhanVien.getValueAt(indexRow, 9).toString());
        txtDiaChi.setText(tblNhanVien.getValueAt(indexRow, 10).toString());
        btnLuu.setEnabled(false);
    }

    public void Save() {

        NhanVien nv = new NhanVien(0, txtTenNV.getText(),
                rdoNam.isSelected() ? "Nam" : "Nữ",
                txtSDT.getText(),
                txtEmail.getText(),
                txtDiaChi.getText(),
                cbbChucVu.getSelectedItem().toString(),
                BigDecimal.valueOf(Double.parseDouble(txtluong1.getText())),
                null,
                txtMatKhau.getText(),
                null);
        int kq = new NhanVienDAO().InsertSQL(nv);
        if (kq == 1) {
            JOptionPane.showMessageDialog(this, "Save Thành Công!");
        } else {
            JOptionPane.showMessageDialog(this, "Save Thất Bại!");
        }

        this.LoadData();

    }

    public void update() {
        try {
            Connection con = new Conn().getConnection();
            PreparedStatement ps = con.prepareStatement("update nhanvien set TenNV= ?, GioiTinh= ?, SDT= ?, Email= ?,DiaChi = ?, ChucVu= ?, Luong= ?,Pass = ? where ID = ?");

            ps.setString(1, txtTenNV.getText());
            String gt;
            if (rdoNam.isSelected()) {
                gt = "Nam";
            } else {
                gt = "Nữ";
            }
            ps.setString(2, gt);
            ps.setString(3, txtSDT.getText());
            ps.setString(4, txtEmail.getText());
            ps.setString(5, txtDiaChi.getText());
            ps.setString(6, (String) cbbChucVu.getSelectedItem());
            ps.setString(7, txtluong1.getText());

            ps.setString(8, txtMatKhau.getText());
            ps.setString(9, txtID.getText());

            int kq = ps.executeUpdate();
            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Update Thành Công!");
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

    public boolean SDT(String sdt) {
        boolean check = false;
        try {
            Connection con = new Conn().getConnection();
            String query = "SELECT COUNT(*) as 'count' FROM nhanvien WHERE SDT = '" + sdt + "' and ngayketthuc is null";
            ResultSet rs = con.createStatement().executeQuery(query);
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    check = true;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    void nghiViec() {
        if (txtngayketthuc.getText().isEmpty()) {
            if (tblNhanVien.getSelectedRow() != -1) {
                int a = JOptionPane.showConfirmDialog(this, "Update nhân viên " + txtTenNV.getText() + "\n Nghỉ việc ?", "Check", JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    int check = new NhanVienDAO().UpdateNgayKetThuc(Integer.parseInt(txtID.getText()));
                    if (check == 1) {
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                        ds = new NhanVienDAO().getArrayListAll();
                        LoadData();
                        for (int i = 0; i < ds.size(); i++) {
                            if (ds.get(i).getId() == Integer.parseInt(txtID.getText())) {
                                tblNhanVien.setRowSelectionInterval(i, i);
                                showDetails(i);
                                break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật lỗi !!!!");
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTimNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbbChucVu = new javax.swing.JComboBox<>();
        txtDiaChi = new javax.swing.JTextField();
        rdoNu = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        txtTenNV = new javax.swing.JTextField();
        txtID = new javax.swing.JTextField();
        txtluong1 = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnLuu = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        txtsua = new javax.swing.JButton();
        txtsua1 = new javax.swing.JButton();
        txtTimNV1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtngayketthuc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnTim = new javax.swing.JButton();

        txtTimNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimNVActionPerformed(evt);
            }
        });
        txtTimNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimNVKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("TÌM NHÂN VIÊN");

        setPreferredSize(new java.awt.Dimension(910, 650));

        tblNhanVien.setBackground(new java.awt.Color(204, 255, 255));
        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Họ Tên", "Giới tính", "SDT", "Email", "Chức vụ", "Lương", "Ngày Tạo", "Pass", "Ngày kết thúc", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(45);
        tblNhanVien.setRowMargin(5);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNhanVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("MÃ NHÂN VIÊN");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("TÊN NHÂN VIÊN");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("GIỚI TÍNH");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("ĐỊA CHỈ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("CHỨC VỤ");

        cbbChucVu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân viên", "Quản lý" }));
        cbbChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbChucVuActionPerformed(evt);
            }
        });

        txtDiaChi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        rdoNu.setBackground(new java.awt.Color(255, 255, 204));
        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoNu.setText("Nữ");
        rdoNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNuActionPerformed(evt);
            }
        });

        rdoNam.setBackground(new java.awt.Color(255, 255, 204));
        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoNam.setText("Nam");

        txtTenNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtluong1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtluong1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtluong1ActionPerformed(evt);
            }
        });

        txtSDT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        txtEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txtNgayTao.setEditable(false);
        txtNgayTao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("MẬT KHẨU");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("EMAIL");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("SỐ ĐIỆN THOẠI");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("LƯƠNG/THÁNG");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("NGÀY KẾT THÚC");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("NGÀY TẠO");

        btnLuu.setBackground(new java.awt.Color(153, 204, 255));
        btnLuu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLuu.setText("SAVE");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(153, 204, 255));
        btnNew.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNew.setText("NEW");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        txtsua.setBackground(new java.awt.Color(153, 204, 255));
        txtsua.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtsua.setText("UPDATE");
        txtsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsuaActionPerformed(evt);
            }
        });

        txtsua1.setBackground(new java.awt.Color(153, 204, 255));
        txtsua1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtsua1.setText("NGHỈ VIỆC");
        txtsua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsua1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsua, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsua1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLuu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtsua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtsua1)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        txtTimNV1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTimNV1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimNV1ActionPerformed(evt);
            }
        });
        txtTimNV1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimNV1KeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 255));
        jLabel13.setText("TÌM NHÂN VIÊN:");

        txtngayketthuc.setEditable(false);
        txtngayketthuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        btnTim.setBackground(new java.awt.Color(153, 204, 255));
        btnTim.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTim.setText("SEARCH");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                            .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoNu))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cbbChucVu, 0, 170, Short.MAX_VALUE)
                                            .addComponent(txtDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtluong1))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimNV1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTim)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdoNam)
                                    .addComponent(rdoNu)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtluong1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimNV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMousePressed
        showDetails(tblNhanVien.getSelectedRow());
    }//GEN-LAST:event_tblNhanVienMousePressed

    private void cbbChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbChucVuActionPerformed

    }//GEN-LAST:event_cbbChucVuActionPerformed

    private void rdoNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNuActionPerformed

    private void txtluong1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtluong1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtluong1ActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        int row = 0;
        if (txtTenNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên Không Được Trống!");
            txtTenNV.requestFocus();
        } else if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SDT Không Được Trống!");
            txtSDT.requestFocus();
        } else if (!(txtSDT.getText().matches("0[0-9]{9}"))) {
            JOptionPane.showMessageDialog(this, "SDT Không Hợp Lệ!");
        } else if (SDT(txtSDT.getText())) {
            JOptionPane.showMessageDialog(this, "Trùng số điện thoại!");
            txtSDT.requestFocus();
        } else if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Email Không Được Trống!");
            txtEmail.requestFocus();
        } else if (!(txtEmail.getText().matches("[^@]{2,64}@[^.]{2,253}\\.[0-9a-z-.]{2,63}"))) {
            JOptionPane.showMessageDialog(this, "Email Không Hợp Lệ!");
            txtEmail.requestFocus();
        } else if (txtDiaChi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Địa Chỉ Không Được Trống!");
            txtDiaChi.requestFocus();
        } else if (txtluong1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Lương Không Được Trống!");
            txtluong1.requestFocus();
        } else if (!txtluong1.getText().matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Lương phải là số !");
            txtluong1.requestFocus();
        } else if (Double.parseDouble(txtluong1.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Lương không được âm!");
            txtluong1.requestFocus();
        } else {
            Save();
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtID.setText(String.valueOf(ds.size() + 1));
        txtTenNV.setText("");
        txtEmail.setText("");
        txtMatKhau.setText("");
        txtDiaChi.setText("");
        cbbChucVu.setSelectedItem(0);
        txtNgayTao.setText("");
        rdoNam.setSelected(true);
        txtID.requestFocus();
        txtNgayTao.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        txtMatKhau.setText("");
        txtluong1.setText("");
        txtngayketthuc.setText("");
        txtSDT.setText("");
        LoadData();
        btnLuu.setEnabled(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void txtsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsuaActionPerformed
        int row = 0;
        if (txtTenNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên Không Được Trống!");
        } else if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SDT Không Được Trống!");
        } else if (!(txtSDT.getText().matches("0[0-9]{9}"))) {
            JOptionPane.showMessageDialog(this, "SDT Không Hợp Lệ!");
        } else if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Email Không Được Trống!");
        } else if (!(txtEmail.getText().matches("[^@]{2,64}@[^.]{2,253}\\.[0-9a-z-.]{2,63}"))) {
            JOptionPane.showMessageDialog(this, "Email Không Hợp Lệ!");
        } else if (txtDiaChi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Địa Chỉ Không Được Trống!");
        } else if (txtluong1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Lương Không Được Trống!");
        } else if (Double.parseDouble(txtluong1.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Lương không được âm!");
        } else {
            update();
        }
    }//GEN-LAST:event_txtsuaActionPerformed

    private void txtTimNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimNVActionPerformed

    }//GEN-LAST:event_txtTimNVActionPerformed

    private void txtTimNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimNVKeyReleased

    }//GEN-LAST:event_txtTimNVKeyReleased

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        try {
            Connection con = new Conn().getConnection();
            PreparedStatement ps = con.prepareStatement("select * from nhanvien where SDT like '%" + txtTimNV1.getText() + "%'");
            ResultSet rs = ps.executeQuery();
            tblModel.setRowCount(0);
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(0, rs.getString("ID"));
                vector.add(1, rs.getString("TenNV"));
                vector.add(2, rs.getString("GioiTinh"));
                vector.add(3, rs.getString("SDT"));
                vector.add(4, rs.getString("Email"));
                vector.add(5, rs.getString("ChucVu"));
                vector.add(6, rs.getString("Luong"));
                vector.add(7, rs.getString("NgayTao"));
                vector.add(8, rs.getString("pass"));
                String x = rs.getString("Ngayketthuc");
                String ngayketthuc = "";
                if (x != null) {
                    ngayketthuc = x;
                }
                vector.add(9, ngayketthuc);
                vector.add(10, rs.getString("diachi"));
                tblModel.addRow(vector);
                rdoNam.setSelected(true);
                tblNhanVien.setRowSelectionInterval(0, 0);
                showDetails(0);

            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnTimActionPerformed

    private void txtTimNV1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimNV1ActionPerformed

    }//GEN-LAST:event_txtTimNV1ActionPerformed

    private void txtTimNV1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimNV1KeyReleased

    }//GEN-LAST:event_txtTimNV1KeyReleased

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void txtsua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsua1ActionPerformed
        // TODO add your handling code here:
        nghiViec();
    }//GEN-LAST:event_txtsua1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnTim;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbChucVu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimNV;
    private javax.swing.JTextField txtTimNV1;
    private javax.swing.JTextField txtluong1;
    private javax.swing.JTextField txtngayketthuc;
    private javax.swing.JButton txtsua;
    private javax.swing.JButton txtsua1;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setTitle(String quản_Lý_Sinh_Viên) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
