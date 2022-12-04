

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
            int port = Integer.valueOf(scanner3.nextLine());
            System.out.print("WS-Attacker / Web_Test_> Enter Test URL: ");
            Scanner scanner4 = new Scanner(System.in);
            String url = scanner4.nextLine();

            long getStartTime = System.currentTimeMillis();
            final long[] runTime = {0};
            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i < testAttackerNum ;i++) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i=0;i < testSendNum/testAttackerNum;i++) {
                                    webSiteTest.sendNum = webSiteTest.sendNum + 1;
                                    long getRunTime = System.currentTimeMillis() - getStartTime;
                                    runTime[0] = getRunTime;
                                    if(webSiteTest.sendNum-1 == testSendNum){
                                        System.out.println("Finish Test: "+String.valueOf(webSiteTest.sendNum-1)+" Requests");
                                        //start print the test information.
                                        System.out.println("[*] Test Result: ");
                                        System.out.println("1. Use Time: "+getRunTime+"ms");
                                        System.out.println("2. Send Requests Number: "+String.valueOf(webSiteTest.sendNum-1));
                                        System.out.println("3. Requests Url: "+url);
                                        System.out.println("4. Remote IP: "+domain);
                                        wsAttacker.commandOprint();
                                        break;
                                    }
                                    else {
                                        PrintStream printStream = new PrintStream(System.out);
                                        printStream.println("[!] Test: "+domain+" "+ webSiteTest.sendNum+" Use Time: "+getRunTime+"ms");
                                        printStream.flush();
                                        webSiteTest.Attack(testSendNum,domain,port,url,getRunTime);
                                    }
                                }
                            }
                        });
                        thread.start();
                    }
                }
            });
            mainThread.start();
            mainThread.join();
            System.out.println("Finish Test: "+String.valueOf(webSiteTest.sendNum-1)+" Requests");
            //start print the test information.
            System.out.println("[*] Test Result: ");
            System.out.println("1. Use Time: "+ runTime[0] +"ms");
            System.out.println("2. Send Requests Number: "+String.valueOf(webSiteTest.sendNum-1));
            System.out.println("3. Requests Url: "+url);
            System.out.println("4. Remote IP: "+domain);
            wsAttacker.commandOprint();
        }catch (Exception exception) {
            System.out.println("[!] Must enter a int number!");
            wsAttacker.commandOprint();
        }
    }
    public static void Attack(int sendNum,String domain,int port,String url,long getRunTime) {
        try {
            Socket socket = new Socket(domain,port);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println("GET "+url+" HTTP/1.1");
            printWriter.flush();
            socket.close();

        }catch (Exception exception) {
            System.out.println("Error: "+exception.getLocalizedMessage());
            //start print the test information.
            System.out.println("[*] Test Result: ");
            System.out.println("1. Use Time: "+getRunTime+"ms");
            System.out.println("2. Send Requests Number: "+String.valueOf(webSiteTest.sendNum-1));
            System.out.println("3. Requests Url: "+url);
            System.out.println("4. Remote IP: "+domain);
            wsAttacker.commandOprint();
        }
    }
}
