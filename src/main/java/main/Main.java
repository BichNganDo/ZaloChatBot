package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.Webhook;
import servlets.api.ApiKeyWordServlet;
import servlets.key_word.AddKeyWordServlet;
import servlets.key_word.EditKeyWordServlet;
import servlets.key_word.ManageKeyWordServlet;

public class Main {

    public static void main(String[] args) throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ManageKeyWordServlet()), "/admin/keyword");
        context.addServlet(new ServletHolder(new AddKeyWordServlet()), "/admin/keyword/add");
        context.addServlet(new ServletHolder(new EditKeyWordServlet()), "/admin/keyword/edit");
        context.addServlet(new ServletHolder(new Webhook()), "/");

        context.addServlet(new ServletHolder(new ApiKeyWordServlet()), "/admin/api/keyword");

        ContextHandler resourceHandler = new ContextHandler("/static");
        String resource = "./public";
        if (!resource.isEmpty()) {
            resourceHandler.setResourceBase(resource);
            resourceHandler.setHandler(new ResourceHandler());
        }

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(8080);

        server.setHandler(handlers);

        server.start();

        System.out.println("Server started");

        server.join();
    }
}
