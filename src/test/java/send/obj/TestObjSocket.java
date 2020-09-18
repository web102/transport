package send.obj;

import com.bobandata.iot.transport.connector.ByteSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 0:18 2018/11/4.
 */
public class TestObjSocket {

    private static final Logger logger = LoggerFactory.getLogger(TestObjSocket.class);

    public static AtomicInteger status = new AtomicInteger(0);

    public static void main(String[] arg) throws InterruptedException {
        List<ByteSocketAdapter> list = new ArrayList<ByteSocketAdapter>();
        for(int i=0; i<100; i++){
            ByteSocketAdapter byteSocketAdapter = new ByteSocketAdapter("169.254.162.196", 6667);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j=0; j<10000; j++){
                        Message message = new Message(1,"lizhipeng", new User(1,"lizhipeng", "123456"), 1.1);
                        byte status = byteSocketAdapter.sendMessage(message);
                    }
                    status.incrementAndGet();
                    while (status.get() == 100){
                        int sum = 0;
                        int num = 0;
                        for (ByteSocketAdapter socket:list) {
                            num++;
                            logger.info("socket"+num+"发送数据："+socket.getSendNum());
                            sum+=socket.getSendNum();
                        }
                        logger.info("客户端发送消息总数："+sum);
                        return;
                    }
                }
            }).start();
            list.add(byteSocketAdapter);
        }
        Thread.sleep(1000000);
    }

}
