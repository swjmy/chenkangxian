package src.PRC.RPCTCP.Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Daemon {
    private static Map<Class<? extends Service>,Class<? extends Service>> services = new HashMap<Class<? extends Service>,Class<? extends Service>>();

    private static void init(){
        services.put(SayHelloService.class,SayHelloServiceImpl.class);
    }

    private static Object get(Class interfaceClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class interClass = services.get(interfaceClass);
        Object result = interClass.newInstance();

        return result;
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, IOException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        init();
        start();
    }


    private static void start() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ServerSocket server = new ServerSocket(1234);
        while (true){
            //监听请求
            Socket socket = server.accept();
            //读取请求参数
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            String interfaceName = inputStream.readUTF();
            String methodStr = inputStream.readUTF();
            Class<?>[] types = (Class<?>[]) inputStream.readObject();
            Object[] arguments = (Object[]) inputStream.readObject();

            Class interfaceClass = Class.forName(interfaceName);
            Service service = (Service) get(interfaceClass);
            Method method = interfaceClass.getMethod(methodStr,types);
            Object result = method.invoke(service,arguments);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
        }

    }
}
