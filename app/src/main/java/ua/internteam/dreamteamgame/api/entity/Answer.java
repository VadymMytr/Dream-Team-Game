package ua.internteam.dreamteamgame.api.entity;

public class Answer {
    private int id;
    private int number;
    private String text;

    public Answer(int number, String text) {
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

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
