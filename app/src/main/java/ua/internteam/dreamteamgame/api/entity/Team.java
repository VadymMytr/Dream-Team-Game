package ua.internteam.dreamteamgame.api.entity;

import java.io.Serializable;
import java.time.temporal.Temporal;

public class Team implements Serializable {

    private String token;
    private String name;

    public Team() {
        token = "";
        name = "";
    }

    public Team(String token) {
        this();
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

}
