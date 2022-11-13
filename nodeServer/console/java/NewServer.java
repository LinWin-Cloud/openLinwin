import java.io.File;;

public class NewServer {
    public static void main(String[]args)
    {
        int CommandLength = args.length;
        if (CommandLength == 2)
        {
            int Port = 0;
            try{
                Port = Integer.valueOf(args[0]);
            }catch(Exception exception){
                System.out.println("Your Port Is Error: "+args[1]);
            }
            String ServerPath = args[1];
            File file = new File(ServerPath);
            if (!file.exists())
            {
                System.out.println("[Err] Target Path Is not exists!");
                System.exit(0);
            }
            if (file.exists()&&!file.isDirectory())
            {
                System.out.println("[Err] Target Path Is A File!");
                System.exit(0);
            }
        }
        else
        {
            System.out.println("[Err] You Must Private All The Value for the Console.\n- newServer [Server Port] [Server Path]");
        }
    }
}
