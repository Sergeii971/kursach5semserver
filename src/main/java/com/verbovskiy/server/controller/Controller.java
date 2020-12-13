package com.verbovskiy.server.controller;

import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.service.UserService;
import com.verbovskiy.server.model.service.impl.UserServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger(Controller.class);
        ExecutorService executeIt = Executors.newCachedThreadPool();
        int i = 0;
        try (ServerSocket server = new ServerSocket(Constant.PORT);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            logger.log(Level.INFO, "Server socket created, port " + Constant.PORT +
                    ", command console reader for listen to server commands");
            while (!server.isClosed()) {
                if (br.ready()) {
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        logger.log(Level.INFO, "Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }
                try {
                    Socket client = server.accept();
                    i++;
                    executeIt.execute(new MonoThreadClientHandler(client, i));
                    logger.log(Level.INFO, "Connection accepted.\n");
                } catch (IOException e) {
                    logger.log(Level.ERROR, "error while creating connection");
                }
            }
            executeIt.shutdown();
        } catch (IOException e) {
            logger.log(Level.ERROR, "error while working server");
        }
    }
}
