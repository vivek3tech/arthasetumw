package com.fincodefusion.arthasetu.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIWebClientConfig  {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.7)
                .build();
        String systemPrompt = """
        please answer in short, simple and crisp statements
        For Greetings say Hi There, how can I assist you in hindi language
        You are a helpful assistant. make use of Bank Account tools  if asked about transfer money date
        always reply in user prompt's language, use INR for amount balance and transfer
        please answer only finance and investment related prompts for any unusual and abusive questions a reply please ask finance related questions""";

        return chatClientBuilder.defaultOptions(chatOptions).defaultSystem(systemPrompt).build();
    }

   /* @Bean
    public ToolCallbackProvider toolCallbackProvider(DateService dateService) {
        // This method can be used to register tools if needed
        return MethodToolCallbackProvider.builder().toolObjects(dateService).build();
    }
*/


}


