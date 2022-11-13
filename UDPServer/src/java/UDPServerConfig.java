import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class UDPServerConfig {

    public static String ConfigRoot = "../../../config/";

    public static String GetErrorPage()
    {
        try{
            File file = new File(ConfigRoot+"ErrorPage.cfg");
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
    public static Boolean DoNotVisit(String page)
    {
        //判断页面是否允许被访问
        try
        {
            File file = new File("../../rules/DoNotVisitContent.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String tmp;
            Boolean boolean1 = false;

            while ((tmp=bufferedReader.readLine())!=null)
            {
                //System.out.println(tmp.equals(page));
                if (page.lastIndexOf(tmp)!=-1)
                {
                    //页面在配置中是不允许被访问的
                    boolean1 = true;
                    break;
                }
                else
                {
                    boolean1 = false;
                }
            }
            //System.out.println(boolean1);

            bufferedReader.close();
            return boolean1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
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
    public static String GetNowTime()
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
    public static String GetLogTime()
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
    public static int API_Port() throws Exception
    {
        /**
         * 获取API服务端口
         */
        File file = new File(ConfigRoot+"Server.cfg");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int i = 10142;
        while((line=bufferedReader.readLine())!=null)
        {
            if (line.indexOf("API_Port: ")!=-1)
            {
                String Str_Port = line.substring(line.indexOf("API_Port: ")+"API_Port: ".length(), line.indexOf(";"));
                i = Integer.valueOf(Str_Port);
            }
        }
        bufferedReader.close();
        return i;
    }
    public static Boolean isURLOK(String url)
    {
        try
        {
            // 读取配置文件
            File file = new File("../../../rules/URL_Return.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            Boolean boolen = false;

            while ((line=bufferedReader.readLine())!=null)
            {
                String configURL = line.substring(line.indexOf("URL: ")+5,line.indexOf(";"));
                //String configReturn = line.substring(line.indexOf("Return: ")+8,line.lastIndexOf(";"));
                if (configURL.equals(url))
                {
                    boolen = true;
                }else{
                    boolen = false;
                }
            }
            bufferedReader.close();
            fileReader.close();
            return boolen;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static Boolean getSafe(String safe_do)
    {
        /**
         * 参数: safe_do，用于探测需要获取什么数据
         */
        try
        {
            File file = new File("../../../rules/Security.cfg");
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
    public static Boolean ISUSER(String user,String pwd) throws Exception
    {
        File file = new File(ConfigRoot+"Admin.cfg");
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
    public static Boolean IsConfigTrue(String configTab,String configFile)
    {
        try
        {
            File file = new File(configFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Boolean bool = false;
            while ((line=bufferedReader.readLine())!=null)
            {
                int c = line.indexOf(configTab);
                if (c != -1)
                {
                    line = line.substring(line.indexOf(configTab)+configTab.length(),line.indexOf(";"));
                    line = line.replace(" ","");
                    if (line.equals("true"))
                    {
                        bool = true;
                    }else {
                        bool = false;
                    }
                    break;
                }else {
                    bool = false;
                }
            }
            bufferedReader.close();
            return bool;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static int GetServerPort() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try{
                    File file = new File(ConfigRoot+"Server.cfg");
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String tmp;
                    int re = 0;

                    while ((tmp=bufferedReader.readLine())!=null)
                    {
                        int start = tmp.indexOf("Port: ");
                        if (start != -1)
                        {
                            int end = tmp.indexOf(";1");
                            re = Integer.valueOf(tmp.substring(start+6,end));
                            break;
                        }
                    }
                    bufferedReader.close();
                    return re;
                }catch (Exception e)
                {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        executorService.shutdown();
        return future.get();
    }
}
