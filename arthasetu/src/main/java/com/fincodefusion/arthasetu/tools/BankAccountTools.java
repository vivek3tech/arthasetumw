package com.fincodefusion.arthasetu.tools;

import com.fincodefusion.arthasetu.services.GlobalState;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class BankAccountTools {

    GlobalState globalState = new GlobalState();


    @Tool(name="getCurrentAccountBalance", description = "Get the current account balance, what is my account balance?")
    String getCurrentAccountBalance() {
        System.out.println("getCurrentAccountBalance called");
        globalState.setAction("voice");
        WebClient webClient = WebClient.create("https://arthasetunode-282482783617.asia-south1.run.app/");

        String response = webClient.get()
                .uri("/api/balance")
                .retrieve()
                .bodyToMono(String.class).block();

        // response = "Account balance for" +user+ " is 10000";

        System.out.println("getCurrentAccountBalance"+ response);

        return response;
    }

    @Tool(name="transferMoney", description = "Transfers the specified amount (in INR) to the given recipient's account. Use this when the user wants to send or transfer money to someone.")
    public String transferMoney(
            @Parameter(name = "amount", description = "Amount to transfer in INR")
            int amount,

            @Parameter(name = "recipient", description = "Recipient name")
            String recipient
    ) {
        System.out.println("Transferred ₹" + amount + " to" + recipient);
       globalState.setAction("transfer");
       globalState.setAmount(Integer.valueOf(amount).toString());
       globalState.setContact(recipient);
       System.out.println("Transferred ₹" + globalState.getAmount() + " to " + globalState.getContact());
        return "Transferred";
    }

    @Tool(name="getCurrentDateTime", description = "Get the current date and time in the user's timezone")
    String getCurrentDateTime() {
        System.out.println("getCurrentDateTime called");
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }


}
