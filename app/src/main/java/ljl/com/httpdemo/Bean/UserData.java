package ljl.com.httpdemo.Bean;

/**
 * ：
 * Created by geenk-008 on 2016/8/30.14:26
 * 作者：ljl
 * 邮箱：ljl6112@aliyun.com
 * 说明：用户注册 javaBean
 */
public class UserData {

    private int id;
    private String username;         //用户昵称
    private String password;    //密码
    private String phone;       //手机号
    private String sex;        //性别
    private String address;    //常住地
    private String email;      //邮箱
    private String taobaoid;   //淘宝号
    private String verification; //验证码

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaobaoid() {
        return taobaoid;
    }

    public void setTaobaoid(String taobaoid) {
        this.taobaoid = taobaoid;
    }
}
