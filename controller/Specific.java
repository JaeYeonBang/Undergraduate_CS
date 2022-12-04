package project.controller;

import project.domain.Comment;
import project.domain.Location;
import project.repository.SQLInsert;
import project.repository.SQLSelect;
import project.repository.SQLprocess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import static project.controller.printing.*;

public class Specific {

    static SQLSelect sqlSelect = new SQLSelect();
    static SQLprocess sqLprocess = new SQLprocess();
    public Specific(Statement st, Location center , ResultSet rs, ArrayList<Location> Locations_Result ) throws SQLException {
        Scanner scn = new Scanner(System.in);
        String input = "";
        Location location;

        while(true) {
            print_specific();
            input = scn.next();
        if(input.equals("0")) break;
        else if (Integer.valueOf(input) > Locations_Result.size() ) break;
        else if (Integer.valueOf(input) <= Locations_Result.size()) {
            location = Locations_Result.get(Integer.valueOf(input) - 1);
            print_Location(location, Integer.valueOf(input));
            View_Add_Comment(location , st,  rs);

        }
        else continue;
        }


    }

    public static void View_Add_Comment(Location location , Statement st,  ResultSet rs) throws SQLException {

        Scanner scn = new Scanner(System.in);
        String input = "";

        SQLInsert sqlInsert = new SQLInsert();
            rs = sqlSelect.comment_select(st, location);
            ArrayList<Comment> comments = new ArrayList<>();
            comments = sqLprocess.Rs_to_Comment(rs);
            print_comments(comments);
            comments = null;
        while(true) {
            System.out.println(("0. 나가기\t 1. 댓글읽기\t2. 댓클추가"));
            input = scn.next();

            if(input.equals("0")) return;
            else if (input.equals("1")) {
                rs = sqlSelect.comment_select(st, location);
                comments = sqLprocess.Rs_to_Comment(rs);
                print_comments(comments);
            }
            else if (input.equals("2")) {
                String ID;
                String contents;
                double x = location.getX();
                double y = location.getY();

                System.out.println(("아이디를 입력해 주세요."));
                ID = scn.next();
                System.out.println(("내용을 입력해 주세요."));
                contents = scn.next();

                Comment new_comment = new Comment(ID,contents,x,y);

                sqlInsert.comment_Insert(st,location,new_comment);

            }
            else continue;
        }
    }
}

