import com.bobandata.iot.transport.acceptor.ByteAcceptor;
import com.bobandata.iot.transport.acceptor.handler.ByteMsgHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 0:18 2018/11/4.
 */
public class TestByteAcceptor {

    private static final Logger logger = LoggerFactory.getLogger(TestByteAcceptor.class);

    public static AtomicInteger receiveNum = new AtomicInteger(0);

    public static void main(String[] arg) throws Exception {
        ByteAcceptor byteAcceptor = new ByteAcceptor(6666, new ByteMsgHandle() {
            @Override
            public boolean HandleMsg(byte[] content) {
                receiveNum.incrementAndGet();
                //System.out.println("服务端接收消息："+new String(content));
                logger.info("服务端接收消息总数："+receiveNum.get());
                return true;
            }
        });
        Thread.sleep(10000000);
    }

}
