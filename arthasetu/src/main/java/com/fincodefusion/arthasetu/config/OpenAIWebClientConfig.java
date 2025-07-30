package com.fincodefusion.arthasetu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OpenAIWebClientConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Disable CORS
                .cors(cors -> cors.disable())

                // Allow all requests (if required)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

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


