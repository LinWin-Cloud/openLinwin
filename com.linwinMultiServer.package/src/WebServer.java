
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
    public void sendDirectory(String path,int code,PrintWriter printWriter,Socket socket,OutputStream outputStream) throws IOException {
        try
        {
            File file = new File(path);

            /**
             * Index the Index.html or other Index HTML files in the config file.
             * Author : openLinwin
             * v2.3 Version Using the HashMap And Virtual Visit to read the Index File and do not
             * Read the disk.
             */

            int last1 = path.length() - 1 ;
            int end = path.length();
            String charStr = path.substring(last1,end);
            if (!charStr.equals("/"))
            {
                printWriter.println("HTTP/1.1 200 OK");
                printWriter.println("Content-Type: text/html");
                printWriter.println();
                printWriter.println("<script>window.location.href=window.location.href+'/';</script>");
            }

            //String Index = VirtualVisist.VirtualList_IndexHTML.get(file.getAbsolutePath().replace("//","/")+"/");
            //System.out.println(file.getAbsolutePath().replace("//","/")+"/");
            String Index = null;
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
            /**
             * If there do not have the Index file , then openLinwin will List all the files on this Dir
             */
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
            socket.close();
        }
    }
    public void sendErrorPage(int code,PrintWriter printWriter,Socket socket,OutputStream outputStream) {
        try
        {
            /**
             * Send Error page , as 400,404,405 and so on.
             * I was using the function: this.snedFile() , but there is a Bug in it.
             * So must write a New function restart.
             */
            printWriter.println("HTTP/1.1 "+code+" OK");
            printWriter.println("Content-Type: text/html");
            printWriter.println("Server: openLinwin/"+MultiServer.Version);
            printWriter.println();
            printWriter.flush();

            FileInputStream fileInputStream = new FileInputStream(MultiServer.errorPage+"/"+code+".html");
            int length = 0 ;
            byte[] bytes = new byte[1024];
            while ((length = fileInputStream.read(bytes)) != -1)
            {
                outputStream.write(bytes,0,length);
                outputStream.flush();
            }
            outputStream.close();
            printWriter.close();
            socket.close();
        }
        catch (Exception exception){
            try{
                socket.close();
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
            }
        }
    }
    public void Service(Socket socket,PrintWriter printWriter,OutputStream outputStream,BufferedReader bufferedReader,String httpUrl) throws Exception {

        if (WebSafety.BlackIP(socket.getInetAddress().toString())) {
           sendErrorPage(403,printWriter,socket,outputStream);
           return;
        }
        WebSafety.XSS_Security(httpUrl,printWriter,outputStream,socket,MultiServer.Version);
        WebSafety.SQL_Security(httpUrl,printWriter,outputStream,socket,MultiServer.Version);
        //System.out.println(httpUrl);
        //String httpMethod = httpUrl.substring(0,httpUrl.indexOf(" "));
        //httpUrl = httpUrl.substring(httpUrl.indexOf(" ")+1,httpUrl.lastIndexOf("HTTP/")-1);

        this.HttpUrl = httpUrl;
        try
        {
            String Http = httpUrl;

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
    public String httpUrl = null;
    public String httpMethod = "GET";
    public HttpService httpService = null;

    public Socket socket = null;
    public OutputStream outputStream = null;
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
    public OutputStream getOutputStream() {
        return this.outputStream;
    }


    public void set(Socket socket,OutputStream outputStream,String HttpUrl,String HttpMethod) {
        this.socket = socket;
        this.outputStream = outputStream;
        this.httpUrl = HttpUrl;
        this.httpMethod = HttpMethod;
    }

    public void mainWebServer() throws Exception
    {
        WebServer.getServerSocket(MultiServer.ServerPort);
        ServerSocket serverSocket = new ServerSocket(MultiServer.ServerPort);
        //serverSocket.setPerformancePreferences(1,5,10);
        //serverSocket.setReceiveBufferSize(64 * 1024 * 1024);
        for (int i = 0 ; i < 16 ; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true)
                    {
                        WebServer.runEXE(serverSocket);
                    }
                }
            });
            thread.start();
        }
    }
    public static void runEXE(ServerSocket serverSocket) {
        try {
            Socket socket = serverSocket.accept();

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            PrintWriter printWriter = new PrintWriter(outputStream);

            String httpUrl = bufferedReader.readLine();
            if (httpUrl == null){
                socket.close();
            }
            httpUrl = java.net.URLDecoder.decode(httpUrl,"UTF-8");

            String httpMethod = httpUrl.substring(0,httpUrl.indexOf(" "));
            httpUrl = httpUrl.substring(httpUrl.indexOf(" ")+1,httpUrl.lastIndexOf("HTTP/")-1);

            HttpService httpService = new HttpService();
            //WebServer webServer = new WebServer();
            //webServer.set(socket,outputStream,httpUrl,httpMethod);

            APIService apiService = new APIService();
            apiService.set(socket,httpUrl,httpMethod,outputStream,httpService);
            apiService.apiKeyRun();

            WebServer.FutureEXE(httpService, socket,printWriter,outputStream,bufferedReader,httpUrl);
        }
        catch (Exception exception) {
        }
    }
    public static void FutureEXE(HttpService httpService,Socket socket,PrintWriter printWriter,OutputStream outputStream,BufferedReader bufferedReader,String httpUrl) throws Exception {
        httpService.Service(socket,printWriter,outputStream,bufferedReader,httpUrl);
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
