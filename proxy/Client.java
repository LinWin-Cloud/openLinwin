import java.io.BufferedReader;
import java.io.File;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Client {
    public static String GetURL(BufferedReader bufferedReader) throws Exception {
        return bufferedReader.readLine();
    }

    public static String GetType(String ExplorerPath) throws Exception
    {
        String tmp = " ";
        File file = new File(ExplorerPath);
        ExplorerPath = file.getName();
        String Lastname="";
        if (ExplorerPath.indexOf(".")!=-1)
        {
            Lastname = ExplorerPath.substring(ExplorerPath.lastIndexOf("."),ExplorerPath.length());

            if (ExplorerPath.equals("/")) {
                return "text/html";
            }
            if (Lastname.equals("/")) {
                return "text/html";
            }
            if (Lastname.equals(".png")) {
                //image pn
                return "image/png";
            }
            if (Lastname.equals(".jpeg") || Lastname.equals(".jpg")) {
                return "image/jpeg";
            }
            if (Lastname.equals(".svg") || Lastname.equals(".bmp") || Lastname.equals(".ico")) {
                return "image/" + Lastname.replace(".", "");
            }
            if (Lastname.equals(".gif")) {
                return "image/gif";
            }
            if (Lastname.equals(".xml")) {
                return "text/xml";
            }
            if (Lastname.equals(".txt")) {
                return "text/xml";
            }
            if (Lastname.equals(".js")) {
                return "application/javascript";
            }
            if (Lastname.equals(".css")) {
                return "text/css";
            }
            if (Lastname.equals(".json")) {
                return "application/json";
            }
            if (Lastname.equals(".html") || Lastname.equals(".htm")) {
                return "text/html";
            }
            if (Lastname.equals(".pdf")) {
                return "application/pdf";
            }
            if (Lastname.equals("")) {
                return "text/html";
            }
            if (Lastname.equals(".mp3") || Lastname.equals(".wav")) {
                return "audio/" + Lastname.replace(".", "");
            }
            if (Lastname.equals(".mp4")) {
                return "video/mp4";
            }
            else
            {
                return "text/html";
            }
        }
        else
        {
            return "text/html";
        }
    }
    public static String GetLastName(String ExplorerPath) throws Exception
    {
        int i = 0 ;
        File file = new File(ExplorerPath);
        String filename = file.getName();
        filename = filename.toLowerCase();
        String lastname;
        if (filename.lastIndexOf(".")!=-1)
        {
            lastname = filename.substring(filename.lastIndexOf("."),filename.length());
        }else{
            lastname = "";
        }
        return lastname;
    }
    public static String GetSystem(BufferedReader bufferedReader) throws Exception
    {
        String tmp = " ";
        for (int i = 1 ; i <= 6 ; i++)
        {
            tmp = bufferedReader.readLine();
        }
        tmp = tmp.replace("sec-ch-ua-platform: \"", "");
        tmp = tmp.substring(0, tmp.indexOf("\""));
        return tmp;
    }
    public static String GetContentType(BufferedReader bufferedReader)
    throws Exception
    {
        return null;
    }
}
