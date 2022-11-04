
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class main {

    public static String[] IPBLACK = new String[0];
    public static String line = "";

    public static void main(String[]args) throws Exception
    {

        /**
         * 创建一个 serversocket, 用于提供HTTP服务
         */
        log.write("Start Http Server");
        //print the verions information

        /*
         * 读取IP黑名单配置
         */
        File file = new File("../rules/IpBlack.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        List<String> list = new ArrayList<String>();
        while ((line=bufferedReader.readLine())!=null)
        {
            list.add(line);            
        }
        String toline = list.toString();
        toline = toline.replace("[", "");
        toline = toline.replace("]", "");
        toline = toline.replace(" ", "");
        IPBLACK = toline.split(",");

        bufferedReader.close();

        String Version = config.ReadLine("../config/Version.txt");
        System.out.println("[LinWin Http Server: "+config.GetNowTime()+"] Version: "+ Version);
        System.out.println("[ Start ] Listen Clients ... ... [Make in China] ");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (config.IsConfigTrue("API_Start: ","../config/Server.cfg"))
                    {
                        API.APIServer();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        //int ThreadToDO = 10;
        ServerSocket serverSocket = new ServerSocket(main.GetServerPort());

        for (;;)
        {
            Socket socket = serverSocket.accept(); //阻塞线程监听
            /**
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line=bufferedReader.readLine())!=null)
                {
                    System.out.println(line);
                }
                bufferedReader.close();
             */
            ExecutorService executorService = Executors.newFixedThreadPool(10000);
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    try
                    {
                        /**
                         * 判断是否拒绝服务
                         */
                        if (WebSafety.BlackIP(socket.getInetAddress().toString().replace("/", ""), IPBLACK))
                        {
                            socket.close();
                        }
                        String GetURL = Client.GetURL(socket);
                        //log.writeLine(GetURL);
                        main.ServerRun(socket, GetURL,Version);
                    }
                    catch (Exception exception)
                    {
                        socket.close();
                    }
                    return 0;
                }
            });
            executorService.shutdown();
        }
    }
    /**
     * 获取需要服务的端口
     */
    public static int GetServerPort() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try{
                    File file = new File("../config/Server.cfg");
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String tmp;
                    int re = 0;

                    while ((tmp=bufferedReader.readLine())!=null)
                    {
                        int start = tmp.indexOf("Port: ");
                        if (start != -1)
                        {
                            int end = tmp.indexOf(";1");
                            re = Integer.valueOf(tmp.substring(start+6,end));
                            break;
                        }
                    }
                    bufferedReader.close();
                    return re;
                }catch (Exception e)
                {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        executorService.shutdown();
        return future.get();
    }
    public static String GetServerPath()
    {
        /**
         * 获取服务器的网页服务目录
         */
        try{
		File file = new File("../config/Server.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String tmp;
            String path = " ";
            while ((tmp=bufferedReader.readLine())!=null)
            {
                int start = tmp.indexOf("ServerDir: ");
                if (start != -1)
                {
                    int end = tmp.indexOf(";2");
                    path = tmp.substring(start+11,end);
                }
            }
            bufferedReader.close();
            return path;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static Boolean IsDefaultPage(String Page)
    {
        /**
         * 判断请求页面是否是默认页面
         */
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(10000);
            Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    File file = new File("../config/defaultPage.cfg");
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String tmp;
                    Boolean DePage = false;
                    while ((tmp=bufferedReader.readLine())!=null)
                    {
                        int defaults = tmp.indexOf(Page);
                        if (defaults != -1)
                        {
                            DePage = true;
                        }
                    }
                    bufferedReader.close();
                    //System.out.println(DePage);
                    return DePage;
                }
            });
            executorService.shutdown();
            return future.get();
        }catch (Exception e){
            return false;
        }
    }
    public static void page403(PrintWriter printWriter,Socket socket,OutputStream outputStream) throws Exception
    {
        //System.out.println("hello world");
        //不允许被访问
        //返回403
        printWriter.println("HTTP/1.1 403 OK");
        printWriter.println("Content-type:text/html");
        printWriter.println("Server:LinWin Http Server");
        printWriter.println();
        printWriter.flush();
        byte[] bytes = new byte[1024];
        FileInputStream fis = new FileInputStream(config.GetErrorPage() + "/403.html");
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        int len = 0;
        while ((len = fis.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bos.flush();
        fis.close();
        //socket.shutdownOutput();
        socket.close();
    }
    public static void ServerRun(Socket socket,String GetURL,String version) throws Exception {
        /**
         * 处理流 
         */
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

	    if (GetURL == null)
	    {
		    socket.close();
            		return;
	    }else {
            String HttpMedth = GetURL.substring(0, GetURL.indexOf(" "));
            String HttpURLs = GetURL.substring(GetURL.indexOf(" ") + 1, GetURL.lastIndexOf("HTTP/") - 1);
            String HttpURL = java.net.URLDecoder.decode(HttpURLs, "UTF-8");

                                /**
                     * 重新载入一些配置文件,这主要是减少IO操作的频次
                     */
                    if (HttpURL.lastIndexOf("linwin_http_boot_web_config_reload")!= -1)
                    {
                        /*
                         * 读取IP黑名单配置
                         */
                        File file = new File("../rules/IpBlack.txt");
                        FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader1 = new BufferedReader(fileReader);

                        List<String> list = new ArrayList<String>();

                        while ((line=bufferedReader1.readLine())!=null)
                        {
                            list.add(line);            
                        }     
                        String toline = list.toString();
                        toline = toline.replace("[", "");
                        toline = toline.replace("]", "");
                        toline = toline.replace(" ", "");
                        IPBLACK = toline.split(",");

                        printWriter.println("HTTP/1.1 201 OK");
                        printWriter.println("Content-Type:text/html");
                        printWriter.println();
                        printWriter.flush();
                        printWriter.println("Reload Successful");
                        printWriter.flush();
                        socket.close();
                        bufferedReader1.close();
                    }

                    //exit the http server
                    if (HttpURL.indexOf("linwin_http_boot_web_1234567890_qwertyuiop=") != -1)
                    {
                        int s = HttpURL.indexOf("linwin_http_boot_web_1234567890_qwertyuiop=");
                        int e = HttpURL.indexOf(";1234567890>>");
                        String user = HttpURL.substring(s + "linwin_http_boot_web_1234567890_qwertyuiop=".length(), e);
                        String pwd = HttpURL.substring(e + ";1234567890>>".length(), HttpURL.length());
                        //读取配置
                        if (config.ISUSER(user, pwd)) {
                            printWriter.println("HTTP/1.1 200 OK");
                            printWriter.println("Content-type:text/html");
                            printWriter.println("Server:LinWin Http Server/" + version);
                            printWriter.println();
                            printWriter.flush();
                            printWriter.println("Successful");
                            printWriter.flush();
                            socket.close();
                            System.exit(0);
                        } else {
                            printWriter.println("HTTP/1.1 200 OK");
                            printWriter.println("Content-type:text/html");
                            printWriter.println("Server:LinWin Http Server/" + version);
                            printWriter.println();
                            printWriter.flush();
                            printWriter.println("Error");
                            printWriter.flush();
                            socket.close();
                        }
                    }

            ExecutorService executorService = Executors.newFixedThreadPool(10000);
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {

                    WebSafety.SQL_Security(HttpURL, printWriter, outputStream, socket,version);
                    log.write("[ " + HttpMedth + " " + socket.getInetAddress() + " ] " + HttpURL);

                    if (API.API_Config("API_Start: ").equals("true")) {
                        Socket api_send = new Socket(API.API_Config("API_IP: "), config.API_Port());
                        PrintWriter api_pw = new PrintWriter(new OutputStreamWriter(api_send.getOutputStream()));
                        api_pw.println("SERVER_URL='" + HttpURL + "';CLIENT_IP='" + socket.getInetAddress() + "'");
                        api_pw.flush();
                        api_send.close();
                    }
                    return 0;
                }
            });
            executorService.shutdown();

            //判断索引页面，然后返回
            File file = new File(main.GetServerPath());
            //File[] ServerRootPath = file.listFiles();

            File TargetFile = new File(main.GetServerPath() + HttpURL);
            if (HttpURL.indexOf("<script") != -1 || HttpURL.indexOf("</") != -1 || HttpURL.indexOf("<link") != -1 || HttpURL.indexOf("/>") != -1 || HttpURL.indexOf("%2") != -1) {
                WebSafety.XSS_Security(HttpURL, printWriter, outputStream, socket,version);
                return;
            }
	    if (config.isURLOK(HttpURL)) {
                URL_Http.URL_Return(socket, HttpURL,version);
                return;
            }
            //判断是否允许访问链接
            if (config.DoNotVisit(HttpURL)) {
                main.page403(printWriter, socket, outputStream);
                return;
            }
            if (HttpURL.equals("/")) {
                // resource the config file
                main.SocketDIR(bufferedReader, outputStream, HttpURL, printWriter, file, socket,version);
            } else if (!TargetFile.exists()) {
                //文件不存在
                // 404
                main.Page404(printWriter, HttpURL, outputStream, socket,version);
                return;
            } else if (TargetFile.isDirectory()) {
                // resource the config file
                main.SocketDIR(bufferedReader, outputStream, HttpURL, printWriter, TargetFile, socket,version);
                return;
            } else if (TargetFile.isFile()) {
                // resource the config file
                
                main.SendPage(bufferedReader, "200", printWriter, HttpURL, outputStream, socket);
            }
        }
    }
    public static void Page405(PrintWriter printWriter,Socket socket,OutputStream outputStream,String HttpURL) throws Exception
    {
        //返回405
        printWriter.println("HTTP/1.1 405 OK");
        printWriter.println("Content-type:text/html");
        printWriter.println("Server:LinWin Http Server");
	printWriter.println("strict-origin-when-cross-origin:"+ServerClientConfig.strict_origin_when_cross_origin(printWriter, HttpURL, socket));
        printWriter.println();
        printWriter.flush();
        byte[] bytes = new byte[1024];
        FileInputStream fis = new FileInputStream(config.GetErrorPage() + "/405.html");
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        int len = 0;
        while ((len = fis.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bos.flush();
        fis.close();
        //socket.shutdownOutput();
        socket.close();
    }
    public static void SendPage(BufferedReader bufferedReader,String code,PrintWriter printWriter,String HttpURL,OutputStream outputStream,Socket socket)
    {
        //返回数据
        try
        {
            printWriter.println("HTTP/1.1 "+code+" OK");
	        printWriter.println("strict-origin-when-cross-origin:"+ServerClientConfig.strict_origin_when_cross_origin(printWriter, HttpURL, socket));
	        printWriter.println("Content-Type:"+Client.GetType(socket,main.GetServerPath()+HttpURL));
            printWriter.println();
            printWriter.flush();
            //System.out.println("[Method:"+HttpMedth+" "+config.GetNowTime()+"] Requests Url: "+HttpURL+" [200] ");
            byte[] bytes = new byte[30];
            FileInputStream fis = new FileInputStream(main.GetServerPath() +"/"+ HttpURL);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            bos.flush();
            bos.close();
            fis.close();
            socket.close();//return;
        }catch (Exception e)
        {
            try{
                URL_Http.Page500(printWriter,socket,outputStream);
                socket.close();
            }catch (Exception ioException){

            }
        }
    }
        public static void SocketDIR (BufferedReader bufferedReader,OutputStream outputStream,String HttpURL,PrintWriter printWriter, File pathURL, Socket socket,String version) {
            try {
		    printWriter.println("HTTP/1.1 200 OK");
		    printWriter.println("strict-origin-when-cross-origin:"+ServerClientConfig.strict_origin_when_cross_origin(printWriter, HttpURL, socket));
		    printWriter.println("Content-Type:"+Client.GetType(socket,main.GetServerPath()+HttpURL));
		    printWriter.println("server:LinWin Http Server/"+version);
                if (pathURL.exists()) {
                    File[] ServerRootPath = pathURL.listFiles();
                    printWriter.println();

                    printWriter.println("<meta charset='utf-8'/>");
                    printWriter.flush();

                    for (int i = 0; i < ServerRootPath.length; i++)
                    {
                        //检测是否索引页面
                        if (main.IsDefaultPage(ServerRootPath[i].getName())&&ServerRootPath[i].isFile()) {
                            byte[] bytes = new byte[1024];
                            FileInputStream fis = new FileInputStream(main.GetServerPath() +HttpURL + "/" + ServerRootPath[i].getName());
                            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                            int len = 0;
                            while ((len = fis.read(bytes)) != -1) {
                                bos.write(bytes, 0, len);
                            }
                            printWriter.flush();
                            bos.flush();
                            bos.close();
                            fis.close();
                            socket.close();
                            break;
                        }
                    }
                    //说明没有索引目录，返回目录下的文件
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            printWriter.println("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\">");
                            printWriter.println("<h1>Server Dir: "+HttpURL+"</h1>");
                            printWriter.println("<a href='../'>Return to last dir</a><br /><br />");
                            printWriter.println("<div style='width=100%;height:2px;background-color: black;'></div><br />");
                            printWriter.println("<style>.div1{width: 100%;height:auto;background-color:black;border-radius:5px;box-shadow:0px 0px 5px black}</style>");
                            printWriter.flush();
                            for (int i = 0; i < ServerRootPath.length; i++) {
                                if (ServerRootPath[i].isDirectory()) {
                                    //是目录
                                    printWriter.println("<div class='.div1' style='padding:-10px;margin-left:10px;border-left: 10px solid black'><a href='" + ServerRootPath[i].getName() + "/'> 目录: " + ServerRootPath[i].getName() + "</a></div>");
                                    printWriter.flush();
                                } else {
                                    printWriter.println("<div class='.div1' style='padding:-10px;margin-left:10px;border-left: 10px solid black'><a href='" + ServerRootPath[i].getName() + "'> 文件: " + ServerRootPath[i].getName() + "</a></div>");
                                    printWriter.flush();
                                }
                            }
                            printWriter.println("<br /><div style='width=100%;height:2px;background-color: black;'></div><br />");
                            printWriter.flush();
                            try {
                                socket.close();
                            } catch (IOException e) {
                                
                            }
                        }
                    });
                    thread.start();
                } else {
                    main.Page404(printWriter,HttpURL,outputStream,socket,version);
                }
            } catch (Exception e) {
                //printWriter.println("500 Error Error");
                //printWriter.flush();
                //System.out.println("Server Error: "+e.getMessage());
		        try{
                    URL_Http.Page500(printWriter,socket,outputStream);
			        //socket.close();
		        }catch(Exception ex) {
                    //ex.printStackTrace();
		    }
        }
    }
    public static void Page404(PrintWriter printWriter,String HttpURL,OutputStream outputStream,Socket socket,String version) throws Exception
    {
        File file = new File(main.GetServerPath() + HttpURL);
        if (!file.exists()) {
            //返回404
            printWriter.println("HTTP/1.1 404 Not Found");
            printWriter.println("Content-type:text/html");
            printWriter.println("Server:LinWin Http Server/"+version);
	    printWriter.println("strict-origin-when-cross-origin:"+ServerClientConfig.strict_origin_when_cross_origin(printWriter, HttpURL, socket));
            printWriter.println();
            printWriter.flush();
            byte[] bytes = new byte[1024];
            FileInputStream fis = new FileInputStream(config.GetErrorPage() + "/404.html");
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            bos.flush();
            fis.close();
            socket.close();
        }
    }
}
