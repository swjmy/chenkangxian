package chenkangxian.PRC.RPCTCP.Consumer;

import chenkangxian.PRC.RPCTCP.Server.SayHelloService;
import chenkangxian.PRC.RPCTCP.Server.SayHelloServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/17.
 * 基于TCP协议实现一个简单的RPC,适用java的SocketAPI
 * 书本1.1.3
 */
public class RPCTCP {

    public static void main(String[] args) throws NoSuchMethodException, IOException, ClassNotFoundException {
        invoke();
    }


    private static Object invoke() throws NoSuchMethodException, IOException, ClassNotFoundException {
        String interfaceName = SayHelloService.class.getName();
        Method method = SayHelloService.class.getMethod("sayHello", String.class);
        String methodName = method.getName();
        Object[] arguments = {"hello"};

        Socket socket = new Socket("127.0.0.1",1234);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeUTF(interfaceName);
        outputStream.writeUTF(methodName);
        outputStream.writeObject(method.getParameterTypes());
        outputStream.writeObject(arguments);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Object result = in.readObject();
        return result;
    }
}
