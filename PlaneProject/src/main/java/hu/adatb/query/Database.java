package hu.adatb.query;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static Connection ConnectionToDatabase() throws ClassNotFoundException, SQLException {
        OracleDataSource ods = new OracleDataSource();
        Class.forName ("oracle.jdbc.OracleDriver");
        ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");

        return ods.getConnection("system","oracle");
    }

}
