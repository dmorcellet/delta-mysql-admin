package delta.tools.mysqladmin;

import java.sql.Connection;

/**
 * Interface to a MySQL server.
 * @author DAM
 */
public class MySqlServer
{
  private String _hostName;

  /**
   * Constructor.
   * @param hostName Server host-name.
   */
  public MySqlServer(String hostName)
  {
    _hostName=hostName;
  }

  /**
   * Check the connection to a MySQL server.
   * @param rootPassword Root password.
   * @return <code>true</code> if connection is successful, <code>false</code> otherwise.
   */
  public boolean checkServer(String rootPassword)
  {
    boolean ok;
    try
    {
      MySqlTargetDbInfo target=MySqlTargetDbInfo.newRootServerDbInfo(_hostName,rootPassword);
      Connection c=MySqlTools.openConnection(target);
      ok=true;
      MySqlTools.closeConnection(c);
    }
    catch(Exception e)
    {
      ok=false;
      e.printStackTrace();
    }
    return ok;
  }
}
