package project.controller;

import project.service.MakeMapService;
import project.domain.Location;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        String input = "";

        Location center = new Location("Ajou University" , "center" , 37.2838, 127.0437);

        ArrayList<Location> marker_locations = new ArrayList<Location>();
        marker_locations.add(new Location("A" , "",37.2838  ,127.0437));
        marker_locations.add(new Location("B" , "",37.2858  ,127.0457));

        while(true) {
            input = scn.nextLine();
            if (input == "0") break;

            MakeMapService makeMap = new MakeMapService(center,marker_locations);

        }
    }
}


