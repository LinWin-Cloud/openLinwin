import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class testHTTP {
    public static void main(String[]args)
    {
        final int[] b = {0};
        for (int i = 0 ; i <= 1000 ; i++)
        {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run()  {
                    try {
                        for (int a = 0 ; a <= 10000 ; a++)
                        {
                            b[0]++;
                            Socket socket = new Socket("127.0.0.1",8080);
                            OutputStream outputStream = socket.getOutputStream();
                            PrintWriter printWriter = new PrintWriter(outputStream);
                            printWriter.println("GET / HTTP/1.1");
                            printWriter.flush();
                            System.out.println("Test: "+a+" for http://127.0.0.1:8080/ ; Thread: "+ b[0]);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}
