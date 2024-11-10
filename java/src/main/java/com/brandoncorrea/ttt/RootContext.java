package com.brandoncorrea.ttt;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class RootContext extends ServletContextHandler {

    public RootContext(String contextPath) {
        super(ServletContextHandler.SESSIONS);

        setContextPath(contextPath);
        var holder = new ServletHolder("static", DefaultServlet.class);
        holder.setInitParameter("resourceBase", Main.class.getResource("/public").toExternalForm());
        holder.setInitParameter("dirAllowed", "true");
        addServlet(holder, contextPath);
    }

    public int getOptions() {
        return this._options;
    }
}
