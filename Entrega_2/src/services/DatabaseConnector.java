package services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private String url ="jdbc:oracle:thin:@localhost:1521:XE";
    private String username ="system";
    private String password = "juandiego25";
    Connection connection;

    public  Connection getConnection(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            System.out.println("conexión exitosa");
        }
        return connection;
    }
}