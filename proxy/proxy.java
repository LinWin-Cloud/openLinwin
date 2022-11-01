
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.URL;

public class proxy {
    public static void main(String[]args)
    {
        System.out.println("[*] Start Proxy Server ... ... [Making In China]");
        File file = new File("../proxy_tmp/");
        if (file.isDirectory()&&file.exists())
        {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length ; i++)
            {
                files[i].delete();
            }
        }
        try
        {
	    ServerSocket serverSocket = new ServerSocket(Integer.valueOf(proxyConfig.ReadConfig("Port: ")));
            while (true)
            {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            proxy.Serverproxy(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void Serverproxy(Socket socket) throws IOException {
        /**
         * 开始获取代理对象
         */
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        String u = proxyConfig.ReadConfig("Proxy: ");
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream2 = socket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String httpURL = bufferedReader.readLine();
            httpURL = java.net.URLDecoder.decode(httpURL, "UTF-8");
            httpURL = httpURL.substring(httpURL.indexOf(" ") + 1, httpURL.lastIndexOf("HTTP/"));

            String HttpURL = httpURL;
            if (HttpURL.indexOf("linwin_http_boot_web_1234567890_qwertyuiop=") != -1)
            {
                int s  = HttpURL.indexOf("linwin_http_boot_web_1234567890_qwertyuiop=");
                int e = HttpURL.indexOf(";1234567890>>");
                String user = HttpURL.substring(s+"linwin_http_boot_web_1234567890_qwertyuiop=".length(),e);
                String pwd = HttpURL.substring(e+";1234567890>>".length(),HttpURL.length());
                System.out.println(user+" "+pwd);
                //读取配置
                if (proxyConfig.ISUSER(user,pwd))
                {
                    printWriter.println("HTTP/1.1 200 OK");
                    printWriter.println("Content-type:text/html");
                    printWriter.println("Server:LinWin Http Server/1.0");
                    printWriter.println();
                    printWriter.flush();
                    printWriter.println("Successful");
                    printWriter.flush();
                    socket.close();
                    System.exit(0);
                    return;
                }else {
                    printWriter.println("HTTP/1.1 200 OK");
                    printWriter.println("Content-type:text/html");
                    printWriter.println("Server:LinWin Http Server/1.0");
                    printWriter.println();
                    printWriter.flush();
                    printWriter.println("Error");
                    printWriter.flush();
                    socket.close();
                }
            }

            URL url;
            int responsecode;
            HttpURLConnection urlConnection;
            BufferedReader reader;
            String line;
            url = new URL(u + "/" + httpURL);

            File file = new File(httpURL);
            //System.out.println(file.getName());

            //System.out.println(httpURL);

            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-type:"+Client.GetType(httpURL));
            printWriter.println("Server:LinWin Http Server");
            printWriter.println("strict-origin-when-cross-origin: *");
            printWriter.println("");
            printWriter.flush();
            //打开URL
            urlConnection = (HttpURLConnection) url.openConnection();
            // "Mozilla/5.0 (X11; Linux x86_64; rv:102.0) Gecko/20100101 Firefox/102.0"
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:102.0) Gecko/20100101 Firefox/102.0");
            //获取服务器响应代码
            responsecode = urlConnection.getResponseCode();

            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            byte[] bytes1 = new byte[1024];
            int length = 0;
            FileOutputStream fileOutputStream = new FileOutputStream(proxy.MakeFilesHTTP(HttpURL));
            DataInputStream dataInputStream = new DataInputStream(urlConnection.getInputStream());

            while ((length = dataInputStream.read(bytes1,0,bytes1.length)) != -1)
            {
                fileOutputStream.write(bytes1,0,length);
                fileOutputStream.flush();
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            dataInputStream.close();

            //得到输入流，即获得了网页的内容

            if (responsecode == 404)
            {
                proxy.SendPage(404,printWriter,socket,httpURL,outputStream);
            }
            if (responsecode == 403)
            {
                proxy.SendPage(403,printWriter,socket,httpURL,outputStream);
            }
            else
            {
                /*
                byte[] bytes = new byte[1024];
                FileInputStream fis = new FileInputStream("../proxy_tmp/"+file.getName());
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                int len = 0;
                while ((len = fis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                    bos.flush();
                }
                bos.flush();
                bos.close();
                fis.close();
                socket.close();
                */

                File file2 = new File("../proxy_tmp/"+file.getName());
                proxy.SocketDIR(outputStream,httpURL,printWriter,file2,socket);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            printWriter.println();
            printWriter.flush();
            proxy.SendPage(500,printWriter,socket,proxyConfig.GetErrorPage()+"/500.html",outputStream);
            printWriter.flush();
            socket.close();
        }
    }
    public static void SendPage(int code,PrintWriter printWriter,Socket socket,String httpURL,OutputStream outputStream)
    {
        try
        {
            byte[] bytes = new byte[1024];
            FileInputStream fis = new FileInputStream(httpURL);
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
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    public static void SocketDIR (OutputStream outputStream,String HttpURL,PrintWriter printWriter, File pathURL, Socket socket) {
        try {
            if (pathURL.exists()) {
                File[] ServerRootPath = pathURL.listFiles();

                byte[] bytes = new byte[1024];
                FileInputStream fis = new FileInputStream(proxy.MakeFilesHTTP(HttpURL));
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
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static String MakeFilesHTTP(String HttpURL)
    {
        // 鉴于网络上很多引入文件不是通过在根目录下引入的，那么搞一个分文件夹的，符合需代理站点的引入特征
        String root = "../proxy_tmp/";
        String returnPath = root+"";
        String name = "";

        String make[] = HttpURL.split("/");

        int a = make.length;
        for (int i =0 ; i < make.length ; i++)
        {
            String tmp = returnPath;
            if (make.length == 0)
            {
                returnPath = root;
                break;
            }
            if (i == a - 1)
            {
                name = make[i];
            }
            else
            {
                File file = new File(tmp+make[i]);
                file.mkdir();
                returnPath = tmp+make[i];
            }
            System.out.println(make[i]);
        }
        System.out.println(returnPath);
        if (HttpURL.endsWith("/"))
        {
            returnPath = root+" ";
        }

        return returnPath+"/"+name;
    }
}
