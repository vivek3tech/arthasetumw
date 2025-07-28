package com.fincodefusion.arthasetu.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fincodefusion.arthasetu.services.GlobalState;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class BankAccountTools {

    @Autowired
    GlobalState globalState;

    WebClient webClient = WebClient.create("https://arthasetunode-282482783617.asia-south1.run.app/");

    /**
     * Retrieves the current account balance by making a GET request to the "/api/balance" endpoint.
     * Sets the global action to "voice" before making the request.
     * @return The account balance as a String.
     */
    @Tool(name="getCurrentAccountBalance", description = "Get the current account balance, what is my account balance?")
    String getCurrentAccountBalance() {
        System.out.println("getCurrentAccountBalance called");
        globalState.setGlobalAction("voice");

        String response = webClient.get()
                .uri("/api/balance")
                .retrieve()
                .bodyToMono(String.class).block();

        // response = "Account balance for" +user+ " is 10000";

        System.out.println("getCurrentAccountBalance"+ response);

        return response;
    }

    /**
     * Transfers the specified amount (in INR) to the given recipient's account.
     * Updates the global state with transfer details.
     * @param amount Amount to transfer in INR.
     * @param recipient Recipient name.
     * @return Confirmation message.
     */
    @Tool(name="transferMoney", description = "Transfers the specified amount (in INR) to the given recipient's account. Use this when the user wants to send or transfer money to someone.")
    public String transferMoney(
            @Parameter(name = "amount", description = "Amount to transfer in INR")
            int amount,

            @Parameter(name = "recipient", description = "Recipient name")
            String recipient
    ) {
        System.out.println("Transferred ₹" + amount + " to" + recipient);
       globalState.setGlobalAction("transfer");
       globalState.setAmount(Integer.valueOf(amount).toString());
       globalState.setContact(recipient);
       System.out.println("Transferred ₹" + globalState.getAmount() + " to " + globalState.getContact());
        return "Transferred";
    }

    /**
     * Gets the current date and time in the user's timezone.
     * @return Current date and time as a String.
     */
    @Tool(name="getCurrentDateTime", description = "Get the current date and time in the user's timezone")
    String getCurrentDateTime() {
        System.out.println("getCurrentDateTime called");
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

    /**
     * Retrieves all transactions for the user by making a GET request to the "/api/transactions" endpoint.
     * Formats the transaction details for output.
     * @return Formatted transaction details as a String.
     * @throws JsonProcessingException if the response cannot be parsed.
     */
    @Tool(name="getTransactions", description = "Get all the transactions for the user")
    String getTransactions() throws JsonProcessingException {
        System.out.println("getTransactions called");
        globalState.setGlobalAction("voice");


        String response = webClient.get()
                .uri("/api/transactions")
                .retrieve()
                .bodyToMono(String.class).block();

        // response = "Account balance for" +user+ " is 10000";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode arrayNode = mapper.readTree(response);

        StringBuilder result = new StringBuilder();

        for (JsonNode transaction : arrayNode) {
            String name = transaction.get("name").asText();
            int amount = transaction.get("amount").asInt();
            result.append(name)
                    .append(" received ₹")
                    .append(amount)
                    .append(". ");
        }

        String formattedOutput = result.toString().trim();
        System.out.println(formattedOutput);
        System.out.println("getTransactions"+ result);

        return formattedOutput;
    }


}
