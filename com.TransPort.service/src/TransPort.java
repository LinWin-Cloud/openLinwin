public class TransPort
{
    public static void main(String[] args)
    {
        try
        {

        }
        catch (Exception exception)
        {
            System.out.println("[Error] You must provide all the value for the Trans Port Software.\n");
            System.out.println("openTP [Proxy Host] [Proxy Host's Port] [TransPort Host] [TransPort Host's Port]");
            System.out.println("For example:\n" +
                    "openTP 127.0.0.1 80 127.0.0.1 8080");
            System.exit(0);
        }
    }
}
