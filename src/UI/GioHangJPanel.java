/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.ChuongTrinhKMDAO;
import DeskHelp.GetMa;
import DeskHelp.Decimal;
import DAO.KhachHangDAO;
import DAO.MaKhuyenMaiDAO;
import DAO.SanPhamDAO;
import DAO.loaiSPDAO;
import Model.ChuongTrinhKM;
import Model.HoaDonCT;
import Model.KhachHang;
import Model.SanPham;
import Model.HoaDon;
import Model.MaKhuyenMai;
import Model.NhanVien;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import table.TableCustom;

/**
 *
 * @author lethi
 */
public class GioHangJPanel extends javax.swing.JPanel {
    BanHangJPanel banhang ;
    ArrayList<HoaDonCT> dshdct = new ArrayList<>();
    JPanel mainJPanel;
    MaKhuyenMai ma = null;
    ChuongTrinhKM ctkm = null;
    DefaultTableModel model;
    KhachHang kh = null;
    NhanVien nv;
    HoaDon hd = new HoaDon(new GetMa().getMaHD(), 0, -1, LocalDate.now(), BigDecimal.valueOf(0), 0, null, null);
    double diemtichluy, total;
    HoaDonJframe nj;

    /**
     * Creates new form HoaDonJPanel
     */
    public GioHangJPanel() {
        initComponents();
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        txtmahoadon.setText(new DeskHelp.GetMa().getMaHD());
        model = new DefaultTableModel();
        model = (DefaultTableModel) tblGioHang.getModel();

    }

    ArrayList<SanPham> setBtnXoa(ArrayList<SanPham> dssp, DefaultTableModel model1, JTable tbl) {
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tblGioHang.getSelectedRow() != -1) {
                    for (HoaDonCT hdct : dshdct) {
                        if (hdct.getIdSp() == (Integer) tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 0)) {
                            dshdct.remove(hdct);
                            break;
                        }
                    }
                    if (dssp != null) {
                        for (SanPham sp : dssp) {
                            int masp = Integer.parseInt(String.valueOf(tblGioHang.getValueAt(Integer.parseInt(String.valueOf(tblGioHang.getSelectedRow())), 0)));
                            if (masp == sp.getId()) {
                                int sltrongtbl = Integer.parseInt(String.valueOf(tblGioHang.getValueAt(Integer.parseInt(String.valueOf(tblGioHang.getSelectedRow())), 2)));
                                int sl = sp.getSoLuong();
                                sp.setSoLuong(sl + sltrongtbl);
                                model1.setRowCount(0);
                                for (int i = 0; i < dssp.size(); i++) {
                                    String tenloai = new loaiSPDAO().getObjectById(String.valueOf(dssp.get(i).getIdLoai())).getTenLoai();
                                    model1.addRow(new Object[]{String.valueOf(dssp.get(i).getId()), dssp.get(i).getTenSP(), tenloai,
                                        String.valueOf(dssp.get(i).getSoLuong()), String.valueOf(dssp.get(i).getDonGia()), dssp.get(i).getDvt()});
                                }
                                break;
                            }
                        }
                    }
                }

                loadDaTa(model, dshdct);
                new BanHangJPanel().LoadData(dssp, model1);
                showtotal();
            }
        });
        return dssp;
    }

    private void suDungDiem() { //sử dụng điểm tích lũy trừ vào total, giữ lại tổng để phòng trường hợp thay đổi điểm
        if (kh != null) {
            while (true) {
                String diem = JOptionPane.showInputDialog(this, "Khách hàng: " + kh.getTen() + " có " + String.valueOf(kh.getDiemTichLuy()) + " . Muốn sử dụng bao nhiêu điểm ?", "Điểm tích lũy", JOptionPane.PLAIN_MESSAGE);
                if (diem != null) {
                    if (!diem.isEmpty()) {
                        if (!diem.matches("[0-9]+")) {
                            JOptionPane.showMessageDialog(this, "Input number !!!");
                        } else if (Integer.valueOf(diem) > kh.getDiemTichLuy() || Integer.valueOf(diem) < 0) {
                            JOptionPane.showMessageDialog(this, "Nhap it thoi. Qua gioi han roi !!!");
                        } else if (Double.parseDouble(diem) * 1000 > total) {
                            JOptionPane.showMessageDialog(this, "Tối đa được sử dụng: " + String.valueOf(total));
                        } else {
                            txtdiem.setText(diem);
                            diemtichluy = Integer.parseInt(diem);
                            txtToTal.setText(String.valueOf(new Decimal().ConvertDoubleToDecimal(total - (Double.valueOf(diemtichluy) * 1000))));
//                            total = Float.valueOf(txtToTal.getText().substring(0, txtToTal.getText().length() - 5));
                            txtTienGiam.setText("Đã giảm: " + new Decimal().ConvertDoubleToDecimal(Double.parseDouble(diem) * 1000) + " VND");
                            giamTienKM();
                            break;
                        }
                    }
                } else {
                    break;
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa có thông tin khách hàng");
        }
    }

    KhachHang hienThiKhachHang() {
        String inputsdt = JOptionPane.showInputDialog(this, "Nhập sdt khách hàng: ", "Tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        KhachHang kh = new KhachHangDAO().getkhachHangFromSDT(inputsdt);

        if (kh != null) {
            String nd = " - Tên khách hàng: " + kh.getTen() + ".\n"
                    + "- Giới tính: " + kh.getGioiTinh() + ".\n"
                    + "- Số điện thoại: " + kh.getSdt() + ".\n"
                    + "- Điểm tích lũy: " + kh.getDiemTichLuy() + " điểm .\n";
            int rp = JOptionPane.showConfirmDialog(this, nd, "Thông tin khách hàng", JOptionPane.YES_NO_OPTION);
            if (rp == JOptionPane.YES_OPTION) {
                txtsdtkh.setText(kh.getSdt());
                hd.setIdKhachHang(kh.getId());
                return kh;
            } else if (rp == JOptionPane.NO_OPTION) {
                txtsdtkh.setText("");
                return null;
            } else {
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Not found !");
            txtsdtkh.setText("");
            return null;
        }

    }

    void loadDaTa(DefaultTableModel model, ArrayList<HoaDonCT> ds) {
        model.setRowCount(0);
        for (int i = 0; i < ds.size(); i++) {
            SanPham sp = new SanPhamDAO().getObjectById(String.valueOf(ds.get(i).getIdSp()));// LAY SANPHAM WHERE IDsP= HDCT.IDSP
            model.addRow(new Object[]{ds.get(i).getIdSp(), sp.getTenSP(), ds.get(i).getSoLuong(), sp.getDonGia(), Decimal.COnvertIntToBigDecimal(ds.get(i).getSoLuong()).multiply(sp.getDonGia())});
        }
    }

    void themSanPhamVaoTable(HoaDonCT x) { //THEM HDCT VAO ARRAYLIST<HOADONCT>

        boolean flag = true;
        for (int i = 0; i < dshdct.size(); i++) {
            if (dshdct.get(i).getIdSp() == x.getIdSp()) {
                System.out.println(1);
                int soluong = dshdct.get(i).getSoLuong() + x.getSoLuong();
                dshdct.get(i).setSoLuong(soluong);
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.println("2");
            dshdct.add(new HoaDonCT(x.getIdHoaDon(), x.getIdSp(), x.getSoLuong()));
        }
        loadDaTa(model, dshdct);
        showtotal();
    }

    void showtotal() { // dùng để reset total
        double total1 = 0;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            total1 = total1 + Double.parseDouble(String.valueOf(tblGioHang.getValueAt(i, 4)));
        }
        total = total1;
        txtToTal.setText(new Decimal().ConvertDoubleToDecimal(total));
        if (!txtdiem.getText().isEmpty()) {
            String diem = txtdiem.getText();
            diemtichluy = Integer.parseInt(diem);
            txtToTal.setText(String.valueOf(new Decimal().ConvertDoubleToDecimal(total - (Double.valueOf(diemtichluy) * 1000))));
//                            total = Float.valueOf(txtToTal.getText().substring(0, txtToTal.getText().length() - 5));
            txtTienGiam.setText("Đã giảm: " + new Decimal().ConvertDoubleToDecimal(Double.parseDouble(diem) * 1000) + " VND");
            giamTienKM();
        } else if (!txtgiamgiaKM.getText().isEmpty()) {
            giamTienKM();
        }
    }

    void giamTienKM() {
        if (ctkm != null) {
            int giamgia = ctkm.getPhanTramGiam();
            txtgiamgiaKM.setText(giamgia + " % ");
            String totalText = txtToTal.getText().replace(",", "");
            if (totalText != null || !totalText.equals("0")) {
                float total1 = Float.valueOf(txtToTal.getText().replaceAll("\\,", ""));
                totalText = String.valueOf(total1 - total1 * (Float.parseFloat(String.valueOf(ctkm.getPhanTramGiam())) / 100));
            }
            txtToTal.setText(new Decimal().ConvertDoubleToDecimal(Double.valueOf(totalText)));
        }
    }

    void themMaKhuyenMai() {
        String code = JOptionPane.showInputDialog(this, "Nhập code khuyến mãi");
        if (code != null) {
            ma = new MaKhuyenMaiDAO().getMaKhuyenMaiByCode(code);
            if (ma != null) {
                ctkm = new ChuongTrinhKMDAO().getObjectById(ma.getIdCTKM());
                if (ctkm.getNgayKetThuc().isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "Mã hết hạn.");
                } else if (ma.getNgaySuDung() != null) {
                    JOptionPane.showMessageDialog(this, "Mã đã sử dụng.");
                } else {
                    txtMaKhuyenMai.setText(" " + ma.getCode() + " ");
                    txtMoTaKM.setText(" " + ctkm.getMoTa() + " ");
                    txtgiamgiaKM.setText(String.valueOf(" " + ctkm.getPhanTramGiam()) + " % ");
                    showtotal();
                    hd.setMaCTKM(ma.getIdCTKM());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã không tồn tại.");
            }
        }
    }

    void reset() {
        banhang.reset();
        txtmahoadon.setText(new DeskHelp.GetMa().getMaHD());
        model.setRowCount(0);
        dshdct = new ArrayList<>();
        txtToTal.setText("");
        txtsdtkh.setText("");
        txtMaKhuyenMai.setText("");
        txtMaKhuyenMai.setText("");
        txtMoTaKM.setText("");
        txtgiamgiaKM.setText("");
        hd = new HoaDon(new GetMa().getMaHD(), 0, -1, LocalDate.now(), BigDecimal.valueOf(0), 0, null, null);
        diemtichluy = 0;
        total=0;
        kh = null;
        ctkm = null;
        nj.dispose();
        nj = new HoaDonJframe();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtToTal = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtMaKhuyenMai = new javax.swing.JLabel();
        txtsdtkh = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtdiem = new javax.swing.JLabel();
        BtnThemGioHang2 = new javax.swing.JButton();
        BtnThemGioHang1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtTienGiam = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtmahoadon = new javax.swing.JLabel();
        btnMaKhuyenMai = new javax.swing.JButton();
        txtMoTaKM = new javax.swing.JTextField();
        txtgiamgiaKM = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.setRowMargin(1);
        jScrollPane1.setViewportView(tblGioHang);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Giỏ hàng");

        txtToTal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtToTal.setForeground(new java.awt.Color(51, 51, 255));
        txtToTal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtToTal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnXoa.setBackground(new java.awt.Color(0, 153, 153));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setPreferredSize(new java.awt.Dimension(86, 43));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 153, 153));
        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Thanh toán");
        jButton10.setPreferredSize(new java.awt.Dimension(120, 43));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 255));
        jLabel15.setText("TOTAL:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 255));
        jLabel16.setText("Mã khuyến mãi:");

        txtMaKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMaKhuyenMai.setForeground(new java.awt.Color(51, 51, 255));
        txtMaKhuyenMai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtMaKhuyenMai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtsdtkh.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtsdtkh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("SĐT: ");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("ĐIỂM SỬ DỤNG:");

        txtdiem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtdiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        BtnThemGioHang2.setBackground(new java.awt.Color(0, 153, 153));
        BtnThemGioHang2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        BtnThemGioHang2.setForeground(new java.awt.Color(255, 255, 255));
        BtnThemGioHang2.setText("DÙNG ĐIỂM");
        BtnThemGioHang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnThemGioHang2ActionPerformed(evt);
            }
        });

        BtnThemGioHang1.setBackground(new java.awt.Color(0, 153, 153));
        BtnThemGioHang1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        BtnThemGioHang1.setForeground(new java.awt.Color(255, 255, 255));
        BtnThemGioHang1.setText("Thêm khách hàng");
        BtnThemGioHang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnThemGioHang1ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setText("Miêu tả");

        txtTienGiam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTienGiam.setForeground(new java.awt.Color(255, 51, 51));
        txtTienGiam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Mã:");

        txtmahoadon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnMaKhuyenMai.setBackground(new java.awt.Color(0, 153, 153));
        btnMaKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnMaKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnMaKhuyenMai.setText("Mã khuyến mãi");
        btnMaKhuyenMai.setPreferredSize(new java.awt.Dimension(120, 43));
        btnMaKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaKhuyenMaiActionPerformed(evt);
            }
        });

        txtMoTaKM.setEditable(false);
        txtMoTaKM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMoTaKM.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("GIẢM GIÁ:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtmahoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 237, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BtnThemGioHang1)
                        .addGap(18, 18, 18)
                        .addComponent(BtnThemGioHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtToTal, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                            .addComponent(txtsdtkh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGap(148, 148, 148)
                                        .addComponent(txtTienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(98, 98, 98))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtdiem, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                .addGap(99, 99, 99)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                        .addComponent(txtMoTaKM))
                                    .addComponent(txtgiamgiaKM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 33, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(11, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmahoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtToTal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(txtsdtkh, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(txtMoTaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtdiem, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtgiamgiaKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnThemGioHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnThemGioHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if (!txtToTal.getText().isEmpty()) {
            hd.setThanhTien(BigDecimal.valueOf(Double.parseDouble(txtToTal.getText().replaceAll("\\,", ""))));
            hd.setIdNguoiLap(nv.getId());
            System.out.println(hd.toString());
            nj = new HoaDonJframe();
            nj.giohang= this;
            double a = 0;
            if ((!txtdiem.getText().isEmpty()) && txtdiem.getText() != null) {
                a = Double.valueOf(txtdiem.getText());
            }
            nj.setHoaDon(dshdct, hd, total, a);
            System.out.println(total);
            nj.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Chua co hang");
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void BtnThemGioHang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnThemGioHang2ActionPerformed
        // TODO add your handling code here:
        suDungDiem();
    }//GEN-LAST:event_BtnThemGioHang2ActionPerformed

    private void BtnThemGioHang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnThemGioHang1ActionPerformed
        // TODO add your handling code here:
        kh = hienThiKhachHang();
    }//GEN-LAST:event_BtnThemGioHang1ActionPerformed

    private void btnMaKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaKhuyenMaiActionPerformed
        // TODO add your handling code here:
        themMaKhuyenMai();
    }//GEN-LAST:event_btnMaKhuyenMaiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnThemGioHang1;
    private javax.swing.JButton BtnThemGioHang2;
    private javax.swing.JButton btnMaKhuyenMai;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton jButton10;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JLabel txtMaKhuyenMai;
    private javax.swing.JTextField txtMoTaKM;
    private javax.swing.JLabel txtTienGiam;
    private javax.swing.JLabel txtToTal;
    private javax.swing.JLabel txtdiem;
    private javax.swing.JLabel txtgiamgiaKM;
    private javax.swing.JLabel txtmahoadon;
    private javax.swing.JLabel txtsdtkh;
    // End of variables declaration//GEN-END:variables
}
