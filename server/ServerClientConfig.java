import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerClientConfig  extends Thread {
    public static String strict_origin_when_cross_origin(PrintWriter printWriter, String HttpURL, Socket socket) throws Exception
    {
	String re = "";
        File file = new File(main.GetServerPath());
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
}
