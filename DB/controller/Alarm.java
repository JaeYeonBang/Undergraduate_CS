package project.controller;

import project.domain.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Alarm implements Runnable {

    Callback callback;
    ResultSet rs;
    ArrayList<Location> locations;
    Location center;
    public Alarm(Callback callback, ResultSet rs , ArrayList<Location> locations, Location center) {
        this.callback = callback;
        this.rs = rs;
        this.locations = locations;
        this.center = center;
    }

    @Override
    public void run() {


        String text = getMessage();
        try {
            callback.disaster_alarm(text , this.rs , this.locations,this.center);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String getMessage() {

        String Message = "[기상청] 10월29일08:27 충북 괴산군 북동쪽 12km 지역 규모4.3 지진발생/낙하물로부터 몸 보호, 진동 멈춘 후 야외 대피하며 여진주의";
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("메시지\n----------------------------\n"+Message);

        return check_Message(Message);
    }
    public String check_Message(String M) {

        if(M.contains("지진발생") &&  M.contains("여진주의")) return "지진경보";
        else if (M.contains("화학") &&  M.contains("사고")) return "화학사고경보";
        else if (M.contains("민방위") &&  M.contains("대피")) return "민방위경보";

        return "";
    }
}



interface Callback {
    void disaster_alarm(String text, ResultSet rs, ArrayList<Location> locations, Location center) throws SQLException;
}

