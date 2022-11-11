import java.io.*;
import java.net.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class HttpRequests {
    public static String RequestsHttp(String HTTPurl,String HTTPmethod) throws Exception{

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                URL url = new URL(HTTPurl);

                Socket socket = new Socket(url.getHost(),url.getPort());

                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("GET "+HTTPurl+" HTTP/1.1\n" +
                        "Host: "+url.getHost()+":"+url.getPort()+"\n" +
                        "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:105.0) Gecko/20100101 Firefox/105.0\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n" +
                        "Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2\n" +
                        "Accept-Encoding: gzip, deflate, br\n" +
                        "Connection: keep-alive\n" +
                        "Cookie: BD_UPN=133352; \n" +
                        "Upgrade-Insecure-Requests: 1\n" +
                        "Sec-Fetch-Dest: document\n" +
                        "Sec-Fetch-Mode: navigate\n" +
                        "Sec-Fetch-Site: cross-site\n" +
                        "Pragma: no-cache\n" +
                        "Cache-Control: no-cache\n");
                printWriter.flush();
                String line;
                while ((line=bufferedReader.readLine())!=null)
                {
                    System.out.println(line);
                }
                bufferedReader.close();
                socket.close();

                return null;
            }
        });
        return future.get();
    }
}
