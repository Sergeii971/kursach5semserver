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
//        UserService service = new UserServiceImpl();
//        String login = "epam.online.store@gmail.com";
//        String password = "571D3BEB574F2D1905196D55E8570D8C03B4CCC20BB65BC641328B06FD8840E7BD8E83354B5E1D1D0A78AE" +
//                "AD9CECE7F1B9B716E5687C357F7A46C694CB5B4668";
//        boolean isAdmin = true;
//        boolean isBlocked = false;
//        boolean isConfirmed = true;
//        String name = "Sergei";
//        String surname = "Verbovskiy";
//        try {
//            service.add(login, "S12345678s&", isAdmin, isBlocked,login,  name, surname);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
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
