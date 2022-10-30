import java.net.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class HttpRequests {
    public static String RequestsHttp(String HTTPurl) throws Exception{

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                URL url = new URL(HTTPurl);

                Socket socket = new Socket(url.getHost(),url.getPort());
                return null;
            }
        });
        return future.get();
    }
}
