package com.fincodefusion.arthasetu.services;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class GlobalState {

    private static String globalAction = "voice";
    private static String amount;
    private static String contact;

    public String getAction() {
        return globalAction;
    }

    public void setAction(String globalMessage) {
        this.globalAction = globalMessage;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
