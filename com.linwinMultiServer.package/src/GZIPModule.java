
import java.io.*;
import java.util.*;

public class GZIPModule {
    public static String[] UserAgent(InputStream inputStream,PrintWriter pWriter,OutputStream outputStream) {
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String tmp;
            //List<String> list = new ArrayList<>();
            while ((tmp=bufferedReader.readLine())!=null) {
                System.out.println(tmp);
                //list.add(tmp);
            }
            bufferedReader.close();
            inputStream.close();
            return new String[0];
            //return list.toString().replace("[", "").replace("]", "").split(",");
        }catch(Exception exception) {
            return null;
        }
    }
}
