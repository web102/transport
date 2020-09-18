import com.bobandata.iot.transport.connector.RxtxSocketAdapter;
import com.bobandata.iot.transport.frame.EasyByteFrame;
import com.bobandata.iot.transport.protocol.ClientByteProtocol;
import com.bobandata.iot.transport.util.HexUtils;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:03 2018/12/13.
 */
public class TestRxtxAdapter1 {

    public static void main(String[]args) throws InterruptedException {
        ClientByteProtocol protocol = new ClientByteProtocol();
        RxtxSocketAdapter rxtxSocketAdapter = new RxtxSocketAdapter("COM2", 2400, (short)8, (short)2,(short)1, protocol);
        rxtxSocketAdapter.connect();
        Thread.sleep(100*1000);
        String hexStr = "68 AA AA AA AA AA AA 68 11 04 33 33 33 33 AD 16".replaceAll(" ", "");
        EasyByteFrame easyByteFrame = new EasyByteFrame(HexUtils.decodeHex(hexStr.toCharArray()));
        try {
            EasyByteFrame frame = (EasyByteFrame)rxtxSocketAdapter.getBody(easyByteFrame);
            System.out.println(new String(frame.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
