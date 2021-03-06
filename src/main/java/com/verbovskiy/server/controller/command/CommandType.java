package com.verbovskiy.server.controller.command;

import com.verbovskiy.server.controller.command.impl.*;

/**
 * The enum Command type.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public enum CommandType {
    AUTHENTICATION(new AuthenticationCommand()),
    REGISTRATION(new RegistrationCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    CHANGE_USER_BLOCK_STATUS(new ChangeUserBlockStatusCommand()),
    SEARCH_USERS(new SearchUsersCommand()),
    FILTER_USERS(new FilterUsersCommand()),
    SORT_USERS(new SortUsersCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    ADD_CAR(new AddCarCommand()),
    SHOW_CARS(new ShowCarsCommand()),
    CHANGE_CAR_AVAILABLE_STATUS(new ChangeCarAvailableStatusCommand()),
    DELETE_CAR(new DeleteCarCommand()),
    FIND_CARS(new FindCarsCommand()),
    BUY_CAR(new BuyCarCommand()),
    SHOW_ORDERS(new ShowOrdersCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    FIND_ORDERS(new FindOrdersCommand()),
    CHANGE_ORDER_STATUS(new ChangeOrderStatusCommand()),
    WEEK_PROFIT(new WeekProfitCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public ActionCommand getCommand() {
        return command;
    }
}
