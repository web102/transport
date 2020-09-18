import com.bobandata.iot.transport.connector.HttpAdapter;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:36 2018/12/13.
 */
public class TestHttpAdapter {

    public static void main(String[] args) throws Exception {
        HttpAdapter client = new HttpAdapter("http://localhost:9090/meter/findAll");
        client.connect();
        System.out.println(client.getBody(""));
        client.disconnect();
    }

}
