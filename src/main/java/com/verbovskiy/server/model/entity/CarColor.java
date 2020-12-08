package com.verbovskiy.server.model.entity;

/**
 * The enum Car color.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public enum CarColor implements Entity {
    BLACK("black"),
    RED("red"),
    WHITE("white"),
    ORANGE("orange");

    CarColor(String color) {
        this.color = color;
    }

    private final String color;

    /**
     * Gets Color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }
}
