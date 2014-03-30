package denary.app.models;

import java.math.BigDecimal;

/**
 * Created by gtkesh on 3/30/14.
 */
public class Transaction {
    private String name;
    private String tag;
    private BigDecimal amount;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Transaction(String name, String tag, BigDecimal amount, String type){
        this.name = name;
        this.tag = tag;
        this.amount = amount;
        this.type = type;

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
