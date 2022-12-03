import java.io.*;
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
							int I = i;
							Thread thread = new Thread(new Runnable() {
								@Override
								public void run() {
									String getServerPort = BootMultiServer.readJson(files[I].getAbsolutePath(),"Server-Port");
									String getServerDir = BootMultiServer.readJson(files[I].getAbsolutePath(),"Index");
									//System.out.println(getServerDir+" "+getServerPort);
									try {
										//using system command to start the multi server.
										Process process = Runtime.getRuntime().exec("/usr/LinWinHttp/sys/BootMultiServerVM.sh "+getServerDir+" "+getServerPort);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
							thread.start();
							System.out.println("[!] Boot Server: "+thread.getName());

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

			List<String> list = new ArrayList<>();
			while ((line=bufferedReader.readLine())!=null) {
				list.add(line);
			}
			String[] listJSON = list.toArray(new String[list.size()]);
			for (int i=0;i < listJSON.length;i++) {
				String dealCode = BootMultiServer.replaceSpace(listJSON[i]);
				if (dealCode != null) {
					//System.out.println(dealCode);
					String defaultValue = dealCode.substring(0,dealCode.indexOf("\""));
					//System.out.println(defaultValue);
					if (defaultValue.equals(value)) {
						//return the config json files content and deal .
						//boot the Http server.
						String getContent = BootMultiServer.getValueContent(listJSON[i]);
						if (getContent != null) {
							jsonContent = getContent;
							break;
						}
					}
				}
			}
			return jsonContent;

		}catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	public static String replaceSpace(String code) {
		String space = "";
		for (int i=0; i < code.length();i++) {
			if (code.indexOf("\"") != -1) {
				space = code.substring(code.indexOf("\"")+1,code.length());
				break;
			}
			else {
				space = null;
			}
		}
		return space;
	}
	public static String getValueContent(String code) {
		String content = "";
		for (int i = 0; i< code.length();i++) {
			if (code.indexOf(":") != -1) {
				String tmpCode = code.substring(code.indexOf(":")+1,code.length());
				content = tmpCode.substring(tmpCode.indexOf("\"")+1,tmpCode.lastIndexOf("\""));
				break;
			}else {
				content = null;
			}
		}
		return content;
	}
}
