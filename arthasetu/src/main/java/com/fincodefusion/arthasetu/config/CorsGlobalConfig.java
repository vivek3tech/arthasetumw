package com.fincodefusion.arthasetu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsGlobalConfig implements WebMvcConfigurer  {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
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


