package com.tienminh.a5s_project_hand_on.classes;

public class Description extends Criteria{
    private String title;
    private String content;
    private Integer criteria_id;

    public Description(String nameOfCriteria) {
        super(nameOfCriteria);
    }

    public Description(String nameOfCriteria, String title, String content, Integer criteria_id) {
        super(nameOfCriteria);
        this.title = title;
        this.content = content;
        this.criteria_id = criteria_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCriteria_id() {
        return criteria_id;
    }

    public void setCriteria_id(Integer criteria_id) {
        this.criteria_id = criteria_id;
    }
}
