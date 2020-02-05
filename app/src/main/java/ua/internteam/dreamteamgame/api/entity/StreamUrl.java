package ua.internteam.dreamteamgame.api.entity;

public class StreamUrl {
    private String url;

    public StreamUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public StreamUrl setUrl(String url) {
        this.url = url;
        return this;
    }
}
