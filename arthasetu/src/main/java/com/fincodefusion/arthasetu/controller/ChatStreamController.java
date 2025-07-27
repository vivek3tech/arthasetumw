package com.fincodefusion.arthasetu.controller;


import com.fincodefusion.arthasetu.entities.ResponseAI;
import com.fincodefusion.arthasetu.services.GlobalState;
import com.fincodefusion.arthasetu.tools.BankAccountTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;

import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static com.fincodefusion.arthasetu.util.Constants.SYSTEM_PROMPT;


@RestController
@RequestMapping("/api/v1")
public class ChatStreamController {

    /**
     * ChatStreamController is responsible for handling chat requests and streaming responses from the AI model.
     * It uses a ChatModel and ChatMemory to manage the conversation state.
     */
    private final ChatModel chatModel;
    private final ChatMemory chatMemory;

    @Autowired
    BankAccountTools bankAccountTool;

    @Autowired
    private GlobalState globalState;

    public ChatStreamController(ChatModel chatModel, ChatMemory chatMemory, GlobalState globalState) {
        this.chatModel = chatModel;
        this.globalState = globalState;
        this.chatMemory = chatMemory; ;
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
        String user = "user"; //
        chatMemory.add(user, new UserMessage(prompt));
        globalState.setGlobalAction("voice");

        String responseFromAI= ChatClient.create(chatModel).prompt(chatMemory.get("user").toString()).system(SYSTEM_PROMPT).tools(bankAccountTool).call().content();

        assert responseFromAI != null;
        chatMemory.add(user, new UserMessage(responseFromAI));

        if(globalState.getGlobalAction().equals("transfer")){
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


    /*@GetMapping(value = "/audio", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] audio(@RequestParam("prompt") String prompt) {
            String response = chatClient.prompt(prompt)
                    .call().content();
            return openAiAudioSpeechModel.call(response);
        }*/



}

