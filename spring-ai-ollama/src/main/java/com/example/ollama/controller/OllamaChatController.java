package com.example.ollama.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ollama.model.ChatRequest;

import reactor.core.publisher.Flux;

/**
 * Ollama示例
 */
@RestController
@RequestMapping("/api/chat")
public class OllamaChatController {

    private final ChatClient chatClient;

    public OllamaChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * 聊天
     * @param request
     * @return
     */
    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
       return  chatClient.prompt().advisors(new SimpleLoggerAdvisor()).user(request.getPrompt()).call()
        .chatResponse();
    }

    /**
     * 聊天（流式）
     * @param request
     * @return
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> chatStream(@RequestBody ChatRequest request) {
        return chatClient.prompt().advisors(new SimpleLoggerAdvisor()).user(request.getPrompt()).stream().chatResponse();
    }
    
}