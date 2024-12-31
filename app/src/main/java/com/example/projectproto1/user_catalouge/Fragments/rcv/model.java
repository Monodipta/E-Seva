package com.example.projectproto1.user_catalouge.Fragments.rcv;

public class model {

    String complaint,date,refid,taxno,title;

    public model() {
    }

    public model(String complaint, String date, String refid, String taxno, String title) {
        this.complaint = complaint;
        this.date=date;
        this.refid = refid;
        this.taxno = taxno;
        this.title = title;
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
