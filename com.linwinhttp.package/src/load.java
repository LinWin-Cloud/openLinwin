import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class load {
    public static void runLoad()
    {
        Thread load_All = new Thread(new Runnable(){
            public void run()
            {
                try
                {
                    main.ServerDir = main.GetServerPath();
                    main.ErrorPage = config.GetErrorPage();
                    main.Version = config.ReadLine("../config/Version.txt");
                    main.IPBLACK = load.IPBLACK();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        load_All.start();
    }
    public static String[] IPBLACK() {
        /*
         * 读取IP黑名单配置
         */
        try
        {
            File file = new File("../rules/IpBlack.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            List<String> list = new ArrayList<String>();
            while ((line=bufferedReader.readLine())!=null)
            {
                list.add(line);
            }
            String toline = list.toString();
            toline = toline.replace("[", "");
            toline = toline.replace("]", "");
            toline = toline.replace(" ", "");

            bufferedReader.close();
            return toline.split(",");
        }catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
