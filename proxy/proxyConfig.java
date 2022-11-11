
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class proxyConfig {
    public static String ReadConfig(String config)
    {
        try
        {
            File file = new File("../config/proxy.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line=bufferedReader.readLine())!=null)
            {
                int c = line.indexOf(config);
                if (c != -1)
                {
                    line = line.substring(c+config.length(),line.indexOf(";"));
                    break;
                }
            }
            line = line.replace(" ","");
            bufferedReader.close();
            return line;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static String GetAgent(Socket socket) throws Exception
    {
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while ((line=bufferedReader.readLine())!=null)
        {
            int a = line.indexOf("User-Agent: ");
            if (a != -1)
            {
                line = line.substring(a+"User-Agent: ".length(),line.length());
                break;
            }
            else
            {
                line = null;
            }
        }
        return line;
    }
    public static Boolean GetStart(Socket socket) throws Exception
    {
        return true;
    }
    public static Boolean ISUSER(String user,String pwd) throws Exception
    {
        File file = new File("../config/Admin.cfg");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String lien;
        Boolean boolean1 = false;
        while ((lien=bufferedReader.readLine())!=null)
        {
            String Config_user = lien.substring(lien.indexOf("User: ")+"User: ".length(),lien.indexOf(";Pwd: "));
            String Config_Pwd = lien.substring(lien.indexOf("Pwd: ")+"Pwd: ".length(),lien.length()-1);
            user = user.replace("\n", "");
            pwd = user.replace("\n", "");
            if (Config_Pwd.equals(pwd)&&Config_user.equals(user))
            {
                boolean1 = true;
                break;
            }else {
                boolean1 = false;
            }
        }
        bufferedReader.close();
        return boolean1;
    }
    public static String GetErrorPage()
    {
        try{
            File file = new File("../config/ErrorPage.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String tmp;
            String re = " ";

            while ((tmp=bufferedReader.readLine())!=null)
            {
                int start = tmp.indexOf("ErrorPage: ");
                if (start != -1)
                {
                    int end = tmp.indexOf(";");
                    re = tmp.substring(start+11,end);
                }
            }
            bufferedReader.close();
            return re;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
