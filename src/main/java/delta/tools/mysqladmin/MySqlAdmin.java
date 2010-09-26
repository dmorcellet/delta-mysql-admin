package delta.tools.mysqladmin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL administration tools.
 * @author DAM
 */
public class MySqlAdmin
{
  private static final String MYSQL_DRIVER="com.mysql.jdbc.Driver";
  private static final String MYSQL_JDBC_URL_TEMPLATE="jdbc:mysql://${HOST_NAME}:3306/${DB_NAME}";
  private static final String MYSQL_ROOT_USER="root";
  private static final String MYSQL_ADMIN_DB="mysql";
  private static final String DEFAULT_HOST="localhost";

  /**
   * Test method.
   */
  public void doIt()
  {
    String hostname=null;
    String password=null;
    String dbName="toto";
    boolean ok=checkServer(hostname,password);
    System.out.println("Server : "+ok);
    if (ok)
    {
      Connection c=openRootConnection(hostname,password);
      createDatabase(c,dbName);
      List<String> databases=showDatabases(c);
      System.out.println(databases);
      dropDatabase(c,dbName);
    }
  }

  /**
   * Check the connection to a MySQL server.
   * @param hostName Targeted host.
   * @param rootPassword Root password.
   * @return <code>true</code> if connection is successfull, <code>false</code> otherwise.
   */
  public boolean checkServer(String hostName, String rootPassword)
  {
    boolean ok;
    try
    {
      Connection c=buildRootConnection(hostName, rootPassword);
      ok=true;
      closeConnection(c);
    }
    catch(Exception e)
    {
      ok=false;
      e.printStackTrace();
    }
    return ok;
  }

  /**
   * Open a connection to the mysql database of the MySQL server
   * on the indicated host, using user root and the given password.
   * @param hostName Name of the targeted host.
   * @param rootPassword Password to use.
   * @return A connection, or <code>null</code> if an error occurred.
   */
  public Connection openRootConnection(String hostName, String rootPassword)
  {
    Connection c;
    try
    {
      c=buildRootConnection(hostName, rootPassword);
    }
    catch(Exception e)
    {
      c=null;
    }
    return c;
  }

  /**
   * Get the name of the databases managed by the targeted
   * MySQL server. 
   * @param c Connection to the mysql database.
   * @return A list of database names.
   */
  public List<String> showDatabases(Connection c)
  {
    List<String> ret;
    String sql="SHOW DATABASES";
    try
    {
      ret=executeSQLQuery(c,sql);
    }
    catch(SQLException sqlException)
    {
      sqlException.printStackTrace();
      System.err.println("Show databases failed.");
      ret=null;
    }
    return ret;
  }

  /**
   * Create a database.
   * @param c Connection to the mysql database.
   * @param dbName Name of the database to create.
   * @return <code>true</code> if the database was created, <code>false</code> if
   * an error occurred.
   */
  public boolean createDatabase(Connection c,String dbName)
  {
    boolean ret;
    String sql="CREATE DATABASE "+dbName;
    try
    {
      executeSQL(c,sql);
      System.out.println("Database "+dbName+" created.");
      ret=true;
    }
    catch(SQLException sqlException)
    {
      sqlException.printStackTrace();
      System.err.println("Database "+dbName+" creation failed.");
      ret=false;
    }
    return ret;
  }

  /**
   * Drop a database.
   * @param c Connection to the mysql database.
   * @param dbName Name of the database to drop.
   * @return <code>true</code> if the database was dropped, <code>false</code> if
   * an error occurred.
   */
  public boolean dropDatabase(Connection c,String dbName)
  {
    boolean ret;
    String sql="DROP DATABASE "+dbName;
    try
    {
      executeSQL(c,sql);
      System.out.println("Database "+dbName+" dropped.");
      ret=true;
    }
    catch(SQLException sqlException)
    {
      sqlException.printStackTrace();
      System.err.println("Database "+dbName+" drop failed.");
      ret=false;
    }
    return ret;
  }

  private Connection buildRootConnection(String hostName,String password) throws Exception
  {
    Connection c=buildConnection(hostName,MYSQL_ADMIN_DB,MYSQL_ROOT_USER,password);
    return c;
  }

  private Connection buildConnection(String hostName, String dbName, String user, String password) throws Exception
  {
    Class.forName(MYSQL_DRIVER);
    String url=MYSQL_JDBC_URL_TEMPLATE;
    if ((hostName==null) || (hostName.length()==0))
    {
      hostName=DEFAULT_HOST;
    }
    url=url.replace("${DB_NAME}",dbName);
    url=url.replace("${HOST_NAME}",hostName);
    Connection c=DriverManager.getConnection(url,user,password);
    return c;
  }

  private void closeConnection(Connection c)
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

  private void executeSQL(Connection c, String sql) throws SQLException
  {
    Statement s=null;
    try
    {
      s=c.createStatement();
      s.execute(sql);
    }
    catch(SQLException sqlException)
    {
      throw sqlException;
    }
    finally
    {
      if (s!=null)
      {
        try
        {
          s.close();
        }
        catch(SQLException sqlException2)
        {
          sqlException2.printStackTrace();
        }
      }
    }
  }

  private List<String> executeSQLQuery(Connection c, String sql) throws SQLException
  {
    List<String> ret=null;
    Statement s=null;
    ResultSet rs=null;
    try
    {
      s=c.createStatement();
      rs=s.executeQuery(sql);
      ret=new ArrayList<String>();
      while (rs.next())
      {
        ret.add(rs.getString(1));
      }
    }
    catch(SQLException sqlException)
    {
      throw sqlException;
    }
    finally
    {
      if (rs!=null)
      {
        try
        {
          rs.close();
        }
        catch(SQLException sqlException2)
        {
          sqlException2.printStackTrace();
        }
      }
      if (s!=null)
      {
        try
        {
          s.close();
        }
        catch(SQLException sqlException2)
        {
          sqlException2.printStackTrace();
        }
      }
    }
    return ret;
  }

  /**
   * Main method (for tests).
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MySqlAdmin().doIt();
  }

  // get structure script:
  //mysqldump --user=root --add-drop-table --no-data --order-by-primary genea > genea.sql
}
