package send.obj;

import com.bobandata.iot.transport.acceptor.ByteAcceptor;
import com.bobandata.iot.transport.acceptor.handler.ObjectMsgHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 0:18 2018/11/4.
 */
public class TestObjAcceptor{

    private static final Logger logger = LoggerFactory.getLogger(TestObjAcceptor.class);

    public static AtomicInteger receiveNum = new AtomicInteger(0);

    public static void main(String[] arg) throws Exception {
        ByteAcceptor byteAcceptor = new ByteAcceptor(6667, new ObjectMsgHandle<Message>() {
            @Override
            public boolean HandleObjMsg(Message message) {
                receiveNum.incrementAndGet();
                System.out.println("服务端接收消息："+message.toString());
                logger.info("服务端接收消息总数："+receiveNum.get());
                return true;
            }
        });
        Thread.sleep(10000000);
    }

}
