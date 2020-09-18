package send.obj;

import java.io.Serializable;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 17:32 2018/11/19.
 */
public class Message implements Serializable {

    private int id;
    private String content;
    private User user;
    private double value;

    public Message(int id, String content, User user, double value){
        this.id = id;
        this.content=content;
        this.user = user;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user.toString() +
                ", value=" + value +
                '}';
    }
}
