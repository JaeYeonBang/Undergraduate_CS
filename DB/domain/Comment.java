package project.domain;

import java.time.LocalDateTime;

public class Comment {
    private String ID;
    private String contents;
    private String Time;
    private double x;
    private double y;

    public Comment(String ID, String contents, String time, double x, double y) {
        this.ID = ID;
        this.contents = contents;
        Time = time;
        this.x = x;
        this.y = y;
    }

    public Comment(String ID, String contents, double x, double y) {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.ID = ID;
        this.contents = contents;
        this.Time = localDateTime.toString();
        this.x = x;
        this.y = y;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
