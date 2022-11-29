package project.controller;

import project.repository.SQLconnect;
import project.service.MakeMapService;
import project.domain.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Controller {
    public static void main(String[] args) throws SQLException {

        SQLconnect sqLconnect = new SQLconnect();

        Scanner scn = new Scanner(System.in);
        String input = "";
        String buffer = "";

        Location center = new Location("Ajou University" , "center" , 37.2838, 127.0437);

        ArrayList<Location> marker_locations = new ArrayList<Location>();
        marker_locations.add(new Location("A" , "",37.2838  ,127.0437));
        marker_locations.add(new Location("B" , "",37.2858  ,127.0457));

        while(true) {

            print_Menu();
            input = scn.next();
            System.out.print(input);
//            System.out.println("====================================");
//            System.out.println("==============메뉴===================");
//            System.out.println("1. 내 주변에 있는 대피장소 보기");
            if (Objects.equals(input, "1")){
                MakeMapService makeMap = new MakeMapService(center,marker_locations);
            }

//            System.out.println("2. 내 주변 항목 별 대피장소 보기");
            if (Objects.equals(input, "2")){

            }
//            System.out.println("3. 특정 위치 근처 대피장소 보기");

            if (Objects.equals(input, "3")){

            }
//            System.out.println("4. 종료");
            if (Objects.equals(input, "4")) break;
//            System.out.println("====================================");


            MakeMapService makeMap = new MakeMapService(center,marker_locations);
            /// 1) 주변에 있는 대피장소 다 보여주기 (항목별로도 보여주고)
            /// 특정 상황 발생시, 그 상황에 맞는 가장가까운 대피장소 + 주변에 있는 거 보여주기
            /// 장소 선택 시, 상세정보 보여주기, 장소에 대한 평가 남기기 -> 1) 특정 고르고, 댓글이나 상세정보 확인하기. + 댓글쓰기
            /// 댓글 domain
            /// 거리계산 -> sql 단계에서 처리하기 orderby하고
            /// 다른위치에 대한 것
        }
    }

    public static void print_Menu(){
        System.out.println("====================================");
        System.out.println("==============메뉴===================");
        System.out.println("1. 내 주변에 있는 대피장소 보기");
        System.out.println("2. 내 주변 항목 별 대피장소 보기");
        System.out.println("3. 특정 위치 근처 대피장소 보기");
        System.out.println("4. 종료");
        System.out.println("====================================");
    }
}


