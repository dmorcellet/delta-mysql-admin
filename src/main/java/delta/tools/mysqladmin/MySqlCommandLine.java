package delta.tools.mysqladmin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.system.externalCommands.ExternalCommand;
import delta.common.utils.text.TextUtils;

/**
 * @author DAM
 */
public class MySqlCommandLine
{
  private String getMysqlExecutable()
  {
    return "mysql";
  }

  public void executeScript(MySqlTargetDbInfo target, String script)
  {
    ExternalCommand cmd=buildCommand(target);
    boolean ok=cmd.executeAsynchronously();
    if (ok)
    {
      cmd.writeToStdin(script);
      cmd.writeToStdin("exit\n");
      cmd.waitForEndOfCommand(10000);
    }
    System.out.println(cmd.getStdout());
    System.out.println(cmd.getStderr());
  }

  private ExternalCommand buildCommand(MySqlTargetDbInfo target)
  {
    String executable=getMysqlExecutable();
    List<String> args=buildArgs(target);
    ExternalCommand cmd=new ExternalCommand("mysql command line",executable,args);
    cmd.storeStdout(true);
    cmd.storeStderr(true);
    return cmd;
  }

  private List<String> buildArgs(MySqlTargetDbInfo target)
  {
    List<String> ret=new ArrayList<String>();
    String host=target.getHost();
    if ((host!=null) && (host.length()>0))
    {
      ret.add("--host="+host);
    }
    String user=target.getUser();
    if ((user!=null) && (user.length()>0))
    {
      ret.add("--user="+user);
    }
    String password=target.getPassword();
    if ((password!=null) && (password.length()>0))
    {
      ret.add("--password="+password);
    }
    String dbName=target.getDbName();
    if ((dbName!=null) && (dbName.length()>0))
    {
      ret.add(dbName);
    }
    return ret;
  }

  public static void main(String[] args)
  {
    File inFile=new File("/home/dm/genea.sql");
    String contents=TextUtils.loadTextFile(inFile,null);
    MySqlCommandLine cmdLine=new MySqlCommandLine();
    MySqlTargetDbInfo target=new MySqlTargetDbInfo(null,"dada","glor4fin3del","dbouquet");
    cmdLine.executeScript(target,contents);
  }
}
