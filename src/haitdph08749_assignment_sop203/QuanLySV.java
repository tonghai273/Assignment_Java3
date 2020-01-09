/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haitdph08749_assignment_sop203;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LaptopAZ
 */
public class QuanLySV extends javax.swing.JFrame {
     private  Connection con;
     DefaultTableModel model;
     ArrayList<student> list= new ArrayList<>();
     String imageName;
     String path;
     int index=0;
    /**
     * Creates new form QuanLySV
     */
    public QuanLySV() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        model = (DefaultTableModel) tbHienthi.getModel();
        buttonGroup1.add(rdoNam);
        buttonGroup1.add(rdoNu);
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLYSINHVIEN;user=sa;password=haiha");
        } catch (Exception e) {
            System.out.println(e);
        }
        list = getListStudent();
        loadDbToTable();
    }
//    public  void upImage(String imageName){
//        ImageIcon img = new ImageIcon("E:\\JAVA\\Haitdph08749_Assignment_SOF203\\src\\img\\"+imageName+".jpg");
//        Image image = img.getImage();
//        ImageIcon anh = new ImageIcon(image.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), image.SCALE_SMOOTH));
//        lblAnh.setIcon(anh);
//    }
    
    public  ArrayList<student> getListStudent(){
        try{
            String sql="SELECT * FROM STUDENTS";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                student sv = new student();
                sv.setMasv(rs.getString("MASV"));
                sv.setTensv(rs.getString("HOTEN"));
                sv.setEmail(rs.getString("EMAIL"));
                sv.setPhone(rs.getString("SODT"));
                sv.setGioitinh(rs.getBoolean("GIOITINH"));
                sv.setDiachi(rs.getString("DIACHI"));
                sv.setAnh(rs.getString("HINH"));
                list.add(sv);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
        return list;
    }
    
    public boolean check() {
        if (txtMaSV.getText().equals("") || txtHovaten.getText().equals("") || txtEmail.getText().equals("")
                || txtSĐT.getText().equals("") || txtDiachi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu sau đó ấn Save");
            return false;
        } else if (!txtEmail.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "Sai định dạng email");
            txtEmail.requestFocus();
            return false;
        } else if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn giới tính");
            return false;
        }
        try{
            int sdt = Integer.parseInt(txtSĐT.getText());
            if(sdt<10){
                JOptionPane.showMessageDialog(this, "Sai định dạng số điện thoại");
                txtSĐT.requestFocus();
            }
            else{
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Số điện thoại không đúng định dạng số");
            txtMaSV.requestFocus();
        }
        return true;
    }
    public void save(){
        if(check()){
            
        try{
        String sql = "INSERT INTO STUDENTS(MASV, HOTEN, EMAIL, SODT, GIOITINH, DIACHI, HINH) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, txtMaSV.getText());
        ps.setString(2, txtHovaten.getText());
        ps.setString(3, txtEmail.getText());
        ps.setString(4, txtSĐT.getText());
        boolean sex;
        if(rdoNam.isSelected()){
            sex=true;
        }
        else{
            sex=false;
        }
        ps.setBoolean(5, sex);
        ps.setString(6, txtDiachi.getText());
        ps.setString(7,path);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Lưu thành công");
        
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
        student sv = new student();
        sv.setMasv(txtMaSV.getText());
        sv.setTensv(txtHovaten.getText());
        sv.setEmail(txtEmail.getText());
        sv.setPhone(txtSĐT.getText());
        boolean gt;
        if(rdoNam.isSelected()){
            gt=true;
        }
        else{
            gt=false;
        }
        sv.setGioitinh(gt);
        sv.setDiachi(txtDiachi.getText());
        sv.setAnh(path);
        list.add(sv);
        }
    }
    public void fillTable() {
        String[] tb={"Mã SV","Họ tên","Email","SĐT","Giới tính","Địa chỉ"};
        model=new DefaultTableModel(tb,0);
        for(int i=0;i<list.size();i++){
            String ma = list.get(i).masv;
            String ten = list.get(i).tensv;
            String email = list.get(i).email;
            String sdt = list.get(i).phone;
            boolean gt = list.get(i).gioitinh;
            String diachi = list.get(i).diachi;
            Object mo[]={ma,ten,email,sdt,gt,diachi};
            model.addRow(mo);
        }
        tbHienthi.setModel(model);
    }
    public void showIndex( int index){
        txtMaSV.setText(list.get(index).getMasv());
        txtHovaten.setText(list.get(index).getTensv());
        txtEmail.setText(list.get(index).getEmail());
        txtSĐT.setText(list.get(index).getPhone());
        if(list.get(index).isGioitinh()==true){
            rdoNam.setSelected(true);
        }
        else{
            rdoNu.setSelected(true);
        }
        txtDiachi.setText(list.get(index).getDiachi());
        String pathh = list.get(index).getAnh();
        File file = new File(pathh);
        BufferedImage img;
        try{
            img=ImageIO.read(file);
            lblAnh.setIcon(new ImageIcon(img.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), img.SCALE_SMOOTH)));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void loadDbToTable() {
        try {
            model.setRowCount(0);
            String sql="SELECT * FROM STUDENTS";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("MASV"));
                row.add(rs.getString("HOTEN"));
                row.add(rs.getString("EMAIL"));
                row.add(rs.getString("SODT"));
                row.add(rs.getBoolean("GIOITINH"));
                row.add(rs.getString("DIACHI"));
                row.add(rs.getString(7));
                model.addRow(row);
            }
            tbHienthi.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void upDate(){
        try{
            String sql= "UPDATE STUDENTS SET HOTEN=?, EMAIL = ?, SODT = ?, GIOITINH =?, DIACHI=?, HINH =? WHERE MASV=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtHovaten.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtSĐT.getText());
            boolean gt;
            if(rdoNam.isSelected()){
                gt=true;
            }
            else{
                gt=false;
            }
            ps.setBoolean(4, gt);
            ps.setString(5, txtDiachi.getText());
            ps.setString(6, path);
            ps.setString(7, txtMaSV.getText());
            ps.executeUpdate();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtHovaten = new javax.swing.JTextField();
        txtMaSV = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSĐT = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHienthi = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiachi = new javax.swing.JEditorPane();
        btnChonAnh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ SINH VIÊN");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã SV");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Họ và tên");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Địa chỉ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Số ĐT");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Giới tính");

        txtHovaten.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtMaSV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtSĐT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSĐT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSĐTActionPerformed(evt);
            }
        });

        rdoNam.setText("Nam");

        rdoNu.setText("Nữ");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
        );

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/new.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/update.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        tbHienthi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        tbHienthi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHienthiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHienthi);

        jScrollPane2.setViewportView(txtDiachi);

        btnChonAnh.setText("Chọn ảnh");
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSĐT, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(29, 29, 29)
                                .addComponent(rdoNu))
                            .addComponent(txtHovaten, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(txtMaSV, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnChonAnh)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(61, 61, 61))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                    .addComponent(txtSĐT))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdoNam)
                                    .addComponent(rdoNu)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)))
                .addGap(17, 17, 17)
                .addComponent(btnChonAnh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSĐTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSĐTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSĐTActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        txtMaSV.setText(null);
        txtHovaten.setText(null);
        txtEmail.setText(null);
        txtSĐT.setText(null);
        txtDiachi.setText(null);
        buttonGroup1.clearSelection();
        lblAnh.setIcon(null);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        save();
        loadDbToTable();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tbHienthiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHienthiMouseClicked
        // TODO add your handling code here:
        index=tbHienthi.getSelectedRow();
        this.showIndex(index);
    }//GEN-LAST:event_tbHienthiMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        index=tbHienthi.getSelectedRow();
        if(index== -1){
            JOptionPane.showMessageDialog(this, "Cần phải chọn sinh viên để cập nhật");
        }
        else{
            upDate();
            index = tbHienthi.getSelectedRow();
            student sv = list.get(index);
            sv.masv=txtMaSV.getText();
            sv.tensv=txtHovaten.getText();
            sv.email=txtEmail.getText();
            sv.phone=txtSĐT.getText();
            boolean gt;
            if(rdoNam.isSelected()){
            gt=true;
            }
            else{
            gt=false;
            }
            sv.gioitinh=gt;
            sv.diachi=txtDiachi.getText();
            sv.anh=path;
      }
        loadDbToTable();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        index=tbHienthi.getSelectedRow();
        if(index== -1){
            JOptionPane.showMessageDialog(this, "Cần phải chọn sinh viên để xóa");
        }
        else{
            try {
                String sql = "DELETE FROM STUDENTS WHERE MASV = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtMaSV.getText());
                ps.execute();
                
                list.remove(index);
            }
            catch (SQLException ex) {
                Logger.getLogger(QuanLySV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadDbToTable();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhActionPerformed
        // TODO add your handling code here:
        JFileChooser jf = new JFileChooser();
//        jf.setFileSelectionMode(jf.FILES_ONLY);
        int result = jf.showOpenDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            File file = jf.getSelectedFile();
            path=file.getAbsolutePath();
            BufferedImage img;
            try{
                img = ImageIO.read(file);
                lblAnh.setIcon(new ImageIcon(img.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), img.SCALE_SMOOTH)));
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_btnChonAnhActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLySV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tbHienthi;
    private javax.swing.JEditorPane txtDiachi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHovaten;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtSĐT;
    // End of variables declaration//GEN-END:variables
}
