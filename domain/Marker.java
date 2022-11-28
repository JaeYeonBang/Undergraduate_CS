package project.domain;

public class Marker{

    public Marker(String icon_Url, double x , double y) {
        this.icon_Url = icon_Url;
        this.x = x;
        this.y = y;
    }
    private String icon_Url;
    private double x;
    private double y;

    public String getIcon_Url() {
        return icon_Url;
    }

    public void setIcon_Url(String icon_Url) {
        this.icon_Url = icon_Url;
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
