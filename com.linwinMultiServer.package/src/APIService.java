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
    private Socket getSocket(){
        /**
         * you can get the socket class type
         */
        return this.socket;
    }
    private OutputStream getOutputStream() {
        return this.outputStream;
    }
    private String getHttpUrl() {
        return this.httpUrl;
    }
    private String getHttpmethod() {
        return this.httpmethod;
    }
    private HttpService getHttpService() {
        return this.httpService;
    }
    public void apiKeyRun() throws Exception
    {
        /**
         * This function is your writing to the Server
         * You can deal all the Http Service on this function
         * openLinwin give some Class and function.
         * You can use output and socket to deal the client requests.
         */
    }
}
