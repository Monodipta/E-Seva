package com.example.projectproto1.admin_catalouge.AdminFragment.adrcv;

public class admodel {

    String complaint;
    String date;
    String department;
    String fullname;
    String phoneno;
    String refid;
    String status;
    String taxno;
    String title;

    public admodel() {
    }

    public admodel(String complaint, String date, String department, String fullname, String phoneno, String refid, String status, String taxno, String title) {
        this.complaint = complaint;
        this.date = date;
        this.department = department;
        this.fullname = fullname;
        this.phoneno = phoneno;
        this.refid = refid;
        this.status = status;
        this.taxno = taxno;
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid;
    }

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
