package com.verbovskiy.server.model.comparator;

import com.verbovskiy.server.model.entity.User;

import java.util.Comparator;

/**
 * The enum User comparator.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public enum UserComparator {
    SURNAME((User user1, User user2) -> user1.getSurname().compareTo(user2.getSurname())),
    EMAIL((User user1, User user2) -> user1.getEmail().compareTo(user2.getEmail()));

    private Comparator<User> comparator;

    UserComparator(Comparator<User> comparator) {
        this.comparator = comparator;
    }

    /**
     * Gets comparator.
     *
     * @return the comparator
     */
    public Comparator<User> getComparator() {
        return comparator;
    }
}
