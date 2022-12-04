package project.repository;

import project.domain.Location;
import project.domain.Marker;

import project.domain.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class SQLprocess {

    public SQLprocess(){}

    public int SQLMakeView(Statement st) throws SQLException {
        String make_view = "Drop view IF EXISTS aed_view CASCADE;\n" +
                "create view aed_view as\n" +
                "select INSTL_PLC as NAME , TELNO , REFINE_ROADNM_ADDR , REFINE_LOTNO_ADDR , REFINE_WGS84_LAT , \n" +
                "REFINE_WGS84_LOGT , null as MAX_ACEPTNC_PSN_CNT , 'aed' as dis_type\n" +
                "from aed;\n" +
                "\n" +
                "\n" +
                "create view chemical_view as\n" +
                "select EVACTN_PLC_NM as NAME , TELNO, REFINE_ROADNM_ADDR , REFINE_LOTNO_ADDR , REFINE_WGS84_LAT , \n" +
                "REFINE_WGS84_LOGT , MAX_ACEPTNC_PSN_CNT , 'chemical' as dis_type\n" +
                "from chemical;\n" +
                "\n" +
                "\n" +
                "create view CivilDefense_view as\n" +
                "select FACLT_NM_BULDNG_NM_INFO as NAME ,null as TELNO , REFINE_ROADNM_ADDR , REFINE_LOTNO_ADDR , REFINE_WGS84_LAT , \n" +
                "REFINE_WGS84_LOGT , null as MAX_ACEPTNC_PSN_CNT , 'civildefense' as dis_type\n" +
                "from CivilDefense\n" +
                "where UNITY_BSN_STATE_NM = '영업/정상';\n" +
                "\n" +
                "create view tsunami_view as\n" +
                "select EATQSNM_SHELTER_NM as NAME ,TELNO , REFINE_ROADNM_ADDR , REFINE_LOTNO_ADDR , REFINE_WGS84_LAT , \n" +
                "REFINE_WGS84_LOGT , MAX_ACEPTNC_PSN_CNT , 'tsunami' as dis_type\n" +
                "from tsunami\n";
        return (st.executeUpdate(make_view));
    }
    public ArrayList<Location> Rs_to_Location(ResultSet rs) throws SQLException {


        ArrayList<Location> marker_locations = new ArrayList<Location>();

        while(rs.next()){
            String name = null;
            String roadnm_addr= null;
            String lotno_addr= null;
            String phone_number= null;
            String type = null; /// from 이 어딘지 저장
            int max_psn = 0;
            double x = 0;
            double y = 0;
            double distance = 0;

            String temp = "";
            name = rs.getString("NAME");
//            type = "tsnami";
            type = rs.getString("dis_type");
            roadnm_addr = rs.getString("REFINE_ROADNM_ADDR");
            lotno_addr = rs.getString("REFINE_LOTNO_ADDR");
            phone_number = rs.getString("TELNO");

            temp = rs.getString("MAX_ACEPTNC_PSN_CNT");
//            System.out.println(temp);
//            System.out.println((temp , "null"));
            if (temp != null && !temp.equals( "null"))  max_psn = Integer.valueOf(temp);
            temp = rs.getString("REFINE_WGS84_LAT");
            if (temp != null && !temp.equals( "null"))   x = Double.valueOf(temp);
            temp =rs.getString("REFINE_WGS84_LOGT");
            if (temp != null && !temp.equals( "null"))   y = Double.valueOf(temp);

            marker_locations.add(new Location(name ,type,roadnm_addr, lotno_addr,phone_number,max_psn,x,y));
        }
        return marker_locations;
    }

    public ArrayList<Comment> Rs_to_Comment(ResultSet rs) throws SQLException {


        ArrayList<Comment> comments  = new ArrayList<Comment>();

        while(rs.next()){
            String ID = null;
            String contents= null;
            String Time= null;
            double x = 0;
            double y = 0;

            String temp = "";
            ID = rs.getString("ID");
            contents = rs.getString("content");
            Time = rs.getString("Time");

            temp = rs.getString("REFINE_WGS84_LAT");
            if (temp != null && !temp.equals( "null"))   x = Double.valueOf(temp);
            temp =rs.getString("REFINE_WGS84_LOGT");
            if (temp != null && !temp.equals( "null"))   y = Double.valueOf(temp);

            comments.add(new Comment(ID, contents, Time, x, y) {
            });
        }
        return comments;
    }
}

//TELNO , REFINE_ROADNM_ADDR , REFINE_LOTNO_ADDR , REFINE_WGS84_LAT ,
//        REFINE_WGS84_LOGT , MAX_ACEPTNC_PSN_CNT , 'tsunami' as dis_type
