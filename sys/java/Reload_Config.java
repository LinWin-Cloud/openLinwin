
import java.lang.Thread;
import java.net.*;
import java.net.Socket;
import java.io.*;

public class Reload_Config {
    public static void main(String[]args)
    {
        try {
            java.net.Socket socket = new Socket("127.0.0.1", GetServerPort());
            java.io.OutputStream outputStream = socket.getOutputStream();
            java.io.PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println("/linwin_http_boot_web_config_reload");
            printWriter.flush();
            java.io.InputStream inputStream = socket.getInputStream();
            java.io.BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                System.out.println(line);
            }
            socket.close();
            bufferedReader.close();
            inputStream.close();
            printWriter.close();
        }
        catch (Exception err)
        {
            System.out.println(err.getLocalizedMessage());
        }
    }
    public static int GetServerPort() {
        try {
            File file = new File("../../config/Server.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String tmp;
            int re = 0;
            while ((tmp = bufferedReader.readLine()) != null) {
                int start = tmp.indexOf("Port: ");
                if (start != -1) {
                    int end = tmp.indexOf(";1");
                    re = Integer.valueOf(tmp.substring(start + 6, end));
                    break;
                }
            }
            bufferedReader.close();
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

