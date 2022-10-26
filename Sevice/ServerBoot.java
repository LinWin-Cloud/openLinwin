
public class ServerBoot {
    public static void main(String[]args) throws Exception
    {
        try
        {
            Runtime.getRuntime().exec("./shell/start_http.sh");
            Runtime.getRuntime().exec("./shell/start_porxy.sh");

        }
        catch (Exception E)
        {
            System.out.println(E.getMessage());
        }
    }
}

