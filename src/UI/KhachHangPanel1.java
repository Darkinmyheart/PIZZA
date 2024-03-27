package UI;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class KhachHangPanel1 extends javax.swing.JPanel {

    DefaultTableModel model = new DefaultTableModel();

    Connection con ;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int index = -1;

    /**
     * Creates new form KhachHangPanel
     */
    public KhachHangPanel1() {
        initComponents();
        try {
            con = new Conn.Conn().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangPanel1.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtMaKH.setRequestFocusEnabled(false);
        txtDiemTL.setRequestFocusEnabled(false);
        txtDiemTL.setText("0");
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        txtNgay.setEditable(false);
        LoadData("");
    }

    public void LoadData(String x) {

        model = (DefaultTableModel) tblKhachHang.getModel();
        try {
            model.setRowCount(0);
            st = con.createStatement();
            String query = "SELECT * FROM KhachHang " + x;
            rs = st.executeQuery(query);
            while (rs.next()) {

                Integer ID = rs.getInt("ID");
                String Ten = rs.getString("Ten");
                String GioiTinh = rs.getString("GioiTinh");
                String SDT = rs.getString("SDT");
                Integer DiemTL = rs.getInt("DiemTichLuy");
                Date NgayTao = rs.getDate("NgayTao");

                model.addRow(new Object[]{ID, Ten, GioiTinh, SDT, DiemTL, NgayTao});
            }

            tblKhachHang.setModel(model);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ShowDate() {
        int row = tblKhachHang.getSelectedRow();

        txtMaKH.setText(tblKhachHang.getValueAt(row, 0).toString());
        txtTenKH.setText(tblKhachHang.getValueAt(row, 1).toString());
        if (tblKhachHang.getValueAt(row, 2).toString().equals("Nam")) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtSDT.setText(tblKhachHang.getValueAt(row, 3).toString());
        txtDiemTL.setText(tblKhachHang.getValueAt(row, 4).toString());
        txtNgay.setText(tblKhachHang.getValueAt(row, 5).toString());

    }

    public void add() {
        try {
            st = con.createStatement();

            String Ten = txtTenKH.getText();
            String GioiTinh = "";
            if (rdoNam.isSelected()) {
                GioiTinh = "Nam";
            } else if (rdoNu.isSelected()) {
                GioiTinh = "Nữ";
            }

            String SDT = txtSDT.getText();
            Integer DiemTL = Integer.parseInt(txtDiemTL.getText());

            String query = "INSERT INTO KhachHang (Ten, GioiTinh, SDT, DiemTichLuy,ngaytao) VALUES ( N'" + Ten + "', N'" + GioiTinh + "', '" + SDT + "', " + DiemTL + ",getdate())";
            st.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Đã thêm!");
            LoadData("");

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;
    
    public int getMaKH() {
        int a = 0;
        try {
            st = con.createStatement();
            String query = "SELECT max(ID) as 'dem' FROM KhachHang";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                a = rs.getInt("dem");
            }
            a = a + 1;

            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }

    public boolean SDT(String sdt) {
        boolean check = false;
        try {
            st = con.createStatement();
            String query = "SELECT COUNT(*) as 'count' FROM KhachHang WHERE SDT = '" + sdt + "'";
            ResultSet rs = st.executeQuery(query);
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

    public void sua() {
        try {

            String query = "UPDATE KhachHang SET Ten = ?, GioiTinh=?, SDT = ?, DiemTichLuy=? WHERE ID = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, txtTenKH.getText());

            String gt;
            if (rdoNam.isSelected()) {
                gt = "Nam";
            } else {
                gt = "Nữ";
            }

            pst.setString(2, gt);
            pst.setString(3, txtSDT.getText());
            pst.setString(4, txtDiemTL.getText());

            pst.setString(5, txtMaKH.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Đã Sửa");
            
            LoadData("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    boolean validates() {
        String TenKH = txtTenKH.getText();
        String SDT = txtSDT.getText();
        String DiemTL = txtDiemTL.getText();
        int row = tblKhachHang.getSelectedRow();
        // Tên Khách Hàng
        if (TenKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên trang trống!");
            txtTenKH.requestFocus();
            return false;
        } else if (!TenKH.matches("[A-Za-z\\s\\p{L}]+")) {
            JOptionPane.showMessageDialog(this, "Tên là chữ cái!");
            txtTenKH.requestFocus();
            return false;
        } // Giới Tính
        else if (!(rdoNu.isSelected() || rdoNam.isSelected())) {
            JOptionPane.showMessageDialog(this, "Chọn giới tính!");
            rdoNu.requestFocus();
            return false;
        } // SDT
        else if (SDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại trống");
            txtSDT.requestFocus();
            return false;
        } else if (!(txtSDT.getText().matches("0[0-9]{9}"))) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không đúng !", "Chu y", 1);
            txtSDT.requestFocus();
            return false;
        } else {
            return true;
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
        txtTimKiem = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDiemTL = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNgay = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(910, 650));

        txtTimKiem.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setText("Tìm Khách Hàng Theo SDT:");

        tblKhachHang.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khách Hàng", "Tên Khách Hàng", "Giới Tính", "SDT", "Điểm Tích Lũy", "Ngày Tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setRowHeight(30);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKhachHangMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Mã KH:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Tên KH:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Giới Tính:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("SDT:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Điểm Tích Lũy:");

        txtDiemTL.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        txtSDT.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        txtTenKH.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        txtMaKH.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        rdoNu.setText("Nữ");

        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jButton12.setText("NEW");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Ngày:");

        txtNgay.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(64, 64, 64)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenKH)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rdoNu))
                                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSDT)
                                    .addComponent(txtDiemTL, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(txtNgay))))
                        .addGap(113, 113, 113)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDiemTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        LoadData("where SDT like N'%" + txtTimKiem.getText() + "%'");
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        LoadData("where SDT like N'%" + txtTimKiem.getText() + "%'");

    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked

    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void tblKhachHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMousePressed
        ShowDate();
        btnThem.setEnabled(false);
        btnSua.setEnabled(true);
    }//GEN-LAST:event_tblKhachHangMousePressed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int row = tblKhachHang.getSelectedRow();

        if (row != -1) {
            if (validates()) {
                String newPhoneNumber = txtSDT.getText();
                boolean phoneNumberExists = false;

                for (int i = 0; i < tblKhachHang.getRowCount(); i++) {
                    if(i==tblKhachHang.getSelectedRow()){
                        i= i +1;
                    }
                    if (i != row) {
                        String phoneNumber = tblKhachHang.getValueAt(i, 3).toString();
                        if (newPhoneNumber.equals(phoneNumber)) {
                            phoneNumberExists = true;
                            break;
                        }
                    }
                    
                }

                if (phoneNumberExists) {
                    JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại!");
                    return;
                }
                sua();
                LoadData("");
                tblKhachHang.setRowSelectionInterval(row, row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chua chon khach hang !");
        }

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validates()) {
            if (SDT(txtSDT.getText())) {
                JOptionPane.showMessageDialog(this, "Trùng số điện thoại");
                return;
            }
            add();
            btnThem.setEnabled(false);
        }


    }//GEN-LAST:event_btnThemActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        btnThem.setEnabled(true);
        txtMaKH.setText(String.valueOf(getMaKH()));
        txtDiemTL.setText("0");
        txtTenKH.setText("");
        txtSDT.setText("");
        txtTimKiem.setText("");
        buttonGroup1.clearSelection();
        btnSua.setEnabled(false);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = formatter.format(currentDate);
        txtNgay.setText(formattedDate);
        LoadData("");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void txtNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayActionPerformed

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton12;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextField txtDiemTL;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNgay;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
