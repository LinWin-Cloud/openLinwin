import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws Exception
    {
        /*
         * 向服务器端发送数据
         */
        InetAddress address = InetAddress.getByName("localhost"); // 1.定义服务器的地址、端口号、数据
        int port = 8800;//定义端口类型
        while(true) {//通过循环不同的向客户端发送和接受数据
            Scanner scanner = new Scanner(System.in);//从键盘接受数据
            String send = scanner.nextLine();//nextLine方式接受字符串
            byte[] data = send.getBytes();//将接收到的数据变成字节数组
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);//2.创建数据报，包含发送的数据信息
            DatagramSocket socket = new DatagramSocket(); // 3.创建DatagramSocket对象
            socket.send(packet);// 4.向服务器端发送数据报

            /*
             * 接收服务器端响应的数据
             */
            byte[] data2 = new byte[1024];//创建字节数组
            DatagramPacket packet2 = new DatagramPacket(data2, data2.length);// 1.创建数据报，用于接收服务器端响应的数据
            socket.receive(packet2);// 2.接收服务器响应的数据
            //3.读取数据
            String reply = new String(data2, 0, packet2.getLength());//创建字符串对象
            System.out.println("我是客户端，服务器说：" + reply);//输出提示信息
            socket.close();//4.关闭资源
        }
    }
}
