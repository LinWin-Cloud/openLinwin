import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class WebSafety {

    public static void SQL_Security(String HttpURL, PrintWriter printWriter, OutputStream outputStream, Socket socket,String version)
    {
        try
        {
            if (MultiServer.Sql_safe)
            {
                // sql防护服务处于打开状态
                //检查是否存在 SQL语句

                HttpURL=HttpURL.toUpperCase();//测试用sql语句
                //System.out.println(HttpURL);
                String column="(\\w+\\s*(\\w+\\s*){0,1})";//一列的正则表达式 匹配如 product p
                String columns=column+"(,\\s*"+column+")*"; //多列正则表达式 匹配如 product p,category c,warehouse w
                String ownerenable="((\\w+\\.){0,1}\\w+\\s*(\\w+\\s*){0,1})";//一列的正则表达式 匹配如 a.product p
                String ownerenables=ownerenable+"(,\\s*"+ownerenable+")*";//多列正则表达式 匹配如 a.product p,a.category c,b.warehouse w
                String from="FROM\\s+"+columns;
                String condition="(\\w+\\.){0,1}\\w+\\s*(=|LIKE|IS)\\s*'?(\\w+\\.){0,1}[\\w%]+'?";//条件的正则表达式 匹配如 a=b 或 a is b..
                String conditions=condition+"(\\s+(AND|OR)\\s*"+condition+"\\s*)*";//多个条件 匹配如 a=b and c like 'r%' or d is null
                String where="(WHERE\\s+"+conditions+"){0,1}";
                String pattern="SELECT\\s+(\\*|"+ownerenables+"\\s+"+from+")\\s+"+where+"\\s*"; //匹配最终sql的正则表达式
                if (HttpURL.matches(pattern))
                {
                    WebServer.sendErrorPage(405, printWriter, socket, outputStream);
                    return;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void XSS_Security(String HttpURL,PrintWriter printWriter,OutputStream outputStream,Socket socket,String version)
            throws Exception
    {
        if (MultiServer.Xss_safe) {
            HttpURL = HttpURL.replace(" ","");
            if (HttpURL.indexOf("<script")!=-1||HttpURL.indexOf("</")!=-1||HttpURL.indexOf("<link")!=-1||HttpURL.indexOf("/>")!=-1||HttpURL.indexOf("%2")!=-1)            {
                WebServer.sendErrorPage(405, printWriter, socket, outputStream);
                return;
            }
        }
    }
    public static Boolean BlackIP(String IPAdress,String[] IPBlack)
    {
        /**
         * IP地址黑名单机制：如果是IP位于黑名单内，则该IP访问直接拒绝访问
         */
        Boolean bool = false;
        for (int i = 0; i < IPBlack.length ; i++)
        {
            if (IPAdress.equals(IPBlack[i]))
            {
                bool = true;
            }
        }
        return bool;
    }
}
