import com.bobandata.iot.transport.acceptor.Acceptor;
import com.bobandata.iot.transport.acceptor.handler.ByteMsgHandle;
import com.bobandata.iot.transport.protocol.ServerByteProtocol;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 18:06 2018/12/13.
 */
public class TestAcceptor {

    public static void main(String[]args){
        ServerByteProtocol serverByteProtocol = new ServerByteProtocol(new ByteMsgHandle() {
            @Override
            public boolean HandleMsg(byte[] content) {
                System.out.println(new String(content));
                return true;
            }
        });
        Acceptor acceptor = new Acceptor(6666, serverByteProtocol);
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
