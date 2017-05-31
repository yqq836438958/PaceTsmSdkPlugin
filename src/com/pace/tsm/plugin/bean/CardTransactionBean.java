
package com.pace.tsm.plugin.bean;

public class CardTransactionBean {
    private String transaction_amount;
    private String transaction_time;
    private String transaction_type;
    private String transaction_status;

    public void setTransaction_amount(String amount) {
        transaction_amount = amount;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_time(String time) {
        transaction_time = time;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_type(String type) {
        transaction_type = type;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_status(String status) {
        transaction_status = status;
    }

    public String getTransaction_status() {
        return transaction_status;
    }
}
