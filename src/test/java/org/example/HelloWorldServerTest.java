package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloWorldServerTest {

    private HelloWorldServer server;
    private OkHttpClient client = new OkHttpClient();

    @BeforeEach
    public void setUp() throws Exception {
        server = new HelloWorldServer();
        server.start(0); // Start on a random free port
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testServer() throws IOException {
        assertTrue(server.isRunning());

        Request request = new Request.Builder()
                .url("http://localhost:" + server.getPort() + "/")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertEquals("Hello World written by Gemini\n", response.body().string());
        }
    }
}