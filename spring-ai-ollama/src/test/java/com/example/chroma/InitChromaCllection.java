package com.example.chroma;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.BaseTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitChromaCllection extends BaseTest {

    @Autowired
    private VectorStore vectorStore;

    /**
     * 初始化文档
     */
    @Test
    public void initDocument() {
        Document javaDoc1 = new Document(
                "在谈 Java 面向对象的时候，不得不提到面向对象的三大特征：封装、继承、多态。三大特征紧密联系而又有区别，合理使用继承能大大减少重复代码，提高代码复用性。");
        Document javaDoc2 = new Document("SpringAI 是一个强大的工具，可以帮助 Java 应用程序开发人员以与平台无关的方式使用 LLM");
        List<Document> documents = List.of(javaDoc1, javaDoc2);

        vectorStore.add(documents);
    }

    /**
     * 查询文档
     */
    @Test
    public void query() {
        List<Document> results = this.vectorStore
                .similaritySearch(
                        SearchRequest.builder().query("Java语言的特性是什么").topK(5).similarityThreshold(0.6).build());
        for (Document doc : results) {
            log.info("Document Text: {}", doc.getText());
        }
    }

}
