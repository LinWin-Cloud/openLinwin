public class load {
    public static void runLoad()
    {
        Thread load_All = new Thread(new Runnable(){
            public void run()
            {
                main.ServerDir = main.GetServerPath();
                main.ErrorPage = config.GetErrorPage();
            }
        });
        load_All.start();
    }
}
