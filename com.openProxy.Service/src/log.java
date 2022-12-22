
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class log {
    public static void Writer(String content) {
        try {
            File target = new File(log.getLogDir());
            if (!target.isDirectory() || !target.exists()) {
                target.mkdir();
            }
            FileWriter fileWriter = new FileWriter(target.getAbsoluteFile()+"/Proxy-"+log.getLogDir()+".log");
            fileWriter.write(content);
            fileWriter.close();
        }catch (Exception exception){}
    }
    public static String getLogDir() {
        try{
            File config = new File("/usr/LinWinHttp/config/log.cfg");
            FileReader fileReader = new FileReader(config);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String getLine = bufferedReader.readLine();
            return getLine;
        }catch (Exception exception){
            return null;
        }
    }
    public static String GetLogTime()
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
