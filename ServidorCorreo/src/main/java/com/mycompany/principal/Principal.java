/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.principal;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author josedanielcardenasrincon
 */
public class Principal {
    private static final Logger LOG = Logger.getLogger(Principal.class.getName());

    public static void main(String agrs[]) {
        try {
            String webappDirLocation = "src/main/webapp/";
            String webPort = System.getenv("PORT");
            if (webPort == null || webPort.isEmpty()) {
                webPort = "8080";
            }
            Server server = new Server(Integer.valueOf(webPort));
            WebAppContext root = new WebAppContext();
            root.setContextPath("/");
            root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
            root.setResourceBase(webappDirLocation);
            root.setParentLoaderPriority(true);
            server.setHandler(root);
            server.start();
            server.join();
        } catch (InterruptedException ex) {
            LOG.log(Level.WARNING, ex.getMessage());
        } catch (Exception ex) {
            LOG.log(Level.WARNING, ex.getMessage());
        }

    }
}
