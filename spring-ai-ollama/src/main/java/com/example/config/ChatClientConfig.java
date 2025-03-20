package com.example.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    private QuestionAnswerAdvisor questionAnswerAdvisor;

    private SimpleLoggerAdvisor simpleLoggerAdvisor;

    private VectorStore vectorStore;

    public ChatClientConfig(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        // 初始化检索器 设置相似度阈值 0.6、返回5条、和提示词格式
        this.questionAnswerAdvisor = new QuestionAnswerAdvisor(this.vectorStore,
                SearchRequest.builder().similarityThreshold(0.6d).topK(5).build(), USER_TEXT_ADVISE);

        // 初始化日志器
        this.simpleLoggerAdvisor = new SimpleLoggerAdvisor();
    }

    /**
     * 用于向量检索的Client
     *
     * @param builder
     * @return
     */
    @Bean("vectorSearchClient")
    ChatClient searchClient(ChatClient.Builder builder) {
        // 定义Client的默认配置
        return builder.defaultSystem(SYSTEM_PROMPT_SEARCH)
                .defaultOptions(
                        ChatOptions.builder().model("yasserrmd/Qwen2.5-7B-Instruct-1M").maxTokens(128000).build())
                .defaultAdvisors(
                        questionAnswerAdvisor,
                        simpleLoggerAdvisor)
                .build();
    }

     /**
     * 系统提示词
     */
    private static final String SYSTEM_PROMPT_SEARCH = """
            你是一名专业的信息整理助手，够准确理解用户的提问，根据上下文进行作答。注意事项：
            1. 你的回答必须基于提供的检索内容，不要编造内容。
            2. 回答中不包含：根据提供的信息、根据上下文等词语。
            """;

    /**
     * 增强生成的提示词
     * 这个实例中是给 Qwen2.5-7B 的
     */
    private static final String USER_TEXT_ADVISE = """

            根据上下文提供的信息，用专业的词汇，总结回答用户的问题，上下文如下：

            ---------------------
            {question_answer_context}
            ---------------------
            """;
}
