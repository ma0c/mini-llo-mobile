package com.contraslash.android.mini_llo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Idea {

    @SerializedName("board")
    @Expose
    private int board;
    @SerializedName("text")
    @Expose
    private String text;

    public int getBoard() {
        return board;
    }

    public void setBoard(int board) {
        this.board = board;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

