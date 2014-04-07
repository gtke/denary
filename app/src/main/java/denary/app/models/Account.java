package denary.app.models;

import java.math.BigDecimal;

/**
 * Created by gtkesh on 3/30/14.
 */
public class Account {
    private String name;
    private String balance;
    private String bank_name;
    private String tag;
    private String cardNumber;

    public Account(String name, String bank_name, String tag, String balance, String cardNumber){
        this.name = name;
        this.bank_name = bank_name;
        this.tag = tag;
        this.balance = balance;
        this.cardNumber = cardNumber;
    }
    public Account(String name){
        this.name = name;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBank_name() {
        return bank_name;

    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String toString(){
        return name + ": " + balance;
    }

}
