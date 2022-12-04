package project.repository;

import project.domain.Comment;
import project.domain.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class SQLInsert {
    public int comment_Insert(Statement st , Location location, Comment comment) throws SQLException {
        ResultSet rs = null;
//        double distance = 0.0001;
        String writer = "'"+comment.getID()+"'";
        String contents = "'"+comment.getContents()+"'";
        double x = comment.getX();
        double y = comment.getY();
        String time = "'"+comment.getTime()+"'";
//        System.out.println(time);


        String query = "insert into comments values("+writer+" , "+contents+" , "+time+" , "+Double.toString(x)+", "+Double.toString(y)+");";
        System.out.println(query);
        return st.executeUpdate(query);

    }
}
