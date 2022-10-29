

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class log {
    public static void write(String info) {
        try{
            File file = new File("../config/log.cfg");
            String WriteDIR = config.ReadLine("../config/log.cfg");
            File tar = new File(WriteDIR);
            if (!tar.exists())
            {
                System.out.println("[ERROR] Config Log Dir Is Not Find!");
            }else{
                if (tar.exists()&&tar.isDirectory()){
                    File logs = new File(WriteDIR);
                    String time = config.GetLogTime();

                    File TargetLogFile = new File(WriteDIR+"/"+time+".log");
                    if (!TargetLogFile.exists()){
                        TargetLogFile.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(TargetLogFile.getAbsoluteFile(),true);
                    fileWriter.write("["+config.GetNowTime()+"]"+" "+info+"\n");
                    fileWriter.close();
                }else{
                    System.out.println("[ERROR] Config Log Dir Is A File");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
