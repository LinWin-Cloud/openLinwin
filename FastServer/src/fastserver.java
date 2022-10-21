

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class fastserver extends Thread {
    public static void main(String[]args) throws Exception
    {
        int cmd_len = args.length;
        //System.out.println(cmd_len);
        if (cmd_len == 0)
        {
            fastserver.ReadAllFile("../../config/help/fastserver.txt");
            return;
        }
        else {
            if (cmd_len == 2)
            {
                String c = args[0];
                if (c.equals("httpserver"))
                {
                    //String StartPort = c.substring(c.indexOf("httpserver")+"httpserver ".length(),c.length()) ;
                    String StartPort = args[1];
                    StartPort = StartPort.replace(" ","");
                    int Port = Integer.valueOf(StartPort);
                    ServerSocket serverSocket = new ServerSocket(Port);
                    System.out.println("[Info] Start Http Server On Port: "+serverSocket.getLocalSocketAddress());
                    while (true)
                    {
                        Socket socket = serverSocket.accept();
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    fastserver.HttpServer(socket);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                    }
                } else
                {
                    fastserver.ReadAllFile("/usr/LinWinHttp/config/help/fastserver.txt");
                }
            }else {
                fastserver.ReadAllFile("/usr/LinWinHttp/config/help/fastserver.txt");
            }
        }
    }
    public static void HttpServer(Socket socket) throws IOException {
        try
        {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(outputStream);

            String HttpURL = bufferedReader.readLine();
            HttpURL = java.net.URLDecoder.decode(HttpURL,"UTF-8");
            String URL = HttpURL.substring(HttpURL.indexOf(" "),HttpURL.lastIndexOf("HTTP/")-1);
            String workDir = fastserver.GetWorkPath();


            System.out.println(" ["+ socket.getInetAddress()+"] "+HttpURL);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-type:text/html");
            printWriter.println("Server:LinWin Http Server/1.0");
            printWriter.println();
            printWriter.flush();

            if (fastserver.IsDefault(new File(workDir+URL).getName()))
            {
                byte[] bytes = new byte[1024];

            }


            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            socket.close();
        }
    }
    public static void ReadAllFile(String name)
    {
        File file = new File(name);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                System.out.println(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static String GetWorkPath()
    {
        return System.getProperty("user.dir");
    }
    public static Boolean IsDefault(String name)
    {
        if (name.equals("index.htm")||name.equals("index.html"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
