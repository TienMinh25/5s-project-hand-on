package com.tienminh.a5s_project_hand_on.classes;

public class Room {
    private String name;
    private Integer area_id;

    public Room(Integer area_id) {
        this.name = "";
        this.area_id = area_id;
    }

    public Room(String name, Integer area_id) {
        this.name = name;
        this.area_id = area_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }
}
