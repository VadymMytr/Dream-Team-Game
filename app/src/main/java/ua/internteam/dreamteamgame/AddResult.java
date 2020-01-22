package ua.internteam.dreamteamgame;

import com.google.gson.annotations.SerializedName;

public class AddResult {

        @SerializedName("number")
        public Integer number;
        @SerializedName("text")
        public String text;
        @SerializedName("Team")
        public Team team;
}
