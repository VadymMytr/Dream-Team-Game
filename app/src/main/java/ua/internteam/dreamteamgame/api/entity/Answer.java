package ua.internteam.dreamteamgame.api.entity;

public class Answer {
    private int id;
    private Team team;
    private String text;

    public Answer(Team team, String text){
        this.team = team;
        this.text = text;
    }

    public Answer(int id, Team team, String text) {
        this(team, text);
        this.id = id;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
