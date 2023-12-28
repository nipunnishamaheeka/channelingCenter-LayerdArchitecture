package lk.ijse.channelingCenter.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class  DbConnection {
    private static DbConnection dbconnection;
    @Getter
    private Connection connection;

    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/channelingcenter",
                "root",
                "Mahee@10985"

        );
    }

    public static DbConnection getInstance() throws SQLException {
        if (Objects.isNull(dbconnection)) {
            dbconnection = new DbConnection();
            return dbconnection;
        }else{
            return dbconnection;
        }

    }
    public static DbConnection getDbConnection() throws SQLException, ClassNotFoundException {
        return dbconnection == null ? dbconnection= new DbConnection() : dbconnection;
    }

    public Connection getConnection() {
        return connection;
    }

}
