import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class APIService {

    private String httpUrl = null;
    private String httpmethod = "GET";
    private OutputStream outputStream = null;
    private Socket socket = null;
    private HttpService httpService = null;
    public void set(Socket socket,String httpUrl,String httpmethod,OutputStream outputStream,HttpService httpService) {
        /**
         * This function is not server to you ,
         * You do not using this function .
         * only the openLinwin Service Software can use this function,
         * it was ti set all the value of a Socket Client Run.
         */
        this.socket = socket;
        this.httpmethod = httpmethod;
        this.httpUrl = httpUrl;
        this.outputStream = outputStream;
        this.httpService = httpService;
    }
    public Runnable apiKeyRun()
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try
                {
                    WebServer webServer =  MultiServer.webServer;
                    System.out.println(webServer.getHttpUrl());
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        };
        return runnable;
    }
}
