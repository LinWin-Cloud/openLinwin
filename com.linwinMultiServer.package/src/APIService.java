public class APIService {
    public Runnable apiKeyRun()
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                WebServer webServer = new WebServer();
                HttpService httpService = webServer.getHttpService();
            }
        };
        return runnable;
    }
}
