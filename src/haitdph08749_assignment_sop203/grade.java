/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haitdph08749_assignment_sop203;

import java.io.Serializable;

/**
 *
 * @author Tong Duy Hai
 */
public class grade implements Serializable{
    public String masv, name;
    public double anh, tin, gdtc, avg;
    public int id;

    grade() {
       
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAnh() {
        return anh;
    }

    public void setAnh(double anh) {
        this.anh = anh;
    }

    public double getTin() {
        return tin;
    }

    public void setTin(double tin) {
        this.tin = tin;
    }

    public double getGdtc() {
        return gdtc;
    }

    public void setGdtc(double gdtc) {
        this.gdtc = gdtc;
    }

    public double getAvg() {
        avg = (anh + tin + gdtc) / 3 ;
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public grade(String masv, String name, double anh, double tin, double gdtc, double avg, int id) {
        this.masv = masv;
        this.name = name;
        this.anh = anh;
        this.tin = tin;
        this.gdtc = gdtc;
        this.avg = avg;
        this.id = id;
    }
}
