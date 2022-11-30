import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BootMultiServer {
    public static void main(String[] args) {
        try {
			File file = new File("../../config/Multi-Server/");
			if (file.isDirectory()&&file.exists()) {
				//read all the json file in for th multi server.
				File[] files = file.listFiles();
				for (int i =0;i < files.length;i++) {
					//only read the json file.
					//String fileLastName = files[i].getName().substring(files[i].getName().lastIndexOf("."), files.length);
					String fileLastName = files[i].getName().substring(files[i].getName().lastIndexOf("."),files[i].getName().length());
					if (fileLastName.equals(".json")) {
						//is json file. read it.
						try {
							String getServerPort = BootMultiServer.readJson(files[i].getAbsolutePath(),"Server-Port");
							String getServerDir = BootMultiServer.readJson(files[i].getAbsolutePath(),"Index");
							System.out.println(getServerDir);
						}catch (Exception exception){
							exception.printStackTrace();
						}
					}
				}
			} else {
				//target path is not a dir.
				System.out.println("[Target path is a file.]");
			}
        } catch (Exception exception) {
			exception.printStackTrace();
        }
    }
	public static String readJson(String filePath,String value) {
		//read and get the json files content.
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			String jsonContent = "";
			
			while ((line=bufferedReader.readLine())!=null) {
				jsonContent = jsonContent + line;
			}
		}catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
}
