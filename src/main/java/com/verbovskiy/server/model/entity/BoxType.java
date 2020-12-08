package com.verbovskiy.server.model.entity;

/**
 * The enum Box type.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public enum BoxType implements Entity {
    MECHANICS("mechanics"),
    AUTOMATION("automation");

    private final String box;

    BoxType(String box) {
        this.box = box;
    }

    /**
     * Gets Box.
     *
     * @return box
     */
    public String getBox() {
        return box;
    }
}
