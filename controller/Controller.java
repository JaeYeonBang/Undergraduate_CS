package project.controller;

import project.repository.SQLSelect;
import project.repository.SQLconnect;
import project.repository.SQLprocess;
import project.service.MakeMapService;
import project.domain.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static project.controller.printing.*;
import static project.domain.Location.getDistance_of_Array;

public class Controller {
    public static void main(String[] args) throws SQLException {

        SQLconnect sqlconnect = new SQLconnect();
        SQLprocess sqlprocess = new SQLprocess();
        SQLSelect sqlSelect = new SQLSelect();
        sqlprocess.SQLMakeView(sqlconnect.getSt());

        Scanner scn = new Scanner(System.in);
        String input = "";
        String buffer = "";
        ResultSet rs = null;

        Location center = new Location("Ajou University" , "center" , 37.2838, 127.0437);


        while(true) {
            ArrayList<Location> Location_result = new ArrayList<Location>();
            print_Menu();
            input = scn.next();
            System.out.print(input);

            if (Objects.equals(input, "1")){

                System.out.println(center.getName());
                rs = sqlSelect.All_select(sqlconnect.getSt(), center);
                Location_result.addAll(sqlprocess.Rs_to_Location(rs));

                getDistance_of_Array(Location_result,center);

                print_Locations(Location_result);

                MakeMapService makeMap = new MakeMapService(center,Location_result);

                Specific specific = new Specific(sqlconnect.getSt(), center,rs,Location_result);
                System.out.println("\nend of 1 : ");
            }

//            System.out.println("2. 내 주변 항목 별 대피장소 보기");

            //("1. AED\t 2. 지진해일대피소\t 3. 민방위대피시설\t 4. 화학사고대피장소 \t 5. 나가기");
            else if (Objects.equals(input, "2")){

                print_types();
                input = scn.next();

                if (Objects.equals(input, "5")) continue;

                else if (Objects.equals(input, "1")){
                    rs = sqlSelect.aed_select(sqlconnect.getSt(), center);//
                    Location_result.addAll(sqlprocess.Rs_to_Location(rs));
                }

                else if (Objects.equals(input, "2")){
                    rs = sqlSelect.tsunami_select(sqlconnect.getSt(), center);//
                    Location_result.addAll(sqlprocess.Rs_to_Location(rs));
                }

                else if (Objects.equals(input, "3")){
                    rs = sqlSelect.civilDefense_select(sqlconnect.getSt(), center);//
                    Location_result.addAll(sqlprocess.Rs_to_Location(rs));
                }

                else if (Objects.equals(input, "4")){
                    rs = sqlSelect.chemical_select(sqlconnect.getSt(), center);//
                    Location_result.addAll(sqlprocess.Rs_to_Location(rs));
                }
                else continue;
                getDistance_of_Array(Location_result,center);
                print_Locations(Location_result);
                MakeMapService makeMap = new MakeMapService(center,Location_result);

                Specific specific = new Specific(sqlconnect.getSt(), center,rs,Location_result);
                System.out.println("\nend of 2 : ");
            }
//            System.out.println("3. 특정 위치 근처 대피장소 보기");
            else if (Objects.equals(input, "3")){
                double x;
                double y;
                String name;
                System.out.print("\n현재 위치의 위도를 입력하세요 : ");
                x = Double.valueOf(scn.next());
                System.out.print("\n현재 위치의 경도를 입력하세요 : ");
                y = Double.valueOf(scn.next());
                System.out.print("\n위치의 이름을 입력하세요 : ");
                name = scn.next();
                center = new Location(name , "center" , x, y);

                System.out.println("\nend of 2 : ");

            }
//            System.out.println("4. 종료");
            else if (Objects.equals(input, "4")) {
                System.out.println("\nend of 4 : ");
                return;
                }

        }


    }


}


