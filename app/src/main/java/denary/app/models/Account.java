package denary.app.models;

import java.math.BigDecimal;

/**
 * Created by gtkesh on 3/30/14.
 */
public class Account {
    private String name;
    private BigDecimal balance;

    public Account(String name, BigDecimal balance){
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String toString(){
        return name + ": " + balance;
    }

}
