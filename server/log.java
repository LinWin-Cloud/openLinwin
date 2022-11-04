

import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class log {

    final String[] logNum = new String[0];

    public static void write(String info) {
        /**
         * Write the log information to the config path,
         * Update: 2022.11.3
         */
        try {
            //File file = new File("../config/log.cfg");
            String WriteDIR = config.ReadLine("../config/log.cfg");
            File tar = new File(WriteDIR);
            if (!tar.exists()) {
                log.writeLine("[ERROR] Config Log Dir Is Not Find!");
            } else {
                if (tar.exists() && tar.isDirectory()) {
                    //File logs = new File(WriteDIR);
                    String time = config.GetLogTime();

                    File TargetLogFile = new File(WriteDIR + "/" + time + ".log");
                    if (!TargetLogFile.exists()) {
                        TargetLogFile.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(TargetLogFile.getAbsoluteFile(), true);
                    fileWriter.write("[" + config.GetNowTime() + "]" + " " + info + "\n");
                    fileWriter.close();
                } else {
                    System.out.println("[ERROR] Config Log Dir Is A File");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Boolean writeLine(String information)
    {
        /**
         * Print the log in the console, instead of 'System.out.println();'
         * use executorService to do this
         */
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                /**
                 * main the function to print the log information
                 */
                PrintStream printStream = new PrintStream(System.out);
                printStream.println(information);
                printStream.flush();
                return 0;
            }
        });
        executorService.shutdown();
        return true;
    }
}
