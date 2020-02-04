package ua.internteam.dreamteamgame.api.entity;

import java.io.Serializable;

public class Team implements Serializable {

    private String id;
    private String name;

    public Team(String id) {
        this.id = id;
        this.name = "";
    }

    public void setToken(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Team{" +
                "token='" +  + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
