package com.verbovskiy.server.controller.command;

import java.util.Map;

public interface ActionCommand {
    Map<String, Object> execute(Map<String, Object> request);
}
