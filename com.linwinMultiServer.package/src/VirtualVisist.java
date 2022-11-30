
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class VirtualVisist {
    public static String[] fileContent = new String[0];
    public static String[] filePath = new String[0];

    public static String getPageContent_Web(String name) throws Exception {
        File file = new File(name);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        String tmpLine = "";
        while ((line=bufferedReader.readLine())!=null) {
            tmpLine = tmpLine + line;
        }
        return tmpLine;
    }
    public static void ServerFile_Web() throws Exception {

        String serverPath = MultiServer.ServerDir;
        VirtualVisist.getDirContent(serverPath);
    }
    public static void getDirContent(String file) throws Exception {
        File file1 = new File(file);
        File[] files = file1.listFiles();
        for (int i=0;i<files.length;i++) {
            if (files[i].isFile()&&files[i].exists()) {
                String fileName = files[i].getName();
                String[] last = {".html",".htm",".css",".js",".txt",".json"};
                for (int a=0;a<last.length;a++) {
                    if (fileName.lastIndexOf(last[i])!=-1) {
                        List<String> list = new ArrayList<>();
                        for (int b=0;b<VirtualVisist.filePath.length;b++) {
                            list.add(VirtualVisist.filePath[b]);
                        }
                        list.add(files[i].getAbsolutePath());
                        VirtualVisist.filePath = list.toArray(new String[list.size()]);

                        VirtualVisist.addFileContent();
                    }
                }
            }
            if (files[i].isDirectory()&&files[i].exists()) {
                VirtualVisist.getDirContent(files[i].getAbsolutePath());
            }
        }
    }
    public static void addFileContent() throws Exception {
        List<String> list = new ArrayList<>();

        List<String> list1 = new ArrayList<>();
        for (int i =0;i < VirtualVisist.filePath.length;i++) {
            FileReader fileReader = new FileReader(VirtualVisist.filePath[i]);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String tmpLine = "";
            while ((line=bufferedReader.readLine())!=null) {
                tmpLine = tmpLine + line;
            }
            list1.add(tmpLine);
        }
        VirtualVisist.filePath = list.toArray(new String[list.size()]);
        VirtualVisist.fileContent = list.toArray(new String[list1.size()]);
    }
}
