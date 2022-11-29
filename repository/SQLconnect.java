package project.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;


public class SQLconnect {
    Connection conn= null;
    Statement st = null;
    ResultSet rs = null;
    String url = "jdbc:postgresql://localhost:5433/postgres";
    String user = "postgres";
    String password = "1234";
    String query = null;
    String input = null;
    public SQLconnect() throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
        st = conn.createStatement();

        String create_table = "create table tsunami(EATQSNM_SHELTER_NM varchar , EATQSNM_SHELTER_DIV_NM varchar , EATQSNM_SHELTER_TYPE varchar,\n" +
                "EATQSNM_SHELTER_TYPE_DIV_NM varchar , REFINE_ROADNM_ADDR varchar , REFINE_LOTNO_ADDR varchar , REFINE_WGS84_LAT float,\n" +
                "REFINE_WGS84_LOGT float, ACEPTNC_POSBL_AR float , MAX_ACEPTNC_PSN_CNT int , EATQSNM_SHELTER_OPERT_STATE_YN varchar,\n" +
                "EATQSNM_SHELTER_TELNO varchar , SUBFACLT_CONVNCE_FACLT_INFO varchar , RESEVC_DISTRICT_NM varchar , RESEVC_DISTRICT_ISE_HSHLD_CNT int,\n" +
                "RESEVC_DISTRICT_ISE_RESPSN_CNT int , RESEVC_DISTRICT_ISE_DSWPSN_CNT\tint , QUKPRF_APPLCTN_YN_DIV_NM\tvarchar,\n" +
                "QUKPRF_DESIGN_GRAD varchar , SHORLN_DIST_DSTN varchar , ALIT_HG varchar , EARTHQUK_EVACTN_GDSPT_CNT int,\n" +
                "EMRGNCY_EVACTN_PLC_GDSPT_CNT varchar , EARTHQUK_EVCTLD_GDSPT_CNT varchar , MANAGE_INST_NM varchar ,\n" +
                "MNGINST_TELNO varchar ,DATA_STD_DE\tvarchar);\n;"; /// tsunami table 만들기
        String read_csv = "COPY tsunami FROM 'C:/tsunami1.csv' DELIMITER ',' CSV HEADER;";
//        st.executeUpdate(create_table);
        st.executeUpdate(read_csv);
    }
}
