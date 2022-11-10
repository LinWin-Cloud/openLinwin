
import java.io.*;
import java.net.Socket;

public class Exit_Server {
    public static void main(String[]args)
    {
        Thread exit_http = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("127.0.0.1",Exit_Server.GetServerPort());
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    InputStream inputStream = socket.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    printWriter.println("GET /linwin_http_boot_web_1234567890_qwertyuiop="+Exit_Server.GetUser()+";1234567890>>"+Exit_Server.GetPwd()+" HTTP/1.0");
                    printWriter.flush();

                    String line;
                    while ((line=bufferedReader.readLine())!=null)
                    {
                        System.out.println(line);
                    }
                    socket.close();

                } catch (Exception e) {
                    System.out.println("[!] DO Not Start The Http Server: "+e.getLocalizedMessage());
                }
            }
        });
        Thread exit_proxy = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("127.0.0.1", Integer.parseInt(Exit_Server.Proxy_ReadConfig("Port: ")));
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    InputStream inputStream = socket.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    printWriter.println("GET linwin_http_boot_web_1234567890_qwertyuiop="+Exit_Server.GetUser()+";1234567890>>"+Exit_Server.GetPwd()+" HTTP/1.0");
                    printWriter.flush();

                    String line;
                    while ((line=bufferedReader.readLine())!=null)
                    {
                        System.out.println(line);
                    }
                    socket.close();
                } catch (Exception e) {
                    System.out.println("[!] DO Not Start The Proxy Server: "+e.getLocalizedMessage());
                }
            }
        });
        exit_http.start();
        exit_proxy.start();
    }
    public static String GetPwd() throws Exception
    {
        File file = new File("../config/Admin.cfg");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String pwd = " ";
        while ((line=bufferedReader.readLine())!=null)
        {
            if (line.indexOf(";Pwd: ")!=-1){
                pwd = line.substring(line.indexOf(";Pwd: ")+";Pwd: ".length(),line.lastIndexOf(";"));
            }
        }
        bufferedReader.close();
        return pwd;
    }
    public static String GetUser() throws Exception
    {
        File file = new File("../config/Admin.cfg");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String pwd = " ";
        while ((line=bufferedReader.readLine())!=null)
        {
            if (line.indexOf("User: ")!=-1){
                pwd = line.substring(line.indexOf("User: ")+"User: ".length(),line.indexOf(";"));
            }
        }
        bufferedReader.close();
        return pwd;
    }
    public static int GetServerPort() {
        try {
            File file = new File("../config/Server.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String tmp;
            int re = 0;

            while ((tmp = bufferedReader.readLine()) != null) {
                int start = tmp.indexOf("Port: ");
                if (start != -1) {
                    int end = tmp.indexOf(";1");
                    re = Integer.valueOf(tmp.substring(start + 6, end));
                    break;
                }
            }
            bufferedReader.close();
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static String Proxy_ReadConfig(String config)
    {
        try
        {
            File file = new File("../config/proxy.cfg");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line=bufferedReader.readLine())!=null)
            {
                int c = line.indexOf(config);
                if (c != -1)
                {
                    line = line.substring(c+config.length(),line.indexOf(";"));
                    break;
                }
            }
            line = line.replace(" ","");
            bufferedReader.close();
            return line;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
