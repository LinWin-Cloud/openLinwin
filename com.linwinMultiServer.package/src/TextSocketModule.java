
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.*;

public class TextSocketModule {

    public static String TextSocket_Dir = "/tmp/openLinwin/";
    public static String Message = "";

    public TextSocketModule() {
        File file = new File(this.TextSocket_Dir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }
        File[] files = file.listFiles();
        for (int i =0; i < files.length ;i++) {
            files[i].delete();
        }
    }
    public void SendTextSocket(String Message)
    {
        try{
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            Future<Integer> future = executorService.submit(new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    TextSocketModule.SocketDO();
                    return 0;
                }
            });
            executorService.shutdown();
        }catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
    private static void SocketDO() {
        try
        {
            File TextSocket = new File(TextSocketModule.TextSocket_Dir + "Message.txt");
            TextSocket.createNewFile();
            FileWriter fileWriter = new FileWriter(TextSocket);
            fileWriter.write(TextSocketModule.Message);
            fileWriter.close();

        }catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
