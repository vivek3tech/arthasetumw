package com.fincodefusion.arthasetu.controller;


import com.fincodefusion.arthasetu.service.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
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

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private OpenAiAudioSpeechModel openAiAudioSpeechModel;
    /**
     * This endpoint allows users to chat with the AI model.
     * It streams the response as a Server-Sent Event (SSE).
     *
     * @param prompt The user's input prompt for the AI model.
     * @return A Flux of String containing the AI's responses.
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<String> chat(@RequestParam("prompt") String prompt) {


        return ChatClient.create(chatModel).prompt(prompt).tools(new DateTimeTools()).stream().content();

    }

        @GetMapping(value = "/audio", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
        public byte[] audio(@RequestParam("prompt") String prompt) {
            String response = chatClient.prompt(prompt)
                    .call().content();
            return openAiAudioSpeechModel.call(response);
        }



}

