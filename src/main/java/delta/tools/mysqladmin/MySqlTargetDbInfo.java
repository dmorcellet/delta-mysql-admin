package delta.tools.mysqladmin;

/**
 * Information to connect to a database.
 * @author DAM
 */
public class MySqlTargetDbInfo
{
  /**
   * Root user.
   */
  public static final String MYSQL_ROOT_USER="root";
  /**
   * Name of the MySQL admin database.
   */
  public static final String MYSQL_ADMIN_DB="mysql";

  private String _host;
  private String _user;
  private String _password;
  private String _dbName;

  /**
   * Build a database info for an admin database.
   * @param hostName Server host-name.
   * @param rootPassword Root password.
   * @return a new database info.
   */
  public static MySqlTargetDbInfo newRootServerDbInfo(String hostName, String rootPassword)
  {
    return new MySqlTargetDbInfo(hostName,MYSQL_ROOT_USER,rootPassword,MYSQL_ADMIN_DB);
  }

  /**
   * Constructor.
   * @param host Server host-name.
   * @param user User.
   * @param password Password.
   * @param dbName Database name.
   */
  public MySqlTargetDbInfo(String host, String user, String password, String dbName)
  {
    _host=host;
    _user=user;
    _password=password;
    _dbName=dbName;
  }

  /**
   * Get the server host-name.
   * @return the server host-name.
   */
  public String getHost()
  {
    return _host;
  }

  /**
   * Get the user login.
   * @return a user login.
   */
  public String getUser()
  {
    return _user;
  }

  /**
   * Get the user password.
   * @return a user password.
   */
  public String getPassword()
  {
    return _password;
  }

  /**
   * Get the target database.
   * @return a database name.
   */
  public String getDbName()
  {
    return _dbName;
  }
}
