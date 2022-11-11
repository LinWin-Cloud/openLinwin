
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.OutputStream;
/*
 * 这是一个专门用于处理API的线程，
 * 大概了原理类似于聊天软件的通信服务方式，
 * 服务端会将获取的数据发送给这个API处理端口，然后应用程序可以通过socket来长链接获取数据
 */
import java.io.PrintWriter;

public class API extends Thread {
    public static void APIServer() throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(config.API_Port());
        try
        {
            while (true){
                Socket socket1 = serverSocket.accept();
                OutputStream outputStream = socket1.getOutputStream();
                InputStream inputStream = socket1.getInputStream();
                PrintWriter printWriter = new PrintWriter(outputStream);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String getMES = bufferedReader.readLine();
                int mes_title = getMES.indexOf("GET_SERVER_URL='");
                int last_mes = getMES.lastIndexOf("'");

                int client_title = getMES.indexOf("CLIENT='");

                if (mes_title != -1) {
                    getMES = getMES.substring(mes_title + "GET_SERVER_URL='".length(), last_mes);
                }
                else{
                    printWriter.println("MESSAGE SEND ERROR");
                    printWriter.flush();
                    socket1.close();
                }
                System.out.println(getMES);
                socket1.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String API_Config(String api_do)
    {
        try
        {
            File file = new File("../config/Server.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String api_config="";
            while ((line=bufferedReader.readLine())!=null)
            {
                int config = line.indexOf(api_do);
                if (config != -1)
                {
                    api_config = line.substring(config+api_do.length(),line.indexOf(";"));
                    api_config = api_config.replace(" ","");
                    break;
                }else{
                    api_config = null;
                }
            }
            bufferedReader.close();
            return api_config;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
