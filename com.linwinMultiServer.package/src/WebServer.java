import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class WebServer {

    public static void mainWebServer() throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(MultiServer.ServerPort,1000000);
            while (true){
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    ExecutorService executorService = Executors.newFixedThreadPool(1);
                    Future<Integer> future = executorService.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            WebServer.ServerHTTP(socket);
                            return 0;
                        }
                    });
                    executorService.shutdown();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }
    public static void ServerHTTP(Socket socket) throws IOException
    {
        try
        {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String HTTPURL = bufferedReader.readLine();
            String HttpURL = HTTPURL;
            if (HTTPURL == null)
            {
                socket.close();
            }
            else
            {

                HTTPURL = java.net.URLDecoder.decode(HTTPURL,"UTF-8");
                String DoWithURL = HTTPURL.substring(HTTPURL.indexOf(" ")+1,HTTPURL.lastIndexOf("HTTP/")-1);

                            //exit the http server
            if (HttpURL.indexOf("linwin_http_boot_web_1234567890_qwertyuiop=") != -1)
            {
                try {
                    int s = HttpURL.indexOf("linwin_http_boot_web_1234567890_qwertyuiop=");
                    int e = HttpURL.indexOf(";1234567890>>");
                    String user = HttpURL.substring(s + "linwin_http_boot_web_1234567890_qwertyuiop=".length(), e);
                    String pwd = HttpURL.substring(e + ";1234567890>>".length(), HttpURL.length());
                    //读取配置
                    if (init.ISUSER(user, pwd)) {
                        MultiServer.sendAPI(printWriter, socket, "Successful");
                        System.exit(0);
                     } else {
                        MultiServer.sendAPI(printWriter, socket, "Error");
                    }
                }catch(Exception exception) {
                    WebServer.sendErrorPage(400, printWriter, socket, outputStream);
                }
            }

                PrintWriter pWriter = new PrintWriter(outputStream);
                WebSafety.SQL_Security(DoWithURL, pWriter, outputStream, socket, MultiServer.Version);
                WebSafety.XSS_Security(DoWithURL, pWriter, outputStream, socket, MultiServer.Version);
                for (int i =0;i < MultiServer.IPBlack.length ;i++) {
                    if (String.valueOf(socket.getInetAddress()).replace("/", "").equals(MultiServer.IPBlack[i])) {
                        socket.close();
                    }            
                }
                WebServer.Send_Dir_And_File_To_Client(printWriter, socket, DoWithURL, outputStream,inputStream);
            }
            bufferedReader.close();
            inputStream.close();
        }
        catch (Exception exception)
        {
            
        }
    }
    public static void Send_Dir_And_File_To_Client(PrintWriter pWriter,Socket socket,String HTTPURL,OutputStream outputStream,InputStream inputStream)
    {
        /**
         * If the url content is '/' return a Enter road Error.
         */
        File file = new File(MultiServer.ServerDir+HTTPURL);
        /**
         * If the path is a fil then run this function
         * send the file to the web client.
         */
        
        if (file.isFile()&&file.exists())
        {
            try
            {
                /*
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                Future<Integer> future = executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {

                        return 0;
                    }
                });
                executorService.shutdown();
                future.get();
                */
                pWriter.println("HTTP/1.1 200 OK");
                pWriter.println("Content-Type:"+HTTPClientType.GetType(socket, MultiServer.ServerDir+HTTPURL));
                pWriter.println("Server: LinWinHttp Server/"+MultiServer.Version);
                pWriter.println("strict-origin-when-cross-origin: "+MultiServer.strict_origin_when_cross_origin);
                pWriter.println("Date: "+WebClient.GetNowTime());
                pWriter.println("Accept-Encoding: gzip");
                pWriter.println();
                pWriter.flush();
                // send the page to the web client.

                FileInputStream fileInputStream = new FileInputStream(MultiServer.ServerDir + "/" + HTTPURL);
                byte[] bytes = new byte[1024];
                
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                //GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
                int length = 0;
                while((length = fileInputStream.read(bytes)) != -1)
                {
                    //gzipOutputStream.write(bytes,0,length);
                    bufferedOutputStream.write(bytes,0,length);
                    bufferedOutputStream.flush();
                }
                //gzipOutputStream.flush();
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                fileInputStream.close();
                socket.close();
            }
            catch (Exception exception)
            {
                //exception.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }
            }
        }
        /**
         * the path is dir and send all the files in the dir to the web client.
         * LinWin Http Server
         */
        if (file.exists()&&file.isDirectory())
        {
            try
            {
                pWriter.println("HTTP/1.1 200 OK");
                pWriter.println("Content-Type:"+HTTPClientType.GetType(socket, MultiServer.ServerDir + "/" + HTTPURL));
                pWriter.println("Server: LinWinHttp Server/"+MultiServer.Version);
                pWriter.println("strict-origin-when-cross-origin: "+MultiServer.strict_origin_when_cross_origin);
                pWriter.println("Date: "+WebClient.GetNowTime());
                pWriter.println();
                pWriter.flush();

                /**
                 * if the default page and send it to the web client.
                 */
                File[] files = file.listFiles();

                for (int i =0;i < files.length ;i++)
                {
                    if (files[i].isFile())
                    {
                        for (int a=0;a< MultiServer.defaultPage.length;a++)
                        {
                            if (files[i].getName().equals(MultiServer.defaultPage[a]))
                            {
                                        byte[] bytes = new byte[1024];
                                        FileInputStream fileInputStream = new FileInputStream(MultiServer.ServerDir + "/" + HTTPURL+"/"+files[i].getName());
                                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                                        int length = 0;
        
                                        while((length = fileInputStream.read(bytes)) != -1)
                                        {
                                            bufferedOutputStream.write(bytes,0,length);
                                            bufferedOutputStream.flush();
                                        }
        
                                        bufferedOutputStream.flush();
                                        bufferedOutputStream.close();
                                        fileInputStream.close();
                                        socket.close();
                                break;
                            }
                        }
                    }
                }
                /**
                 * send the Dir Content
                 */
                pWriter.println("<meta charset='utf-8'/>");
                pWriter.println("<h2>IndexOf: "+HTTPURL+"</h2>");
                pWriter.println("Last Dir: <a href='../'>Back To ../</a><br />");
                pWriter.println("Index Page: <a href='/'>Index</a>");
                pWriter.println("<div style='width:98%;height:2px;background-color:black'></div><br />");
                pWriter.flush();

                pWriter.println("<h3>Directory: </h3>");
                for (int i =0;i < files.length ;i++)
                {
                    if (files[i].isDirectory())
                    {
                        pWriter.println("<a href='"+files[i].getName()+"/"+"'> * Directory: "+files[i].getName()+"</a><br />");
                        pWriter.flush();
                    }
                }
                pWriter.println("<h3>Files:</h3>");
                for (int i =0;i < files.length ;i++)
                {
                    if (files[i].isFile())
                    {
                        pWriter.println("<a href='"+files[i].getName()+"'> * File: "+files[i].getName()+"</a><br />");
                        pWriter.flush();
                    }
                }
                pWriter.println("<br /><div style='width:98%;height:2px;background-color:black'></div>");
                pWriter.println("LinWin Http Server/"+MultiServer.Version);
                pWriter.flush();
                socket.close();
                //socket.shutdownOutput();
            }
            catch(Exception exception)
            {
                //exception.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }
            }
        }
        if (!file.exists())
        {
            try{
                //send the 404 page
                WebServer.sendErrorPage(404, pWriter, socket, outputStream);
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }
    public static void sendErrorPage(int statusCode,PrintWriter pWriter,Socket socket,OutputStream outputStream) {
        pWriter.println("HTTP/1.1 "+statusCode+" OK");
        pWriter.println("Content-Type:text/html");
        pWriter.println("Server: LinWin Http/"+MultiServer.Version);
        pWriter.println("strict-origin-when-cross-origin: "+MultiServer.strict_origin_when_cross_origin);
        pWriter.println("Date: "+WebClient.GetNowTime());
        pWriter.println();
        pWriter.flush();

        byte[] bytes = new byte[1024];
        FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(MultiServer.errorPage+"/"+statusCode+".html");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            int length = 0;
            while((length = fileInputStream.read(bytes)) != -1)
            {
                bufferedOutputStream.write(bytes,0,length);
                bufferedOutputStream.flush();
            }      
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileInputStream.close();
            socket.close();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }
}
