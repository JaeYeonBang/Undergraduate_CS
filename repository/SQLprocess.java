package project.repository;

import project.domain.Marker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLprocess {


    public ArrayList<Marker> makelocaton(ResultSet rs) throws SQLException {
        ArrayList<Marker> markers = new ArrayList<Marker>();
        try {
            while (rs.next()) {
                String type = rs.getString("sID");
                String x = rs.getString("sName");
                String y = rs.getString("GPA");
                String sizeHS = rs.getString("sizeHS");
            }
        } catch (SQLException e) {
            throw e;
        }
        return markers;
    }
}
