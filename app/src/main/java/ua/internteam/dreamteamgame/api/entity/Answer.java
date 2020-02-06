package ua.internteam.dreamteamgame.api.entity;

public class Answer {
    private String teamId;
    private String text;

    public Answer(String text, String teamId) {
        this.text = text;
        this.teamId = teamId;
    }

    public String getId() {
        return teamId;
    }

    public void setId(String teamId) {
        this.teamId = teamId;
    }
    }
