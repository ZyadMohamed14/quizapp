package com.hanynemr.g4dquizapp;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {

    private String name;
    private byte score;


    public Player(String name, byte score) {
        this.name = name;
        this.score = score;
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getScore() {
        return score;
    }

    public void setScore(byte score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    @Override
    public String toString() {
        return name; // This will be displayed in the ListView
    }
}
