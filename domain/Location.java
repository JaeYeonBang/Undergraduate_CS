package project.domain;

public class Location{
    public Location(String name,String type, double x , double y) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
    }
    private String name;

    private String type ;
    private double x;
    private double y;


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.name = type;
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
