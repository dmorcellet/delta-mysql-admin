package delta.tools.mysqladmin;

/**
 * Information to connect to a database.
 * @author DAM
 */
public class MySqlTargetDbInfo
{
  public static final String MYSQL_ROOT_USER="root";
  public static final String MYSQL_ADMIN_DB="mysql";

  private String _host;
  private String _user;
  private String _password;
  private String _dbName;

  public static MySqlTargetDbInfo newRootServerDbInfo(String hostName, String rootPassword)
  {
    return new MySqlTargetDbInfo(hostName,MYSQL_ROOT_USER,rootPassword,MYSQL_ADMIN_DB);
  }

  public MySqlTargetDbInfo(String host, String user, String password, String dbName)
  {
    _host=host;
    _user=user;
    _password=password;
    _dbName=dbName;
  }
  
  public String getHost()
  {
    return _host;
  }

  public String getUser()
  {
    return _user;
  }
  
  public String getPassword()
  {
    return _password;
  }
  
  public String getDbName()
  {
    return _dbName;
  }
}
