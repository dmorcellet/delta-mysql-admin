package delta.tools.mysqladmin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database utilities.
 * @author DAM
 */
public class MySqlTools
{
  private static final String MYSQL_DRIVER="com.mysql.jdbc.Driver";
  private static final String MYSQL_JDBC_URL_TEMPLATE="jdbc:mysql://${HOST_NAME}:3306/${DB_NAME}";
  private static final String DEFAULT_HOST="localhost";

  /**
   * Open a new JDBC connection to a MySQL database.
   * @param target Target database.
   * @return the new connection.
   * @throws Exception If a connection error occurs.
   */
  public static Connection openConnection(MySqlTargetDbInfo target) throws Exception
  {
    Class.forName(MYSQL_DRIVER);
    String url=MYSQL_JDBC_URL_TEMPLATE;
    String hostName=target.getHost();
    if ((hostName==null) || (hostName.length()==0))
    {
      hostName=DEFAULT_HOST;
    }
    String dbName=target.getDbName();
    url=url.replace("${DB_NAME}",dbName);
    url=url.replace("${HOST_NAME}",hostName);
    String user=target.getUser();
    String password=target.getPassword();
    Connection c=DriverManager.getConnection(url,user,password);
    return c;
  }

  /**
   * Close a JDBC connection.
   * @param c Connection to close.
   */
  public static void closeConnection(Connection c)
  {
    if (c!=null)
    {
      try
      {
        if (!c.isClosed())
        {
          c.close();
        }
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
}

