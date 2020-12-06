package com.verbovskiy.server.model.entity;

/**
 * The type User.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class User implements Entity {
    private String email;
    private String name;
    private String surname;
    private Account account;

    public User(Account account, String email, String name, String surname) {
        this.account = account;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * set surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * set account.
     *
     * @param account the account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        User user = (User) o;

        if (email == null) {
            if (user.email != null) {
                return false;
            }
        } else {
            if (!email.equals(user.email)) {
                return false;
            }
        }
        if (name == null) {
            if (user.name != null) {
                return false;
            }
        } else {
            if (!name.equals(user.name)) {
                return false;
            }
        }
        if (surname == null) {
            if (user.surname != null) {
                return false;
            }
        } else {
            if (!surname.equals(user.surname)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        result += 31 * result + (account == null ? 0 : account.hashCode());
        result += 31 * result + (email == null ? 0 : email.hashCode());
        result += 31 * result + (name == null ? 0 : name.hashCode());
        result += 31 * result + (surname == null ? 0 : surname.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(account.toString());
        builder.append(" ");
        builder.append(email);
        builder.append(" ");
        builder.append(name);
        builder.append(" ");
        builder.append(surname);
        return builder.toString();
    }
}