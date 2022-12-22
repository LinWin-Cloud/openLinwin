

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class init {
    public static void initAllValue(String[] args) {
        /*
         * how to boot the LinWin Multi Server:
         * java -jar MultiServer [ServerPath] [ServerPort]
         */
        try {
            MultiServer.ServerDir = args[0];
            MultiServer.ServerPort = Integer.valueOf(args[1]);
            //Get how to start the sql safe.
            MultiServer.Sql_safe = init.getSafe("SQL_Protection: ");
            //Get how to start the Xss Safe.
            MultiServer.Xss_safe = init.getSafe("XSS_Protection: ");
            //Get Ip black.
            MultiServer.IPBlack = init.IPBLACK();
            //Get Version Information.
            MultiServer.Version = init.ReadLine(MultiServer.configRoot+"Version.txt");
            MultiServer.errorPage = MultiServer.DebugDir+init.GetErrorPage();
            MultiServer.doNotVisit = init.doNotVisit();
            MultiServer.urlOK = init.urlOK();
            MultiServer.defaultPage = init.defaultPage();
            MultiServer.strict_origin_when_cross_origin = WebClient.strict_origin_when_cross_origin();
            //VirtualVisist.ServerFile_Web();
            MultiServer.defaultPage_str = init.defaultPage_str();

        }catch(Exception exception) {
            exception.printStackTrace();
        }
    }    
    public static Boolean getSafe(String safe_do)
    {
        /**
         * 参数: safe_do，用于探测需要获取什么数据
         */
        try
        {
            File file = new File(MultiServer.rulesRoot+"Security.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Boolean boolen = false;
            while ((line=bufferedReader.readLine())!=null)
            {
                int in_line = line.indexOf(safe_do);
                if (in_line!=-1)
                {
                    line = line.substring(in_line+safe_do.length(),line.indexOf(";"));
                    if (line.equals("true"))
                    {
                        boolen = true;
                    }else{
                        boolen = false;
                    }
                    break;
                }else{
                    boolen = false;
                }
            }
            bufferedReader.close();
            return boolen;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static HashMap<String,String> IPBLACK() {
        /*
         * 读取IP黑名单配置
         */
        try
        {
            File file = new File(MultiServer.rulesRoot+"IpBlack.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            HashMap<String,String> hashMap = new HashMap<>();
            while ((line=bufferedReader.readLine())!=null) {
                hashMap.put(line,"true");
            }
            return hashMap;

        }catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public static String ReadLine(String name) throws Exception
    {
        File file = new File(name);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        bufferedReader.close();

        return line;
    }
    public static String GetErrorPage()
    {
        try{
            File file = new File(MultiServer.configRoot+"ErrorPage.cfg");
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
    public static String[] doNotVisit()
    {
        //判断页面是否允许被访问
        try
        {
            File file = new File(MultiServer.rulesRoot+"DoNotVisitContent.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String tmp;

            List<String> list = new ArrayList<String>();
            while ((tmp=bufferedReader.readLine())!=null)
            {
                list.add(tmp);
            }
            tmp = list.toString();
            //System.out.println(boolean1);

            bufferedReader.close();
            fileReader.close();
            
            return tmp.split(",");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static String[] urlOK()
    {
        try
        {
            // 读取配置文件
            File file = new File(MultiServer.rulesRoot+"URL_Return.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            List<String> list = new ArrayList<String>();
            while ((line=bufferedReader.readLine())!=null)
            {
                String configURL = line.substring(line.indexOf("URL: ")+5,line.indexOf(";"));
                list.add(configURL);
            }
            bufferedReader.close();
            fileReader.close();
            return list.toString().replace("[", "").replace("]", "").split(",");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static HashMap<String,String> defaultPage()
    {
        /**
         * 判断请求页面是否是默认页面
         */
        try {
            File file = new File(MultiServer.configRoot+"defaultPage.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String tmp;
            HashMap<String,String> hashMap = new HashMap<>();
            while ((tmp=bufferedReader.readLine())!=null)
            {
                hashMap.put(tmp,"true");
            }
            return hashMap;
        }catch (Exception e){
            return null;
        }
    }
    public static String[] defaultPage_str()
    {
        /**
         * 判断请求页面是否是默认页面
         */
        try {
            File file = new File(MultiServer.configRoot+"defaultPage.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String tmp;
            List<String> list = new ArrayList<>();
            while ((tmp=bufferedReader.readLine())!=null)
            {
                list.add(tmp);
            }
            String[] strings = list.toArray(new String[list.size()]);
            return strings;
        }catch (Exception e){
            return null;
        }
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
            System.out.println("1: "+Config_user+" "+Config_user.equals(user)+";2: "+Config_Pwd+";"+" "+Config_Pwd.equals(pwd)+" &&& "+user+" "+pwd);
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
}
