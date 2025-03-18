package com.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OllamaApplication {

    
    public static void main(String[] args) {
        SpringApplication.run(OllamaApplication.class,  args);
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("你是一个AI助手，可以回答任何问题。")
                .defaultOptions(ChatOptions.builder().maxTokens(128000).build()).build();
    }
}
