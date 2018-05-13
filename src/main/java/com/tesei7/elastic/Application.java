package com.tesei7.elastic;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableElasticsearchRepositories
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws IOException {
        File tmpDir = File.createTempFile("elastic", Long.toString(System.currentTimeMillis()));
        log.info(String.format("Temp directory: %s", tmpDir.getAbsolutePath()));
        Settings.Builder settings = Settings.settingsBuilder()
                .put("http.enabled", "true")
                .put("path.home", tmpDir);
        return new ElasticsearchTemplate(nodeBuilder().local(true).settings(settings.build()).node().client());
    }
}
