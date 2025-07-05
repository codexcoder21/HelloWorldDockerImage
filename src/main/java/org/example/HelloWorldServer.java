package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloWorldServer {

    private Server server;

    public void start(int port) throws Exception {
        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new HelloServlet()), "/*");

        server.start();
    }

    public int getPort() {
        return ((ServerConnector) server.getConnectors()[0]).getLocalPort();
    }

    public void stop() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

    public boolean isRunning() {
        return server != null && server.isRunning();
    }

    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello World written by Gemini");
        }
    }

    public static void main(String[] args) throws Exception {
        String portStr = System.getenv("PORT");
        int port = portStr != null ? Integer.parseInt(portStr) : 8080;

        HelloWorldServer helloWorldServer = new HelloWorldServer();
        helloWorldServer.start(port);

        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("http://localhost:" + port + "/"));
        } catch (Exception e) {
            System.out.println("Could not open browser: " + e.getMessage());
        }
    }
}