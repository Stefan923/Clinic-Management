package com.sanitas.clinicapp.struct;

import java.util.Date;

public class Transaction implements Comparable<Transaction>{
    private int id;

    private String type;
    private String sender;
    private String receiver;

    private Date date;
    private float amount;

    public Transaction(int id, String type, Date date, float amount, String sender, String receiver) {
        this.id = id;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Date getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Transaction o) {
        return Integer.compare(0, date.compareTo(o.date));
    }
}
