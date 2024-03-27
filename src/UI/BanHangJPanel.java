package UI;

import DAO.SanPhamDAO;
import DAO.loaiSPDAO;
import Model.HoaDonCT;
import Model.NhanVien;
import Model.SanPham;
import Model.LoaiSP;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import table.TableCustom;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author lethi
 */
public class BanHangJPanel extends javax.swing.JPanel {

    ArrayList<LoaiSP> dsLoai = null;
    DefaultTableModel model, model1;
    ArrayList<SanPham> ds = null;
    NhanVien nv ;

    public void setNv(NhanVien nv) {
        
        this.nv = nv;
        giohang.nv=nv;
    }
    
    public void reset(){
        ds = new ArrayList<>();
        ds = new SanPhamDAO().getArrayListAll();
         model = new DefaultTableModel();
        model = (DefaultTableModel) TBLsp.getModel();
        giohang.setBtnXoa(ds,model,TBLsp);
        LoadCBloai();
        txtPopup.setText("");
    }
    /**
     * Creates new form NewJPanel
     */
    
    public ArrayList<SanPham> getDs() {
        return ds;
    }
    public void resetUI(){
        this.removeAll();
        this.revalidate();
        this.repaint();
    }
    public BanHangJPanel() {
        initComponents();
        ds = new ArrayList<>();
        ds = new SanPhamDAO().getArrayListAll();
        giohang.banhang = this;
        
        TableCustom.apply(jScrollPane2, TableCustom.TableType.DEFAULT);

        //set model jtable
        model = new DefaultTableModel();
        model = (DefaultTableModel) TBLsp.getModel();
        giohang.setBtnXoa(ds,model,TBLsp);
        

        //set jspinner
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
        spinnersl.setModel(spinnerModel);
        JFormattedTextField txt = ((JSpinner.DefaultEditor) spinnersl.getEditor()).getTextField();
        txt.setEditable(false);

        txtmasp.setEnabled(false);
        LoadCBloai();
        LoadData(ds, model);
        CBloaiSP.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && CBloaiSP.isPopupVisible()) {
                    if (CBloaiSP.getSelectedIndex() != 0 && ds != null) {
                        model.setRowCount(0);
                        for (int i = 0; i < ds.size(); i++) {
                            String tenloai = new loaiSPDAO().getObjectById(String.valueOf(ds.get(i).getIdLoai())).getTenLoai();
                            if (tenloai.equalsIgnoreCase((String) CBloaiSP.getSelectedItem())) {
                                model.addRow(new Object[]{String.valueOf(ds.get(i).getId()), ds.get(i).getTenSP(), tenloai,
                                    String.valueOf(ds.get(i).getSoLuong()), String.valueOf(ds.get(i).getDonGia()), ds.get(i).getDvt()});
                            }
                            if (TBLsp.getRowCount() > 0) {
                                TBLsp.setRowSelectionInterval(0, 0);
                                showDetail(TBLsp.getSelectedRow());
                            } else {
                                txtmasp.setText("");
                                txttensp.setText("");
                                txtdongia.setText("");
                                txtgia.setText("");
                            }
                        }
                    } else if (CBloaiSP.getSelectedItem() != null && CBloaiSP.getSelectedItem().toString().equalsIgnoreCase("all") && ds != null) {
                        LoadData(ds, model);
                    }
                }
            }
        });
        Totalgia1sp();
    }

    void clearform() {
        txtmasp.setText("");
        txttensp.setText("");
        txtdongia.setText("");
        txtgia.setText("");
        spinnersl.setValue(0);
    }


    private void LoadCBloai() {
        dsLoai = new ArrayList<>();
        dsLoai = new loaiSPDAO().getArrayListAll();
        CBloaiSP.removeAllItems();
        CBloaiSP.addItem("all");
        for (int i = 0; i < dsLoai.size(); i++) {
            CBloaiSP.addItem(dsLoai.get(i).getTenLoai());
        }

    }

    void showDetail(int index) {

        txtmasp.setText(String.valueOf(TBLsp.getValueAt(index, 0)));
        for (int i = 0; i < CBloaiSP.getItemCount(); i++) {
            if (CBloaiSP.getItemAt(i).equalsIgnoreCase(String.valueOf(TBLsp.getValueAt(index, 2)))) {
                CBloaiSP.setSelectedIndex(i);
            }
        }
        txttensp.setText(String.valueOf(TBLsp.getValueAt(index, 1)));
        txtdongia.setText(String.valueOf(TBLsp.getValueAt(index, 4)));
        spinnersl.setValue(1);
        Totalgia1sp();
    }

    void LoadData(ArrayList<SanPham> ds, DefaultTableModel model) {
        model.setRowCount(0);
        if (ds != null) {
            for (int i = 0; i < ds.size(); i++) {
                String tenloai = new loaiSPDAO().getObjectById(String.valueOf(ds.get(i).getIdLoai())).getTenLoai();
                model.addRow(new Object[]{String.valueOf(ds.get(i).getId()), ds.get(i).getTenSP(), tenloai,
                    String.valueOf(ds.get(i).getSoLuong()), String.valueOf(ds.get(i).getDonGia()), ds.get(i).getDvt()});
            }
        }
    }

    void btnTimKiem() {
        ArrayList<SanPham> ds1 = new ArrayList<>();
        for (SanPham sp : ds) {
            if ((sp.getTenSP().toLowerCase()).indexOf(txttimkiem.getText().toLowerCase()) != -1) {
                ds1.add(sp);
            }
        }
        LoadData(ds1, model);
    }

    private void Totalgia1sp() {
        if (!txtdongia.getText().isEmpty()) {
            txtgia.setText(String.valueOf((int) (Double.parseDouble(String.valueOf(spinnersl.getValue())) * Double.parseDouble(txtdongia.getText()))));
        } else {
            txtgia.setText("");
        }
    }

    boolean checkSoLuongConLai() {
        boolean flag = false;
        for (SanPham sp : ds) {
            if (sp.getId()==Integer.parseInt(txtmasp.getText())) {
                if (sp.getSoLuong()>= Integer.parseInt(String.valueOf(spinnersl.getValue())) && sp.getSoLuong()> 0) {
                    flag = true;
                    sp.setSoLuong(sp.getSoLuong()- Integer.parseInt(String.valueOf(spinnersl.getValue())));
                    if (!txttimkiem.getText().isEmpty()) {
                        btnTimKiem();
                    } else {
                        LoadData(ds, model);
                    }
                    return true;
                }
            }
        }
        if(!flag){
                    JOptionPane.showMessageDialog(this, "Het san pham !!!");
        return false;
        }
        return false;
    }

    void PopupThanhToan() {

        if (!txtPopup.getText().isEmpty()) {
            String popup = txtPopup.getText() + "\n" + jLabelTime1.getText() + ": Đã thêm " + spinnersl.getValue().toString() + " " + txttensp.getText();
            System.out.println(popup);
            txtPopup.setText(popup);
        } else {
            String popup = jLabelTime1.getText() + ": Đã thêm " + spinnersl.getValue().toString() + " " + txttensp.getText();
            System.out.println(popup);
            txtPopup.setText(popup);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        banHangJpanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TBLsp = new javax.swing.JTable();
        txttimkiem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtmasp = new javax.swing.JTextField();
        CBloaiSP = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txttensp = new javax.swing.JTextField();
        txtdongia = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        spinnersl = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        BtnThemGioHang = new javax.swing.JButton();
        txtgia = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPopup = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabelDate1 = new JLabelDungChung.JLabelDate();
        jLabelTime1 = new JLabelDungChung.JLabelTime();
        giohang = new UI.GioHangJPanel();

        setMinimumSize(new java.awt.Dimension(910, 650));
        setPreferredSize(new java.awt.Dimension(910, 650));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Danh sách sản phẩm");

        TBLsp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TBLsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên SP", "Loại", "Số lượng", "Đơn giá", "Đơn vị tính"
            }
        ));
        TBLsp.setRowMargin(1);
        TBLsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TBLspMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(TBLsp);

        txttimkiem.setPreferredSize(new java.awt.Dimension(192, 25));
        txttimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttimkiemKeyReleased(evt);
            }
        });

        jLabel8.setText("Tìm kiếm:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Mã SP:");
        jLabel6.setPreferredSize(new java.awt.Dimension(63, 22));

        txtmasp.setEditable(false);
        txtmasp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        CBloaiSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CBloaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Loại :");
        jLabel4.setPreferredSize(new java.awt.Dimension(63, 22));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Tên:");
        jLabel5.setPreferredSize(new java.awt.Dimension(63, 22));

        txttensp.setEditable(false);
        txttensp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtdongia.setEditable(false);
        txtdongia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Đơn giá");
        jLabel7.setPreferredSize(new java.awt.Dimension(63, 22));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("Số lượng");

        spinnersl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        spinnersl.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerslStateChanged(evt);
            }
        });
        spinnersl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                spinnerslMousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("Giá :");
        jLabel13.setPreferredSize(new java.awt.Dimension(63, 22));

        BtnThemGioHang.setBackground(new java.awt.Color(0, 153, 153));
        BtnThemGioHang.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        BtnThemGioHang.setForeground(new java.awt.Color(255, 255, 255));
        BtnThemGioHang.setText("Thêm giỏ hàng");
        BtnThemGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnThemGioHangActionPerformed(evt);
            }
        });

        txtgia.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtgia.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(1, 1, 1))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtdongia, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBloaiSP, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txttensp, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnersl, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmasp)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtgia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnThemGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtmasp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CBloaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttensp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdongia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnersl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnThemGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Ngày : ");

        txtPopup.setEditable(false);
        txtPopup.setColumns(20);
        txtPopup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtPopup.setForeground(new java.awt.Color(102, 255, 102));
        txtPopup.setRows(5);
        jScrollPane3.setViewportView(txtPopup);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Giờ:");

        jLabelDate1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelDate1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDate1.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N

        jLabelTime1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelTime1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime1.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N

        javax.swing.GroupLayout banHangJpanelLayout = new javax.swing.GroupLayout(banHangJpanel);
        banHangJpanel.setLayout(banHangJpanelLayout);
        banHangJpanelLayout.setHorizontalGroup(
            banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(banHangJpanelLayout.createSequentialGroup()
                .addGroup(banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(banHangJpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(banHangJpanelLayout.createSequentialGroup()
                                .addGroup(banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, banHangJpanelLayout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelDate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, banHangJpanelLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(35, 35, 35)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTime1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(26, 26, 26)
                        .addGroup(banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)))
                    .addGroup(banHangJpanelLayout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(jLabel1)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        banHangJpanelLayout.setVerticalGroup(
            banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(banHangJpanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(banHangJpanelLayout.createSequentialGroup()
                        .addGroup(banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(banHangJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDate1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BÁN HÀNG", banHangJpanel);
        jTabbedPane1.addTab("GIỎ HÀNG", giohang);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtnThemGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnThemGioHangActionPerformed
        // TODO add your handling code here:

        int sl = 0;
        if (checkSoLuongConLai()) {
            if (!txtmasp.getText().isEmpty() && String.valueOf(spinnersl.getValue()) != "0") {
                giohang.themSanPhamVaoTable(new HoaDonCT(giohang.hd.getId(), Integer.parseInt(txtmasp.getText()), Integer.parseInt(String.valueOf(spinnersl.getValue()))));
                PopupThanhToan();
            }
        }
    }//GEN-LAST:event_BtnThemGioHangActionPerformed

    private void spinnerslMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spinnerslMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_spinnerslMousePressed

    private void spinnerslStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerslStateChanged
        // TODO add your handling code here:

        Totalgia1sp();
    }//GEN-LAST:event_spinnerslStateChanged

    private void txttimkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttimkiemKeyReleased
        // TODO add your handling code here:
        btnTimKiem();

    }//GEN-LAST:event_txttimkiemKeyReleased

    private void TBLspMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBLspMousePressed
        // TODO add your handling code here:
        showDetail(TBLsp.getSelectedRow());
    }//GEN-LAST:event_TBLspMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnThemGioHang;
    private javax.swing.JComboBox<String> CBloaiSP;
    private javax.swing.JTable TBLsp;
    private javax.swing.JPanel banHangJpanel;
    private UI.GioHangJPanel giohang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private JLabelDungChung.JLabelDate jLabelDate1;
    private JLabelDungChung.JLabelTime jLabelTime1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner spinnersl;
    private javax.swing.JTextArea txtPopup;
    private javax.swing.JTextField txtdongia;
    private javax.swing.JLabel txtgia;
    private javax.swing.JTextField txtmasp;
    private javax.swing.JTextField txttensp;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
