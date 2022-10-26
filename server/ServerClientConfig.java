import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerClientConfig  extends Thread {
    public static void strict_origin_when_cross_origin(PrintWriter printWriter) throws Exception
    {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                File file = new File(main.GetServerPath());
                File[] files = file.listFiles();
                for (int i = 0 ; i > files.length ; i++)
                {
                    String configfile = files[i].getName();
                    if (configfile.equals("strict_origin_when_cross_origin.txt"))
                    {
                        FileReader fileReader = new FileReader(files[i].getAbsoluteFile());
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String line;
                        while ((line=bufferedReader.readLine())!=null)
                        {
                            printWriter.println("strict-origin-when-cross-origin:"+line);
                            printWriter.flush();
                        }
                        bufferedReader.close();
                        fileReader.close();
                        break;
                    }
                }
                return 0;
            }
        });
    }
}
