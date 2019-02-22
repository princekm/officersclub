package com.example.officersclub.db;

public class Bill {
    private Integer billNo;
    private Double amount;
    private String date;
    private String time;
    Bill()
    {
        this.billNo=0;
        this.amount=0.0;
        this.date="";
        this.time="";
    }
    Bill(int billNo,Double amount,String date,String time){
        this.billNo=billNo;
        this.amount=amount;
        this.date=date;
        this.time=time;
    }

    public String getAmount() {
        return amount.toString();
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBillNo() {
        return billNo.toString();
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }


}
