
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.WatchService;
import java.util.Scanner;

public class wsAttacker {

    public static String configRoot = "../config/";

    public static void main(String[] args) {
        try {
            File file = new File(wsAttacker.configRoot+"logo.txt");
            FileReader fileReader = new FileReader(file);

            //read the logo boo file.
            //ws-attacker.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line=bufferedReader.readLine())!=null) {
                System.out.println(line);
            }
            bufferedReader.close();
            fileReader.close();
            String Version = "";
            try {
                //read the version information.
                File file1 = new File(wsAttacker.configRoot+"Version.txt");
                FileReader fileReader1 = new FileReader(file1);
                BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
                System.out.println("Version: "+bufferedReader1.readLine());
                System.out.println("Enter: 'show options' to get the options and help.");
                System.out.println("Enter: 'exit' to exit the console.");
                bufferedReader1.close();
                fileReader1.close();

                //then into the command UI.
                wsAttacker.commandOprint();
            }catch (Exception exception) {
                //do not find the config file.
                System.out.println("[Error] Do not find config file: Version.txt");
                System.exit(0);
            }

        }catch(Exception exception) {
            System.out.println("[Error] Do not find config file: logo.txt");
        }
    }
    public static void commandOprint() {
        System.out.println("");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("WS-Attacker_> ");
            String getCommand = scanner.nextLine();
            if (getCommand.equals("exit")) {
                System.exit(0); //exit the console.
            }
            if (getCommand.equals("show options")) {
                wsAttacker.fileRead(wsAttacker.configRoot+"options.txt");
            }
            if (getCommand.equals("use payload web_test")) {
                webSiteTest.webTest();
            }
        }
    }
    public static void fileRead(String name) {
        //to read this file's content.
        try {
            File file = new File(name);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line=bufferedReader.readLine())!=null) {
                System.out.println(line);
            }
            bufferedReader.close();
            fileReader.close();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
