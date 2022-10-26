

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.text.html.HTML;

public class URL_Http {
    public static void URL_Return(Socket socket,String url)
    {
        try
        {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(outputStream);

            File file = new File("../rules/URL_Return.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader config_br = new BufferedReader(fileReader);

            String line;
            String configURL = "";
            String configPath = "";

            while ((line=config_br.readLine())!=null)
            {
                // get config url
                String tmp = line;
                configURL = tmp.substring(tmp.indexOf("URL: ")+5,tmp.indexOf(";"));
                if (configURL.equals(url))
                {
                    configPath = tmp.substring(tmp.indexOf("Return: ")+8,tmp.lastIndexOf(";"));
                    File server = new File(configPath);
                    if (server.exists())
                    {
                        main.SocketDIR(bufferedReader,outputStream,url,printWriter,server,socket);
                        break;
                    }else{
                        main.Page404(printWriter, url,outputStream,socket);
                    }
                }
            }
            socket.close();
            return;

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
