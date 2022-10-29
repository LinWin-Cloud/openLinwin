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
    public static void strict_origin_when_cross_origin(PrintWriter printWriter, String HttpURL, Socket socket) throws Exception
    {
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
                printWriter.println("HTTP/1.1 200 OK");
                printWriter.println("strict-origin-when-cross-origin:"+line);
                printWriter.println("Server:LinWin Http Server/1.0");
                printWriter.println("Content-type:"+Client.GetType(socket,main.GetServerPath() + HttpURL));
                printWriter.flush();

                bufferedReader.close();
                fileReader.close();
                break;
            }
        }
    }
}
