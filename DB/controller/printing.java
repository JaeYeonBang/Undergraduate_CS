package project.controller;

import project.domain.Location;
import project.domain.Comment;

import java.util.ArrayList;

public class printing {

    public static void print_Menu(){
        System.out.println("====================================");
        System.out.println("==============메뉴===================");
        System.out.println("1. 내 주변에 있는 대피장소 보기");
        System.out.println("2. 내 주변 항목 별 대피장소 보기");
        System.out.println("3. 내 위치 옮기기");
        System.out.println("4. 종료");
        System.out.println("====================================");
    }

    public static void print_Locations(ArrayList<Location> locations) {
        String name = null;
        String roadnm_addr= null;
        String lotno_addr= null;
        String phone_number= null;
        String type = null; /// from 이 어딘지 저장
        int max_psn = 0;
        double distance;
        int num = 1;
        System.out.println("====================================");
        for( Location l : locations){
            name = l.getName();
            roadnm_addr = l.getRoadnm_addr();
            lotno_addr = l.getLotno_addr();
            phone_number = l.getPhone_number();
            type = type_change(l.getType());
            max_psn = l.getMax_psn();
            distance = l.getDistance();
            if (type.equals(("AED"))) {
                System.out.printf("%3d | 타입: %-24s 이름 : %-60s 직선거리 : %5.1fm 최대 수용인원 : %5d명 전화번호:%13s\n",num,type,name,distance,max_psn,phone_number);
            } else {
//            System.out.printf("%3d | 타입:%8s 이름 : %20s 직선거리 : %5.1f 최대 수용인원 : %5d명 전화번호 : %15s 도로명주소 : %25s 지번주소 : %25s\n",num,type,name,distance,max_psn,phone_number,roadnm_addr,lotno_addr);
            System.out.printf("%3d | 타입: %-20s 이름 : %-60s 직선거리 : %5.1fm 최대 수용인원 : %5d명 전화번호:%13s\n",num,type,name,distance,max_psn,phone_number);}
            num++;
        }
        System.out.println("====================================");
    }

//    public static void print_Locations(ArrayList<Location> locations) {
//        String name = null;
//        String roadnm_addr= null;
//        String lotno_addr= null;
//        String phone_number= null;
//        String type = null; /// from 이 어딘지 저장
//        int max_psn = 0;
//        double distance;
//        int num = 1;
//        System.out.println(String.format("%s%s%s%s%s%s"
//                , convert("", 3)
//                , convert("타입", 20)
//                , convert("이름", 60)
//                , convert("직선거리", 7)
//                , convert("수용인원", 7)
//                , convert("전화번호", 15)));
//        System.out.printf("===================================================%n");
//        for( Location l : locations){
//            name = l.getName();
//            roadnm_addr = l.getRoadnm_addr();
//            lotno_addr = l.getLotno_addr();
//            phone_number = l.getPhone_number();
//            type = type_change(l.getType());
//            max_psn = l.getMax_psn();
//            distance = l.getDistance();
//
//            System.out.printf("%3d",num);
//            System.out.print(convert(type , 20));
//            System.out.print(convert(name, 60));
//            System.out.printf("%7.1f",distance);
//            System.out.printf("%7d" , max_psn);
//            System.out.printf("%15s" , phone_number);
//            System.out.println();
//
//
//
////            System.out.printf("%3d | 타입:%8s 이름 : %20s 직선거리 : %5.1f 최대 수용인원 : %5d명 전화번호 : %15s 도로명주소 : %25s 지번주소 : %25s\n",num,type,name,distance,max_psn,phone_number,roadnm_addr,lotno_addr);
////                System.out.printf("%3d | 타입: %-20s 이름 : %-60s 직선거리 : %5.1fm 최대 수용인원 : %5d명 전화번호:%13s\n",num,type,name,distance,max_psn,phone_number);
//            num++;
//        }
//        System.out.println("====================================");
//    }
    public static String type_change(String type){
        if (type.equals("tsunami")) return "지진해일대피소";
        else if (type.equals("civildefense")) return "민방위대피시설";
        else if (type.equals("aed")) return "AED";
        else if (type.equals("chemical")) return "화학사고대피장소";

        return "";
    }

    public static void print_types() {
        System.out.println("====================================");

            System.out.printf("1. AED\t 2. 지진해일대피소\t 3. 민방위대피시설\t 4. 화학사고대피장소 \t 5. 나가기");

        System.out.println("\n====================================");
    }

    public static void print_specific() {
        System.out.println("====================================");

        System.out.printf("0. 나가기 \t 1~. 상세정보 및 댓글 확인");

        System.out.println("\n====================================");
    }

    public static void print_Location(Location l, int num) {
        String name = l.getName();
        String roadnm_addr = l.getRoadnm_addr();
        String lotno_addr = l.getLotno_addr();
        String phone_number = l.getPhone_number();
        String type = type_change(l.getType());
        int max_psn = l.getMax_psn();
        double distance = l.getDistance();
        System.out.println("\n===============상세정보===============");

        System.out.printf("%3d | 타입:%8s 이름 : %20s 직선거리 : %5.1f 최대 수용인원 : %5d명 전화번호 : %15s \n도로명주소 : %25s 지번주소 : %25s\n",num,type,name,distance,max_psn,phone_number,roadnm_addr,lotno_addr);

        System.out.println("\n====================================");
    }

    public static void print_comments(ArrayList<Comment> comments) {
        int num = 0;
        String ID = null;
        String contents= null;
        String Time = null;
        double x = 0    ;
        double y = 0;
        if(comments.size() == 0) {
            System.out.println("댓글이 없습니다.");
            return;
        }
        for (Comment c : comments) {
            ID = c.getID();
            contents = c.getContents();
            Time = c.getTime();
            x = c.getX();
            y = c.getY();
            num++;
            System.out.printf("%3d | ID:%15s 작성시간 : %20s 위도 : %10f \t경도 : %10f\n",num,ID,Time,x,y);
            System.out.println("-------------댓글 내용------------");
            System.out.println(contents);
            System.out.println("--------------------------------");
        }




    }

    private static int getKorCnt(String kor) {
        int cnt = 0;
        for (int i = 0 ; i < kor.length() ; i++) {
            if (kor.charAt(i) >= '가' && kor.charAt(i) <= '힣') {
                cnt++;
            }
        } return cnt;
    }

    // 전각문자의 개수만큼 문자열 길이를 빼주는 메서드
    public static String convert(String word, int size) {
        String formatter = String.format("%%%ds", size -getKorCnt(word));
        return String.format(formatter, word);
    }
}



