/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haitdph08749_assignment_sop203;

import java.io.Serializable;

/**
 *
 * @author LaptopAZ
 */
public class student implements Serializable{
    public String masv;
    public String tensv;
    public String email;
    public String phone;
    public String diachi;
    public String anh;
    public boolean gioitinh;

    public student(String masv, String tensv, String email, String phone, String diachi, String anh, boolean gioitinh) {
        this.masv = masv;
        this.tensv = tensv;
        this.email = email;
        this.phone = phone;
        this.diachi = diachi;
        this.anh = anh;
        this.gioitinh = gioitinh;
    }

    public student() {
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }
}
