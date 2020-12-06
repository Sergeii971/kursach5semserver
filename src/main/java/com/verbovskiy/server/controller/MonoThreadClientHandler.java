package com.verbovskiy.server.controller;

import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.CommandProvider;
import com.verbovskiy.server.controller.command.RequestParameter;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class MonoThreadClientHandler implements Runnable {
    private final Logger logger = LogManager.getLogger(MonoThreadClientHandler.class);
    private static Socket clientDialog;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public MonoThreadClientHandler(Socket client, int numberConnect) throws IOException {
        MonoThreadClientHandler.clientDialog = client;
        out = new ObjectOutputStream(clientDialog.getOutputStream());
        in = new ObjectInputStream(clientDialog.getInputStream());
        logger.log(Level.INFO,"подключений за все время работы сервера: " + numberConnect);
    }

    @Override
    public void run() {
        try {
            while (!clientDialog.isClosed()) {
                Map<String, Object> request = (Map<String, Object>) in.readObject();
                String commandName = (String) request.get(RequestParameter.COMMAND_NAME);
                if (commandName == null || commandName.equals(RequestParameter.EXIT_COMMAND)) {
                    break;
                } else {
                    ActionCommand command = CommandProvider.defineCommand(commandName);
                    Map<String, Object> response = command.execute(request);
                    out.flush();
                    out.writeObject(response);
                }
            }
            logger.log(Level.INFO, "Connection disconnected.\n");
            clientDialog.close();
        } catch (IOException e) {
            logger.log(Level.ERROR, "error while close stream");
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, "error while getting request");
        }
    }
}
