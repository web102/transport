import com.bobandata.iot.transport.connector.SocketAdapter;
import com.bobandata.iot.transport.frame.EasyByteFrame;
import com.bobandata.iot.transport.frame.SingleByteFrame;
import com.bobandata.iot.transport.protocol.ClientByteProtocol;
import com.bobandata.iot.transport.util.FinalConst;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 17:53 2018/12/13.
 */
public class TestSocketAdapter {

    public static void main(String[]args){
        //此规约加了心跳操作、如果不是长连接不需要加心跳操作
        ClientByteProtocol protocol = new ClientByteProtocol();
        SocketAdapter socketAdapter = new SocketAdapter("169.254.162.196",6666,protocol, FinalConst.LONG_CONNECT);
        socketAdapter.connect();
        EasyByteFrame easyByteFrame = new EasyByteFrame(new String("11111111").getBytes());
        try {
            SingleByteFrame frame = (SingleByteFrame)socketAdapter.getBody(easyByteFrame);
            System.out.println(frame.getSingleByte());
            Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
