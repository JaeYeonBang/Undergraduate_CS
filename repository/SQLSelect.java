package project.repository;
import project.domain.Location;

import java.sql.*;
public class SQLSelect {
    private double distance = 0.0001;
    public ResultSet tsunami_select(Statement st , Location center) throws SQLException {
        ResultSet rs = null;
//        double distance = 0.0001;
        double x = center.getX();
        double y = center.getY();
        String query = "select *\n" +
                "from (select * ,power(REFINE_WGS84_LAT -"+Double.toString(x)+",2 ) + power(REFINE_WGS84_LOGT -"+Double.toString(y) +",2) as difference \n" +
                "from tsunami_view order by difference asc) Differ\n" +
                "where difference < "+Double.toString(distance)+";";
        rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet aed_select(Statement st , Location center) throws SQLException {
        ResultSet rs = null;
//        double distance = 0.0001;
        double x = center.getX();
        double y = center.getY();
        String query = "select *\n" +
                "from (select * ,power(REFINE_WGS84_LAT -"+Double.toString(x)+",2 ) + power(REFINE_WGS84_LOGT -"+Double.toString(y) +",2) as difference \n" +
                "from aed_view order by difference asc) Differ\n" +
                "where difference < "+Double.toString(distance)+";";
        rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet civilDefense_select(Statement st , Location center) throws SQLException {
        ResultSet rs = null;
//        double distance = 0.0001;
        double x = center.getX();
        double y = center.getY();
        String query = "select *\n" +
                "from (select * ,power(REFINE_WGS84_LAT -"+Double.toString(x)+",2 ) + power(REFINE_WGS84_LOGT -"+Double.toString(y) +",2) as difference \n" +
                "from CivilDefense_view order by difference asc) Differ\n" +
                "where difference < "+Double.toString(distance)+";";
        rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet chemical_select(Statement st , Location center) throws SQLException {
        ResultSet rs = null;
//        double distance = 0.0001;
        double x = center.getX();
        double y = center.getY();
        String query = "select *\n" +
                "from (select * ,power(REFINE_WGS84_LAT -"+Double.toString(x)+",2 ) + power(REFINE_WGS84_LOGT -"+Double.toString(y) +",2) as difference \n" +
                "from chemical_view order by difference asc) Differ\n" +
                "where difference < "+Double.toString(distance)+";";
        rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet All_select(Statement st , Location center) throws SQLException {
        ResultSet rs = null;
//        double distance = 0.0001;
        System.out.println(center.getName() + center.getX() + center.getY());
        double x = center.getX();
        double y = center.getY();
        String query = "select * \n" +
                "from (\n" +
                "select * \n" +
                "from (select * , power(REFINE_WGS84_LAT -"+Double.toString(x)+",2) + power(REFINE_WGS84_LOGT -"+Double.toString(y)+",2) as difference\n" +
                "from tsunami_view order by difference asc) Differ\n" +
                "where difference <" +Double.toString(distance)+"\n" +
                "union\n" +
                "select * \n" +
                "from (select * , power(REFINE_WGS84_LAT -"+Double.toString(x)+",2) + power(REFINE_WGS84_LOGT -"+Double.toString(y)+",2) as difference\n" +
                "from chemical_view order by difference asc) Differ\n" +
                "where difference <" +Double.toString(distance)+"\n" +
                "union\n" +
                "select * \n" +
                "from (select * , power(REFINE_WGS84_LAT -"+Double.toString(x)+" ,2) + power(REFINE_WGS84_LOGT - "+Double.toString(y)+",2) as difference\n" +
                "from civildefense_view order by difference asc) Differ\n" +
                "where difference <" +Double.toString(distance)+"\n" +
                "union\n" +
                "select * \n" +
                "from (select * , power(REFINE_WGS84_LAT -"+Double.toString(x)+",2) + power(REFINE_WGS84_LOGT -"+Double.toString(y)+",2) as difference\n" +
                "from aed_view order by difference asc) Differ\n" +
                "where difference <" +Double.toString(distance)+"\n" +
                "\t) AllLocation\n" +
                "order by difference asc\n";
        rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet comment_select(Statement st , Location location) throws SQLException {

        ;
        ResultSet rs = null;
//        double distance = 0.0001;
        double x = location.getX();
        double y = location.getY();

        String query = "select * from comments where REFINE_WGS84_LAT ="+Double.toString(x)+" and REFINE_WGS84_LOGT = "+Double.toString(y)+";";
//        System.out.println(query);
        rs = st.executeQuery(query);
        return rs;
    }
}

//TELNO , REFINE_ROADNM_ADDR , REFINE_LOTNO_ADDR , REFINE_WGS84_LAT ,
//        REFINE_WGS84_LOGT , MAX_ACEPTNC_PSN_CNT , 'tsunami' as dis_type

