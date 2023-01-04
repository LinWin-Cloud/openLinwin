
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.concurrent.*;

public class VirtualVisist {
    public static HashMap<String,String> VirtualList = new HashMap<>();
    public static HashMap<String,byte[]> VirtualList_Byte = new HashMap<>();
    public static HashMap<String,String> VirtualList_IndexHTML = new HashMap<>();

    public static void load_Service_Dir(String Service) {
        File file = new File(Service);
        File[] files = file.listFiles();
        for (int i = 0 ; i < files.length ; i++)
        {
            if (files[i].isDirectory()) {
                VirtualVisist.load_Service_Dir(Service+"/"+files[i].getName());
                continue;
            }
            else {
                String path = files[i].getAbsolutePath().replace("//","/");
                try {
                    if (VirtualVisist.isText(new File(path))) {

                        ExecutorService executorService = Executors.newFixedThreadPool(1);
                        Future<Integer> future = executorService.submit(new Callable<Integer>() {
                            @Override
                            public Integer call() throws Exception {
                                FileReader fileReader = new FileReader(path);
                                BufferedReader bufferedReader = new BufferedReader(fileReader);
                                String line;
                                String addText = "";

                                while ((line = bufferedReader.readLine()) != null)
                                {
                                    addText = addText + line;
                                }
                                //System.out.println(path);
                                VirtualVisist.VirtualList.put(path,addText);
                                bufferedReader.close();
                                fileReader.close();
                                //System.out.println(VirtualVisist.VirtualList.get(path));
                                return 0;
                            }
                        });
                        executorService.shutdown();
                        continue;
                    }else {
                        ExecutorService executorService = Executors.newFixedThreadPool(1);
                        Future<Integer> future = executorService.submit(new Callable<Integer>() {
                            @Override
                            public Integer call() throws Exception {

                                return 0;
                            }
                        });
                        executorService.shutdown();
                        continue;
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
    }

    public static boolean isText(File file) {
        boolean isText = true;
        try {
            FileInputStream fin = new FileInputStream(file);
            long len = file.length();
            for (int j = 0; j < (int) len; j++) {
                int t = fin.read();
                if (t < 32 && t != 9 && t != 10 && t != 13) {
                    isText = false;
                    break;
                }
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isText;
    }
    public static void Index(String path)
    {
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0 ; i < files.length ; i++)
        {
            if (files[i].isDirectory()) {
                VirtualVisist.load_Service_Dir(path+"/"+files[i].getName());
                continue;
            }
            else {
                try {
                    for (int a = 0 ; i < MultiServer.defaultPage_str.length ; a++)
                    {
                        File Index = new File(path+"/"+MultiServer.defaultPage_str[a]);
                        if (Index.exists() && Index.isFile())
                        {
                            
                        }
                    }

                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
    }
}