package project.domain;

public class Location{

    private String name = null;
    private String roadnm_addr= null;
    private String lotno_addr= null;
    private String phone_number= null;
    private String type = null; /// from 이 어딘지 저장
    private int max_psn = 0;
    private double x;
    private double y;
    public Location(String name,String type, double x , double y) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Location(String name,String type , String roadnm_addr,String lotno_addr, double x , double y) {
        this.name = name;
        this.type = type;
        this.roadnm_addr = roadnm_addr;
        this.lotno_addr = lotno_addr;
        this.x = x;
        this.y = y;
    }

    public Location(String name,String type , String roadnm_addr,String lotno_addr,int max_psn , double x , double y) {
        this.name = name;
        this.type = type;
        this.roadnm_addr = roadnm_addr;
        this.lotno_addr = lotno_addr;
        this.max_psn =max_psn;
        this.x = x;
        this.y = y;
    }

    public Location(String name,String type , String roadnm_addr,String lotno_addr, String phone_number, double x , double y) {
        this.name = name;
        this.type = type;
        this.roadnm_addr = roadnm_addr;
        this.lotno_addr = lotno_addr;
        this.phone_number = phone_number;
        this.x = x;
        this.y = y;
    }

    public Location(String name,String type , String roadnm_addr,String lotno_addr, String phone_number, int max_psn, double x , double y) {
        this.name = name;
        this.type = type;
        this.roadnm_addr = roadnm_addr;
        this.lotno_addr = lotno_addr;
        this.phone_number = phone_number;
        this.max_psn = max_psn;
        this.x = x;
        this.y = y;
    }




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

    public String getRoadnm_addr() {
        return roadnm_addr;
    }

    public void setRoadnm_addr(String roadnm_addr) {
        this.roadnm_addr = roadnm_addr;
    }

    public String getLotno_addr() {
        return lotno_addr;
    }

    public void setLotno_addr(String lotno_addr) {
        this.lotno_addr = lotno_addr;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getMax_psn() {
        return max_psn;
    }

    public void setMax_psn(int max_psn) {
        this.max_psn = max_psn;
    }
}
