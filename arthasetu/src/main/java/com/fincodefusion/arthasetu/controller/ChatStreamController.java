package com.fincodefusion.arthasetu.controller;


import com.fincodefusion.arthasetu.entities.ResponseAI;
import com.fincodefusion.arthasetu.services.GlobalState;
import com.fincodefusion.arthasetu.tools.BankAccountTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;




@RestController
@RequestMapping("/api/v1")
public class ChatStreamController {

    private final ChatClient chatClient;
    private final ChatModel chatModel;
    private final ChatMemory chatMemory;

    @Autowired
    private GlobalState globalState;

    public ChatStreamController(ChatClient chatClient, ChatModel chatModel, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.chatModel = chatModel;
        this.chatMemory = MessageWindowChatMemory.builder ().build(); ;
    }

    /**
     * This endpoint allows users to chat with the AI model.
     * It streams the response as a Server-Sent Event (SSE).
     *
     * @param prompt The user's input prompt for the AI model.
     * @return A Flux of String containing the AI's responses.
     */
    @GetMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseAI chat(@RequestParam("prompt") String prompt) {
        System.out.println("Received prompt:for user:" +prompt);
        ResponseAI responseAI = new ResponseAI();
       // chatMemory.add(user, new UserMessage(prompt));

       // Flux<String> responseFromAI= ChatClient.create(chatModel).prompt(prompt).tools(new BankAccountTools(user)).stream().content();
        globalState.setAction("voice");
       String responseFromAI= ChatClient.create(chatModel).prompt(prompt).tools(new BankAccountTools()).call().content();

       // System.out.println(responseFromAI.toStream().toList().toString());
        //chatMemory.add(user, new UserMessage(responseFromAI.collectList().toString()));

        if(globalState.getAction().equals("transfer")){
            responseAI.setAction("transfer");
            responseAI.setAmount(globalState.getAmount());
            responseAI.setContact(globalState.getContact());
        }
        else {
            responseAI.setAction("voice");
            responseAI.setVoicedata(responseFromAI);
        }
        return responseAI;
    }

   /* @GetMapping(value = "/audio", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] audio(@RequestParam("prompt") String prompt) {
            String response = chatClient.prompt(prompt)
                    .call().content();
            return openAiAudioSpeechModel.call(response);
        }*/



}

