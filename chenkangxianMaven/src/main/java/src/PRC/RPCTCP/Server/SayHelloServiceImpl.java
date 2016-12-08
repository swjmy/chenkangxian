package src.PRC.RPCTCP.Server;

/**
 * Created by Administrator on 2016/11/24.
 */
public class SayHelloServiceImpl implements SayHelloService {
    public String sayHello(String helloArg) {
        if (helloArg.equals("hello"))
            return "hello";
        return "byebye";
    }
}
