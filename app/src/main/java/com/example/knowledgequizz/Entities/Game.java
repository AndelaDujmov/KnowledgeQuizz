package com.example.knowledgequizz.Entities;

import java.util.Date;

public class Game {
    public int totalPoints;
    public int achievedPoints;
    public String playedAt;
    public String currentUser;

    public Game(){

    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getAchievedPoints() {
        return achievedPoints;
    }

    public String getPlayedAt() {
        return playedAt;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
