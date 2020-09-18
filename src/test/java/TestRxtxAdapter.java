import com.bobandata.iot.transport.acceptor.RxtxAcceptor;
import com.bobandata.iot.transport.acceptor.handler.ByteMsgHandle;
import com.bobandata.iot.transport.connector.RxtxSocketAdapter;
import com.bobandata.iot.transport.frame.EasyByteFrame;
import com.bobandata.iot.transport.protocol.ClientByteProtocol;
import com.bobandata.iot.transport.protocol.ISlaveProtocol;
import com.bobandata.iot.transport.protocol.ServerByteProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:03 2018/12/13.
 */
public class TestRxtxAdapter {

    public static void main(String[]args) throws InterruptedException {
        ISlaveProtocol slaveProtocol = new ISlaveProtocol() {
            @Override
            public void channelRegister(Channel channel) {

            }

            @Override
            public void channelRead(Channel paramChannel, Object paramObject) {
                paramChannel.writeAndFlush("123");
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getVersion() {
                return null;
            }

            @Override
            public void installProtocolFilter(ChannelPipeline paramChannelPipeline) {

            }
        };
        RxtxAcceptor rxtxAcceptor = new RxtxAcceptor("COM1", 2400, (short) 8, (short) 2, (short) 1, slaveProtocol);
        rxtxAcceptor.connect();
        Thread.sleep(1000000);
    }

}
