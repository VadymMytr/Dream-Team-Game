package ua.internteam.dreamteamgame.api.entity;

public class Answer {
    private int id;
    private String teamToken;
    private String text;

    public Answer(String teamToken, String text){
        this.text = text;
    }

    public Answer(int id, String teamToken, String text) {
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
}
