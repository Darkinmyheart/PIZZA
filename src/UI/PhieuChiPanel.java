/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.NhanVienDAO;
import DAO.PhieuChiDAO;
import DeskHelp.Decimal;
import DeskHelp.GetMa;
import Model.NhanVien;
import Model.PhieuChi;
import java.awt.event.ComponentAdapter;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import table.TableCustom;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author lethi
 */
public class PhieuChiPanel extends javax.swing.JPanel {
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<PhieuChi> ds  = new ArrayList<>();
    NhanVien nv;
 
    public PhieuChiPanel() {
        initComponents();
        ChangeStatus(0);
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        model = (DefaultTableModel) tblPhieuChi.getModel();
        
        //Them  ArrayList
        ds = new PhieuChiDAO().getArrayListAll();
        
        loadData(ds);
        
    }

    void ChangeStatus(int i){
        if(i==0){
            txtpanelchitite.setText("Phiếu chi chi tiết");
            btnthem.setVisible(false);
        }else{
            txtpanelchitite.setText("Thêm phiếu chi");
            btnthem.setVisible(true);
        }
    }
    
    void loadData(ArrayList<PhieuChi> ds){
        
        model.setRowCount(0);
        for (PhieuChi pc: ds){
            NhanVien nv = new NhanVienDAO().getObjectById(String.valueOf(pc.getIdNguoiLap()));
            model.addRow(new Object[]{pc.getId(),pc.getTongTien(),nv.getSdt()});
        }
    }
    void timKiem(String sdt){
        java.sql.Date jdatetu ;
        java.sql.Date jdateden ;
        if (jDateTu.getDate()==null) {
            jdatetu= Date.valueOf("1753-01-01");
        }else{
            jdatetu = new Date(jDateTu.getDate().getTime());
        }
        if(jDateDen.getDate()==null){
            jdateden=Date.valueOf("9999-12-31");
        }else{
            jdateden= new Date(jDateDen.getDate().getTime());
        }
        ArrayList<PhieuChi> dstimkiem = new PhieuChiDAO().getArrayListBySdtNguoiLap(sdt,jdatetu,jdateden);
        loadData(dstimkiem);
    }
    void showdetail(){
        for (PhieuChi pc : ds){
            if(pc.getId().equals(tblPhieuChi.getValueAt(tblPhieuChi.getSelectedRow(), 0))){
                txtSoPhieuChi.setText(pc.getId());
                txtNgayChi.setText(pc.getNgayLap().toString());
                txtSDTNhan.setText(tblPhieuChi.getValueAt(tblPhieuChi.getSelectedRow(), 2).toString());
                txtTenNguoiNhan.setText(new  NhanVienDAO().getObjectById(String.valueOf(pc.getIdNguoiLap())).getTenNV());
                txtSoTien.setText(Decimal.ConvertBigDecimalToStringFormat(pc.getTongTien()));
                txtLyDo.setText(pc.getLyDo());
            }
        }
        ChangeStatus(0);
    }
    boolean validateThem(){
        if(txtSDTNhan.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Chưa nhập số đt");
            return false;
        }else if(txtTenNguoiNhan.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Chưa có tên người nhận");
            return false;
        }else if(!txtSoTien.getText().matches("[0-9]+")){
            JOptionPane.showMessageDialog(this, "Số tiền phải là kiểu number");
            return false;
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jDateTu = new com.toedter.calendar.JDateChooser();
        jDateDen = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuChi = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtpanelchitite = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSoPhieuChi = new javax.swing.JTextField();
        txtNgayChi = new javax.swing.JTextField();
        txtSDTNhan = new javax.swing.JTextField();
        txtTenNguoiNhan = new javax.swing.JTextField();
        txtSoTien = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtLyDo = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        btnthem = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txttimkiem = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(910, 650));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PHIẾU CHI");

        jDateTu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateTuKeyReleased(evt);
            }
        });

        jDateDen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateDenKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Từ:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Đến: ");

        tblPhieuChi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu chi", "Số tiền", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuChi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblPhieuChiMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuChi);

        txtpanelchitite.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtpanelchitite.setForeground(new java.awt.Color(51, 51, 255));
        txtpanelchitite.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtpanelchitite.setText("Phiếu chi chi tiết");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Số phiếu chi: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Ngày chi:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Sdt nhận:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Tên người nhận:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel10.setText("Lý do:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Số tiền:");

        txtSoPhieuChi.setEditable(false);
        txtSoPhieuChi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtNgayChi.setEditable(false);
        txtNgayChi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtSDTNhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSDTNhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSDTNhanKeyReleased(evt);
            }
        });

        txtTenNguoiNhan.setEditable(false);
        txtTenNguoiNhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtSoTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtLyDo.setColumns(20);
        txtLyDo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtLyDo.setRows(5);
        jScrollPane2.setViewportView(txtLyDo);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("HỦY");

        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnthem.setText("THÊM");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        jButton2.setText("Check");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtpanelchitite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoPhieuChi))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgayChi))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSDTNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenNguoiNhan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoTien))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtpanelchitite, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSoPhieuChi)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNgayChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSDTNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoTien, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("SDT:");

        txttimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttimkiemKeyReleased(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setText("Thêm phiếu mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateTu, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateDen, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateTu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateDen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ChangeStatus(1);
        txtSoPhieuChi.setText(new GetMa().getMaPC());
        txtNgayChi.setText(new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()));
        txtSDTNhan.setText("");
        txtTenNguoiNhan.setText("");
        txtSoTien.setText("");
        txtLyDo.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txttimkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttimkiemKeyReleased
        // TODO add your handling code here:
        timKiem(txttimkiem.getText());
    }//GEN-LAST:event_txttimkiemKeyReleased

    private void jDateTuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateTuKeyReleased
        // TODO add your handling code here:
        timKiem(txttimkiem.getText());
    }//GEN-LAST:event_jDateTuKeyReleased

    private void jDateDenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateDenKeyReleased
        // TODO add your handling code here:
        timKiem(txttimkiem.getText());
    }//GEN-LAST:event_jDateDenKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        NhanVien nvchi = new  NhanVienDAO().getNhanVienBySDT(txtSDTNhan.getText());
        if(nvchi==null){
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên có sdt: "+txtSDTNhan.getText());
        }else{
            txtTenNguoiNhan.setText(nv.getTenNV());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblPhieuChiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuChiMousePressed
        // TODO add your handling code here:
        showdetail();
    }//GEN-LAST:event_tblPhieuChiMousePressed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        if(validateThem()){
            NhanVien nvchi = new  NhanVienDAO().getNhanVienBySDT(txtSDTNhan.getText());
            int check = new PhieuChiDAO().InsertSQL(new PhieuChi(txtSoPhieuChi.getText(), nv.getId(),  nvchi.getId()
                                                , LocalDate.now(), txtLyDo.getText(), BigDecimal.valueOf(Double.valueOf(txtSoTien.getText().replaceAll(",", "")))));
            if(check == 1){
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                ds = new PhieuChiDAO().getArrayListAll();
                loadData(ds);
            }
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void txtSDTNhanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSDTNhanKeyReleased
        // TODO add your handling code here:
        txtTenNguoiNhan.setText("");
    }//GEN-LAST:event_txtSDTNhanKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnthem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateDen;
    private com.toedter.calendar.JDateChooser jDateTu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPhieuChi;
    private javax.swing.JTextArea txtLyDo;
    private javax.swing.JTextField txtNgayChi;
    private javax.swing.JTextField txtSDTNhan;
    private javax.swing.JTextField txtSoPhieuChi;
    private javax.swing.JTextField txtSoTien;
    private javax.swing.JTextField txtTenNguoiNhan;
    private javax.swing.JLabel txtpanelchitite;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
