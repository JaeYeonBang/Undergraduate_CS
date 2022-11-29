package project.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;


public class SQLconnect { //connet and create table
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

        String create_table = "DROP TABLE IF EXISTS tsunami CASCADE;\n" +
                "create table tsunami(EATQSNM_SHELTER_NM varchar , EATQSNM_SHELTER_DIV_NM varchar , EATQSNM_SHELTER_TYPE varchar,\n" +
                "EATQSNM_SHELTER_TYPE_DIV_NM varchar , REFINE_ROADNM_ADDR varchar , REFINE_LOTNO_ADDR varchar , REFINE_WGS84_LAT float,\n" +
                "REFINE_WGS84_LOGT float, ACEPTNC_POSBL_AR float , MAX_ACEPTNC_PSN_CNT int , EATQSNM_SHELTER_OPERT_STATE_YN varchar,\n" +
                "TELNO varchar , SUBFACLT_CONVNCE_FACLT_INFO varchar , RESEVC_DISTRICT_NM varchar , RESEVC_DISTRICT_ISE_HSHLD_CNT int,\n" +
                "RESEVC_DISTRICT_ISE_RESPSN_CNT int , RESEVC_DISTRICT_ISE_DSWPSN_CNT\tint , QUKPRF_APPLCTN_YN_DIV_NM\tvarchar,\n" +
                "QUKPRF_DESIGN_GRAD varchar , SHORLN_DIST_DSTN varchar , ALIT_HG varchar , EARTHQUK_EVACTN_GDSPT_CNT int,\n" +
                "EMRGNCY_EVACTN_PLC_GDSPT_CNT varchar , EARTHQUK_EVCTLD_GDSPT_CNT varchar , MANAGE_INST_NM varchar ,\n" +
                "MNGINST_TELNO varchar ,DATA_STD_DE\tvarchar);\n"; /// tsunami table 만들기
                st.executeUpdate(create_table);
        create_table = "DROP TABLE IF EXISTS chemical CASCADE;\n" +
                "create table chemical(METROPL_SIDO_NM varchar , SIGUN_NM varchar , MNG_NO_NM  varchar ,\n" +
                "FACLT_DIV  varchar , EVACTN_PLC_NM varchar , DETAIL_SI_DESC varchar , ACEPTNC_AR int,\n" +
                "MAX_ACEPTNC_PSN_CNT int , JURISD_NM varchar , TELNO varchar, CHRGPSN_NM varchar , CHARGE_TELNO varchar,\n" +
                "DATA_STD_DE varchar , AGRE_YN varchar , RM varchar , REFINE_ROADNM_ADDR varchar,\n" +
                "REFINE_LOTNO_ADDR varchar , REFINE_ZIPNO int , REFINE_WGS84_LAT float , REFINE_WGS84_LOGT float);\n"; // chemical table 만들기
                st.executeUpdate(create_table);
        create_table = "DROP TABLE IF EXISTS AED CASCADE;\n" +
                "create table AED(SIGUN_NM varchar , INSTL_PLC varchar , TELNO varchar,\n" +
                "REFINE_ROADNM_ADDR varchar , REFINE_LOTNO_ADDR varchar , REFINE_WGS84_LAT float,\n" +
                "REFINE_WGS84_LOGT float);\n"; // aed table 만들기
                st.executeUpdate(create_table);
        create_table = "DROP TABLE IF EXISTS CivilDefense CASCADE;\n" +
                "create table CivilDefense (MANAGE_NO varchar , LICENSG_DE int , LICENSG_CANCL_DE varchar ,\n" +
                "BSN_STATE_DIV_CD int , BSN_STATE_NM varchar , UNITY_BSN_STATE_DIV_CD int ,\n" +
                "UNITY_BSN_STATE_NM varchar , CLSBIZ_DE varchar ,ROADNM_ZIP_CD int , REFINE_ZIP_CD int, \n" +
                "EMRGNC_FACLT_LOC varchar , FACLT_NM_BULDNG_NM_INFO varchar , FACLT_DIV_NM varchar,\n" +
                "REFINE_ROADNM_ADDR varchar , REFINE_LOTNO_ADDR varchar , REFINE_WGS84_LAT float,\n" +
                "REFINE_WGS84_LOGT float , LOCPLC_AR_INFO float , TELNO varchar ,\n" +
                "LAST_UPD_TM varchar);\n" +
                "\n"; // civil defence table 만들기
        st.executeUpdate(create_table);
        String read_csv = "COPY tsunami FROM 'C:/tsunami.csv' DELIMITER ',' CSV HEADER;";
        st.executeUpdate(read_csv);

        read_csv = "COPY CivilDefense FROM 'C:/war.csv' DELIMITER ',' CSV HEADER;";
        st.executeUpdate(read_csv);

        read_csv = "COPY AED FROM 'C:/aed.csv' DELIMITER ',' CSV HEADER;";
        st.executeUpdate(read_csv);

        read_csv = "COPY chemical FROM 'C:/chemical.csv' DELIMITER ',' CSV HEADER;";
        st.executeUpdate(read_csv);
    }

    public Statement getSt() {
        return st;
    }
}
