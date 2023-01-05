
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.concurrent.*;

class HttpService {

    private String HttpMethod = "GET";
    private String HttpUrl = "";
    private HttpService httpService;

    public String getHttpUrl()
    {
        return this.HttpUrl;
    }
    public String getHttpMethod()
    {
        return this.HttpMethod;
    }
    public HttpService getHttpService()
    {
        return this.httpService;
    }

    public void sendFile(String path,int code,PrintWriter printWriter,Socket socket,OutputStream outputStream) throws Exception {

                printWriter.println(MultiServer.httpVersion+" "+code+" OK");
                printWriter.println("Content-Type: "+HTTPClientType.GetType(socket,path));
                printWriter.println("Server: openLinwin/"+ MultiServer.Version);
                printWriter.println("Length: "+new File(path).length());
                printWriter.println();
                printWriter.flush();

                FileInputStream fileInputStream = new FileInputStream(path);
                // 获取文件相应的channel，channel中会有相关数据
                FileChannel channel = fileInputStream.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                int length = 0;
                while ((length = channel.read(buffer)) != -1)
                {
                    byte[] bytes = buffer.array();
                    buffer.clear();
                    outputStream.write(bytes);
                    outputStream.flush();
                }

                channel.close();
                outputStream.close();
                socket.close();
                fileInputStream.close();
    }
    public void sendDirectory(String path,int code,PrintWriter printWriter,Socket socket,OutputStream outputStream) {
        try
        {
            File file = new File(path);

            /**
             * Index the Index.html or other Index HTML files in the config file.
             * Author : openLinwin
             * v2.3 Version Using the HashMap And Virtual Visit to read the Index File and do not
             * Read the disk.
             */

            String Index = VirtualVisist.VirtualList_IndexHTML.get(file.getAbsolutePath().replace("//","/")+"/");
            //System.out.println(file.getAbsolutePath().replace("//","/")+"/");
            if (Index != null)
            {
                printWriter.println(MultiServer.httpVersion+" "+code+" OK");
                printWriter.println("Content-Type: "+HTTPClientType.GetType(socket,path));
                printWriter.println("Server: openLinwin/"+ MultiServer.Version);
                printWriter.println("Length: "+new File(path).length());
                printWriter.println();
                printWriter.flush();

                printWriter.println(Index);
                printWriter.flush();
                socket.close();
            }

            for (int i = 0 ; i < MultiServer.defaultPage_str.length ; i++)
            {
                File file1 = new File(path+"/"+MultiServer.defaultPage_str[i]);
                if (file1.exists() && file1.isFile())
                {
                    try{
                        this.sendFile(file1.getAbsolutePath(),200,printWriter,socket,outputStream);
                    }catch (Exception exception)
                    {
                        this.sendErrorPage(200,printWriter,socket,outputStream);
                    }
                    break;
                }
            }
            File[] ListFiles = file.listFiles();

            printWriter.println(MultiServer.httpVersion+" "+code+" OK");
            printWriter.println("Content-Type: text/html");
            printWriter.println("Server: openLinwin/"+ MultiServer.Version);
            printWriter.println("Length: "+new File(path).length());
            printWriter.println();
            printWriter.flush();

            printWriter.println("<h3>IndexOF: "+path.substring(path.indexOf(MultiServer.ServerDir)+MultiServer.ServerDir.length())+"</h3>");
            printWriter.println("<a href='../'>Back</a><br /><br />");
            for (int i = 0 ; i < ListFiles.length ; i ++)
            {
                if (ListFiles[i].isDirectory()) {
                    printWriter.println("<a href='./"+ListFiles[i].getName()+"/'>* Directory -- "+ListFiles[i].getName()+"</a><br />");
                    printWriter.println();
                    printWriter.flush();
                }
            }
            for (int i = 0 ; i < ListFiles.length ; i ++)
            {
                if (ListFiles[i].isFile()) {
                    printWriter.println("<a href='./"+ListFiles[i].getName()+"'>* File -- "+ListFiles[i].getName()+"</a><br />");
                    printWriter.println();
                    printWriter.flush();
                }
            }
            printWriter.println("<div style='width:90%;height:3px;background-color:black></div>'");
            printWriter.println("OpenLinwin/"+MultiServer.Version);
            printWriter.flush();
            printWriter.flush();
            printWriter.close();
            socket.close();

        }catch (Exception exception) {

        }
    }
    public void sendErrorPage(int code,PrintWriter printWriter,Socket socket,OutputStream outputStream) {
        try
        {
            this.sendFile(MultiServer.errorPage+"/"+code+".html",code,printWriter,socket,outputStream);
        }
        catch (Exception exception){
            return;
        }
    }
    public void Service(Socket socket) throws Exception {

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        PrintWriter printWriter = new PrintWriter(outputStream);

        if (WebSafety.BlackIP(socket.getInetAddress().toString())) {
           sendErrorPage(403,printWriter,socket,outputStream);
           return;
        }

        String httpUrl = bufferedReader.readLine();
        httpUrl = java.net.URLDecoder.decode(httpUrl,"UTF-8");

        String httpMethod = httpUrl.substring(0,httpUrl.indexOf(" "));
        httpUrl = httpUrl.substring(httpUrl.indexOf(" ")+1,httpUrl.lastIndexOf("HTTP/")-1);

        this.HttpMethod = httpMethod;
        this.HttpUrl = httpUrl;
        try
        {
            String Http = httpUrl;

            WebSafety.XSS_Security(Http,printWriter,outputStream,socket,MultiServer.Version);
            WebSafety.SQL_Security(Http,printWriter,outputStream,socket,MultiServer.Version);

            if (Http == null) {
                socket.close();
            }
            String path = MultiServer.ServerDir + Http;
            path = path.replace("//", "/");
            // System.out.println(path+";");

            File RequestsPath = new File(path);
            if (RequestsPath.exists() && RequestsPath.isFile()) {
                sendFile(path, 200, printWriter, socket, outputStream);
            } else if (RequestsPath.exists() && RequestsPath.isDirectory()) {
                sendDirectory(path, 200, printWriter, socket, outputStream);
            } else {
                sendErrorPage(404, printWriter, socket, outputStream);
            }
            bufferedReader.close();
        }
        catch (Exception exception){
        }
    }
}

public class WebServer {
    private static int EXE_Boot = 0;

    public static int Start_Test = 0;
    private String httpUrl = null;
    private String httpMethod = "GET";
    private HttpService httpService = null;
    private Socket socket = null;
    public String getHttpUrl() {
        return this.httpUrl;
    }
    public String getHttpMethod() {
        return this.httpMethod;
    }
    public HttpService getHttpService() {
        return this.httpService;
    }
    public Socket getSocket()
    {
        return this.socket;
    }

    public static void mainWebServer() throws Exception
    {
        WebServer.getServerSocket(MultiServer.ServerPort);
        ServerSocket serverSocket = new ServerSocket(MultiServer.ServerPort);
        serverSocket.setPerformancePreferences(1,5,10);
        serverSocket.setReceiveBufferSize(64 * 1024 * 1024);
        for (int i = 0 ; i < 16 ; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true)
                    {
                        try {
                            Socket socket = serverSocket.accept();
                            socket.setSoTimeout(500);
                            APIService apiService = new APIService();
                            apiService.apiKeyRun();

                            HttpService httpService = new HttpService();
                            WebServer.FutureEXE(httpService, socket);
                        }
                        catch (Exception exception) {
                        }
                    }
                }
            });
            thread.start();
        }
    }
    public static void FutureEXE(HttpService httpService,Socket socket) throws Exception {
        socket.setTcpNoDelay(false);
        httpService.Service(socket);
    }
    public static Boolean getServerSocket(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
        }
        catch (Exception exception) {
            WebServer.Start_Test = WebServer.Start_Test + 1;
            try {
                if (WebServer.Start_Test == 5) {
                    System.exit(0);
                }
                Thread.sleep(200);
                WebServer.getServerSocket(port);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
