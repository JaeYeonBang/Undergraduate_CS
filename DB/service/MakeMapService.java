package project.service;

import project.domain.Location;
import project.domain.Marker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;


public class MakeMapService extends JFrame {


    static final String chemical_icon = "https://raw.githubusercontent.com/JaeYeonBang/DB/main/chemical.png";
//    static final String war_icon = "https://upload.wikimedia.org/wikipedia/commons/f/fa/Map_marker_icon_%E2%80%93_Nicolas_Mollet_%E2%80%93_War_museum_%E2%80%93_Culture_%26_Entertainment_%E2%80%93_Default.png";
    static final String civildefense_icon = "https://raw.githubusercontent.com/JaeYeonBang/DB/main/war.png";
    static final String AED_icon = "https://upload.wikimedia.org/wikipedia/commons/5/52/Map_marker_icon_%E2%80%93_Nicolas_Mollet_%E2%80%93_Automated_External_Defibrillator_%E2%80%93_Health_%26_Education_%E2%80%93_Default.png";
    static final String tsunami_icon = "https://upload.wikimedia.org/wikipedia/commons/c/c0/Map_marker_icon_%E2%80%93_Nicolas_Mollet_%E2%80%93_Earthquake_%E2%80%93_Events_%E2%80%93_White.png";
    private GoogleAPIService googleAPI = new GoogleAPIService();
//    private String location = "ajou";

//    Marker marker =


    private JLabel googleMap;

    public MakeMapService(Location center , ArrayList<Location> marker_locations  ) {


        double[] center_x_y = {center.getX(), center.getY()};
        ArrayList<Marker> markers = Locations_to_Markers(marker_locations);

        googleAPI.downloadMap(center.getName(), center_x_y, markers);
        googleMap = new JLabel(googleAPI.getMap(center.getName()));
        googleAPI.fileDelete(center.getName());
        add(googleMap);
        setTitle(center.getName());
        setVisible(true);
        pack();
    }

    public ArrayList<Marker> Locations_to_Markers(ArrayList<Location> locations) {
        ArrayList<Marker> Markers = new ArrayList<>();
        for (Location location : locations){
            Markers.add(new Marker(type_To_iconURL(location.getType()) , location.getX() , location.getY()));
        }

        return Markers;
    }
    public String type_To_iconURL(String type) {

        String icon_url = "";
        if (Objects.equals(type, "tsunami")) icon_url = tsunami_icon;
        else if (Objects.equals(type, "chemical")) icon_url = chemical_icon;
        else if (Objects.equals(type, "civildefense")) icon_url = civildefense_icon;
        else if (Objects.equals(type,"aed")) icon_url = AED_icon;
//        System.out.println(type);
        return icon_url;
    }
}

