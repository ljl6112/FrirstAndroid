package ljl.com.httpdemo;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ：
 * Created by geenk-008 on 2016/8/27.14:55
 * 作者：ljl
 * 邮箱：ljl6112@aliyun.com
 * 说明：okHttp 的进一步封装
 */
public class OkHttpManager {
    private OkHttpClient client;
    private Handler mHandler;
    //使用单例模式
    private static OkHttpManager okHttpManager;
    private static OkHttpManager getInstance(){
        if (okHttpManager==null){
            okHttpManager=new OkHttpManager();
        }
        return okHttpManager;
    }
    private OkHttpManager(){
        client=new OkHttpClient();
        mHandler=new Handler(Looper.getMainLooper());
    }

    /**
     * GET 内部逻辑处理
     * @param url
     * @return
     */
    private Response p_getSync(String url)throws IOException{
        Request request=new Request.Builder().url(url).build();
        Response response=client.newCall(request).execute();
        return  response;
    }
    private String p_getSyncAsString(String url) throws IOException{
        return p_getSync(url).body().string();
    }

    private void p_getAsyncAsString(String url, final DataCallBack callBack){
        final Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataNo(request,e,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
    /**
     * zhi
     * 数据分发
     */
    private void deliverDataNo(final Request request, final IOException e, final DataCallBack callBack){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
//                    callBack.requestnNo(request,e);
                }
            }
        });
    }

    private void deliverDataOk(final Request request, IOException e, final DataCallBack callBack){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
//                    callBack.requestnNo(request,e);
                }
            }
        });
    }

    /**
     * 直接调用GET 同步请求
      * @param url
     * @return
     */
    public static Response getSync(String url)throws IOException{
        return getInstance().p_getSync(url);
    }
/**
 * 直接返回String
 */

    public static String getSyncAsString(String url) throws  IOException{
        return getInstance().p_getSyncAsString(url);
    }

    public static void getAsync(String url,DataCallBack callBack){

    }
    /**
     * 数据接口
     */
    public interface DataCallBack{
       void  requestnNo(Call call, IOException e);
        void  requstOk(Call call, Response response);
    }


}
