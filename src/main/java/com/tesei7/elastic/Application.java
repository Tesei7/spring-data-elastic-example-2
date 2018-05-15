package com.tesei7.elastic;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

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

    @Primary
    @Bean
    public ElasticsearchOperations elasticTemplate() throws IOException {
        Path tmpDir = Files.createTempDirectory("elastic");
        Path data = tmpDir.resolve("data");
        Path logs = tmpDir.resolve("logs");
        Path work = tmpDir.resolve("work");
        Stream.of(data, logs, work).forEach(this::createDir);
        log.info(String.format("Temp directory: %s", tmpDir.toAbsolutePath()));
        Settings.Builder settings = Settings.builder()
                .put("http.enabled", "true")
                .put("path.data", data.toAbsolutePath())
                .put("path.logs", logs.toAbsolutePath())
                .put("path.work", work.toAbsolutePath())
                .put("path.home", tmpDir.toAbsolutePath());
        return new ElasticsearchTemplate(nodeBuilder().local(true).settings(settings.build()).node().client());
    }

    @SneakyThrows
    private void createDir(Path path){
        Files.createDirectory(path);
    }
}
