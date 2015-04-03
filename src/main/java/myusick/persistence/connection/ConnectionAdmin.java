package myusick.persistence.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Sandra on 30/03/2015.
 */
public class ConnectionAdmin {
    /*
	 * Atributos de la clase
	 */
    private final static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private final static String DRIVER_URL =
            "jdbc:mysql://localhost:3306/mydb";

    private final static String USER = "root";
    private final static String PASSWORD = "";


    static {

        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.err);
        }

    }

    private ConnectionAdmin() {}
    /**
     * Conecta con la base de datos de datos meteorologicos
     * @throws java.sql.SQLException
     */
    public final static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
    }
}
