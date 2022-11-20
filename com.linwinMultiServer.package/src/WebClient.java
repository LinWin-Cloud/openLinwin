import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebClient extends Thread {
    public static String strict_origin_when_cross_origin() throws Exception
    {
	    String re = "";
        File file = new File(MultiServer.ServerDir);
        File[] files = file.listFiles();
        for (int i = 0 ; i < files.length ; i++)
        {
            String configfile = files[i].getName();
            //System.out.println(configfile);
            if (configfile.equals("strict_origin_when_cross_origin.txt"))
            {
                FileReader fileReader = new FileReader(files[i].getAbsoluteFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();

                //System.out.println(line);
                re = line;

                bufferedReader.close();
                fileReader.close();
                break;
            }
        }
	return re;
    }
    public static String GetNowTime()
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
