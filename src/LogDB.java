import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

public class LogDB {

    private static Map dbParams;
    private static Connection connection = null;
    private static Statement stmt;

    static {
        try {
            stmt = LogDB.getConnection().createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private LogDB(){
        try{
            Properties connectionProps = new Properties();

            connectionProps.put("user",dbParams.get("userName"));
            connectionProps.put("password",dbParams.get("password"));
            connection =DriverManager.getConnection("jdbc:"+dbParams.get("dbms")+"://"+dbParams.get("serverName")
                    +":"+dbParams.get("portNumber")+"/",connectionProps);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        if(connection== null){
            new LogDB();
        }
        return connection;
    }

    public static Map getDbParams() {
        return dbParams;
    }
    public static void setDbParams(Map dbParams) {
        LogDB.dbParams = dbParams;
    }

    public static Statement getStmt() {
        return stmt;
    }
}
