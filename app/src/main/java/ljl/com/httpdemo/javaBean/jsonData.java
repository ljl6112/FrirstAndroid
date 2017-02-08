package ljl.com.httpdemo.javaBean;

/**
 * ：
 * Created by geenk-008 on 2016/8/27.10:31
 * 作者：ljl
 * 邮箱：ljl6112@aliyun.com
 * 说明：
 */
public class jsonData {

        public  String name;
        public String imgurl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
        public String toString() {
            return "Result{" +
                    "name='" + name + '\'' +
                    ", imgurl='" + imgurl + '\'' +
                    '}';
        }

    }