

import java.io.*;
import java.net.Socket;


public class URL_Http {
    public static void URL_Return(Socket socket,String url,String version)
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
                        main.SocketDIR(bufferedReader,outputStream,url,printWriter,server,socket,version);
                        break;
                    }else{
                        main.Page404(printWriter, url,outputStream,socket,version);
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
    public static void Page500(PrintWriter printWriter,Socket socket,OutputStream outputStream) throws Exception
    {
        printWriter.println("HTTP/1.1 500 OK");
        printWriter.println("Content-type:text/html");
        printWriter.println("Server:LinWin Http Server");
        printWriter.println();
        printWriter.flush();
        byte[] bytes = new byte[1024];
        FileInputStream fis = new FileInputStream(config.GetErrorPage() + "/500.html");
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        int len = 0;
        while ((len = fis.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bos.flush();
        fis.close();
        socket.shutdownOutput();
        socket.close();
    }
}
