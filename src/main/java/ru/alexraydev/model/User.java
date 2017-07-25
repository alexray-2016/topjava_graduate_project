package ru.alexraydev.model;

import java.util.List;

public class User {
    private int id;
    private String name;
    private List<UserVote> votes;
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<UserVote> getVotes() {
        return votes;
    }

    public void setVotes(List<UserVote> votes) {
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
