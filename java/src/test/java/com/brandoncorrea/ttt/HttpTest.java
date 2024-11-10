package com.brandoncorrea.ttt;

import com.brandoncorrea.ttt.server.memory.MemoryServer;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTest {

    @Nested
    class RootPath {

        MemoryServer server;

        @BeforeEach
        public void beforeEach() {
            server = new MemoryServer(8080);
            Http.wrapServer(server);
        }

        @Test
        public void contextHandlerSettings() {
            var handler = getContextHandler(server);
            assertEquals("/", handler.getContextPath());
            assertEquals(ServletContextHandler.SESSIONS, handler.getOptions());
        }

        @Test
        public void holderNamedStaticWithDefaultServlet() {
            var holder = getFirstServlet(server);
            assertEquals("static", holder.getName());
            assertEquals(DefaultServlet.class, holder.getHeldClass());
        }

        @Test
        public void holderCreatedWithParameters() {
            var parameters = getServletParameters(server);
            var resourceBase = parameters.get("resourceBase");
            var publicDirectory = Main.class.getResource("/public").toExternalForm();
            assertEquals(publicDirectory, resourceBase);
            assertEquals("true", parameters.get("dirAllowed"));
        }

        RootContext getContextHandler(MemoryServer server) {
            return (RootContext) server.getHandler();
        }

        ServletHandler getServletHandler(MemoryServer server) {
            return getContextHandler(server).getServletHandler();
        }

        ServletHolder getFirstServlet(MemoryServer server) {
            return getServletHandler(server).getServlets()[0];
        }

        Map<String, String> getServletParameters(MemoryServer server) {
            return getFirstServlet(server).getInitParameters();
        }
    }
}
