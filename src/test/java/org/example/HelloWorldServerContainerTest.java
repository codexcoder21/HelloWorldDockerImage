package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Testcontainers
public class HelloWorldServerContainerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldServerContainerTest.class);

    @Container
    public GenericContainer<?> app = new GenericContainer<>("hello-world-docker-image:latest")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/").forStatusCode(200).withStartupTimeout(java.time.Duration.ofSeconds(120)))
            .withLogConsumer(new Slf4jLogConsumer(LOGGER));

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testHelloWorld() throws IOException {
        Integer mappedPort = app.getMappedPort(8080);
        Request request = new Request.Builder()
                .url("http://localhost:" + mappedPort)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertEquals("Hello World written by Gemini\n", response.body().string());
        }
    }
}
