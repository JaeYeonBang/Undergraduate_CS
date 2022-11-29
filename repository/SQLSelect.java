package project.repository;
import project.domain.Location;

import java.sql.*;
public class SQLSelect {
    public ResultSet tsunami_select(Statement st , Location center) throws SQLException {
        ResultSet rs = null;
        double distance = 0.0001;
        double x = center.getX();
        double y = center.getY();
        String query = "select *\n" +
                "from (select * ,power(REFINE_WGS84_LAT -"+Double.toString(x)+",2 ) + power(REFINE_WGS84_LOGT -"+Double.toString(y) +",2) as difference \n" +
                "from tsunami_view order by difference asc) Differ\n" +
                "where difference < "+Double.toString(distance)+";";
        rs = st.executeQuery(query);
        return rs;
    }
}

//TELNO , REFINE_ROADNM_ADDR , REFINE_LOTNO_ADDR , REFINE_WGS84_LAT ,
//        REFINE_WGS84_LOGT , MAX_ACEPTNC_PSN_CNT , 'tsunami' as dis_type

