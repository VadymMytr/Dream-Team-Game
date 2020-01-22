package ua.internteam.dreamteamgame.api.entity;

public class Answer {
    private int id;
    private int number;
    private Team team;
    private String text;

    public Answer(int id, int number, Team team, String text) {
        this.id = id;
        this.team = team;
        this.number = number;
        this.text = text;
    }

    public Answer( int number, Team team, String text) {
        this.team = team;
        this.number = number;
        this.text = text;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
