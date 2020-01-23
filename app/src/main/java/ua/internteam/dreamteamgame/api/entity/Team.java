package ua.internteam.dreamteamgame.api.entity;

import java.io.Serializable;

public class Team implements Serializable {

    private String token;
    private String name;

    public Team(String token, String name) {
       this.token = token;
       this.name = name;
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
