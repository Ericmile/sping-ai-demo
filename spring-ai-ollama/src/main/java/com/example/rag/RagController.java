package com.example.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ollama.model.ChatRequest;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rag")
public class RagController {

    private final ChatClient chatClient;

    public RagController(@Qualifier("vectorSearchClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * 非流式接口
     * 
     * @param request
     * @return
     */
    @PostMapping
    public ChatResponse rag(@RequestBody ChatRequest request) {
        return this.chatClient.prompt()
                .user(request.getPrompt())
                .call()
                .chatResponse();
    }

    /**
     * 流式接口
     * 
     * @param request
     * @return
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> ragFlux(@RequestBody ChatRequest request) {
        return this.chatClient.prompt()
                .user(request.getPrompt())
                .stream()
                .chatResponse();
    }

}
