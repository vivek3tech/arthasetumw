package com.fincodefusion.arthasetu.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIWebClientConfig  {

 /*   @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.7)
                .build();

        return chatClientBuilder.defaultOptions(chatOptions).defaultSystem(systemPrompt).build();
    }
*/
   /* @Bean
    public ToolCallbackProvider toolCallbackProvider(DateService dateService) {
        // This method can be used to register tools if needed
        return MethodToolCallbackProvider.builder().toolObjects(dateService).build();
    }
*/


}


