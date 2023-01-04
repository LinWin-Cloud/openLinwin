import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.*;


public class WebServer {

    public static int Start_Test = 0;

    public static void mainWebServer() throws Exception
    {
        WebServer.getServerSocket(MultiServer.ServerPort);
        ServerSocket serverSocket = new ServerSocket(MultiServer.ServerPort);

        for (int a = 0 ; a < 16 ; a++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    Socket socket = null;
                    while (true) {
                        try
                        {
                            socket = serverSocket.accept();
                            socket.setKeepAlive(true);
                            socket.setSoTimeout(700);
                            WebServer.ServerHTTP(socket);
                        }
                        catch (Exception exception) {
                            try {
                                socket.close();
                            }catch (Exception exception1) {
                                exception1.printStackTrace();
                            }
                        }
                    }
                }
            });
            thread.start();
        }

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
    public static void ServerHTTP(Socket socket) throws IOException
    {
        try
        {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter pWriter = new PrintWriter(outputStream);

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
                WebSafety.SQL_Security(DoWithURL, pWriter, outputStream, socket, MultiServer.Version);
                WebSafety.XSS_Security(DoWithURL, pWriter, outputStream, socket, MultiServer.Version);

                //String getIP = String.valueOf(socket.getInetAddress()).replace("/", "");
                //if (MultiServer.IPBlack.get(getIP) != null) {
                  //  socket.close();
                //}
                WebServer.Send_Dir_And_File_To_Client(printWriter, socket, DoWithURL, outputStream,inputStream);
            }
            bufferedReader.close();
            inputStream.close();
        }
        catch (Exception exception) {}
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
                pWriter.println(MultiServer.httpVersion+" 200 OK");
                pWriter.println("Content-Type:"+HTTPClientType.GetType(socket, MultiServer.ServerDir+HTTPURL));
                pWriter.println("Server: openLinwin/"+MultiServer.Version);
                pWriter.println("strict-origin-when-cross-origin: "+MultiServer.strict_origin_when_cross_origin);
                pWriter.println("Date: "+WebClient.GetNowTime());
                pWriter.println("Length: "+file.length());
                pWriter.println();
                pWriter.flush();
                // send the page to the web client.


                //String putPath = MultiServer.ServerDir+HTTPURL;
                //putPath = putPath.replace("//","/");

                /*
                                String indexText = VirtualVisist.VirtualList.get(putPath);

                if (indexText != null && VirtualVisist.isText(new File(putPath)) && new File(putPath).exists()) {
                    pWriter.println(indexText);
                    pWriter.flush();
                    socket.close();
                }
                 */
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                FileInputStream fileInputStream = new FileInputStream(MultiServer.ServerDir + "/" + HTTPURL);

                int cap = 1024;
                FileChannel fileChannel = fileInputStream.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(cap);
                int length = -1;
                while ((length = fileChannel.read(byteBuffer)) != -1) {
                    byte[] bytes = byteBuffer.array();
                    byteBuffer.clear();
                    bufferedOutputStream.write(bytes,0,length);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                fileChannel.close();
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
         * openLinwin Server
         */
        if (file.exists()&&file.isDirectory())
        {
            try
            {
                pWriter.println(MultiServer.httpVersion+" 200 OK");
                pWriter.println("Content-Type:"+HTTPClientType.GetType(socket, MultiServer.ServerDir + "/" + HTTPURL));
                pWriter.println("Server: openLinwin/"+MultiServer.Version);
                pWriter.println("strict-origin-when-cross-origin: "+MultiServer.strict_origin_when_cross_origin);
                pWriter.println("Date: "+WebClient.GetNowTime());
                pWriter.println();
                pWriter.flush();

                /**
                 * if the default page and send it to the web client.
                 */
                File[] files = file.listFiles();

                for (int i=0; i < MultiServer.defaultPage_str.length ;i++) {
                    File index = new File(MultiServer.ServerDir+HTTPURL+"/"+MultiServer.defaultPage_str[i]);
                    String str_path = index.getAbsolutePath();
                    //System.out.println(str_path);

                    if (index.exists() && index.isFile()) {

                        String indexPage = VirtualVisist.VirtualList.get(str_path.replace("//","/"));
                        if (indexPage != null && index.exists())
                        {
                            pWriter.println(indexPage);
                            pWriter.flush();
                            socket.close();
                        }
                        FileInputStream fileInputStream = new FileInputStream(MultiServer.ServerDir+HTTPURL+"/"+MultiServer.defaultPage_str[i]);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                        int cap = 4096;
                        FileChannel fileChannel = fileInputStream.getChannel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(cap);
                        int length = -1;
                        while ((length = fileChannel.read(byteBuffer)) != -1) {
                            byteBuffer.clear();
                            byte[] bytes = byteBuffer.array();
                            bufferedOutputStream.write(bytes,0,length);
                            bufferedOutputStream.flush();
                        }
                        bufferedOutputStream.close();
                        /**
                         * BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                         int length = 0;
                         while ((length = fileInputStream.read(bytes)) != -1) {
                         bufferedOutputStream.write(bytes, 0, length);
                         }
                         bufferedOutputStream.flush();
                         bufferedOutputStream.close();
                         */
                        fileInputStream.close();
                        fileChannel.close();
                        socket.close();
                        break;
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
                pWriter.println("OpenLinwin/"+MultiServer.Version);
                pWriter.flush();
                socket.close();
                //socket.shutdownOutput();
            }
            catch(Exception exception)
            {
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
        pWriter.println(MultiServer.httpVersion+" "+statusCode+" OK");
        pWriter.println("Content-Type:text/html");
        pWriter.println("Server: OpenLinwin/"+MultiServer.Version);
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
