import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class UsingAPI {
    public static void main(String[]args) throws Exception {
        while (true)
        {
            Socket socket = new Socket("127.0.0.1",10143);
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            printWriter.println("CLIENT='URL'");
            printWriter.flush();
            System.out.println(bufferedReader.readLine());
            //socket.close();
            Thread.sleep(100);
        }
    }
}
