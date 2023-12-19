package com.tienminh.a5s_project_hand_on.classes;

public class Score {
    private Integer room_id;
    private Integer user_id;
    private Integer description_id;
    private Integer score;

    public Score(Integer room_id, Integer user_id, Integer description_id, Integer score) {
        this.room_id = room_id;
        this.user_id = user_id;
        this.description_id = description_id;
        this.score = score;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDescription_id() {
        return description_id;
    }

    public void setDescription_id(Integer description_id) {
        this.description_id = description_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
