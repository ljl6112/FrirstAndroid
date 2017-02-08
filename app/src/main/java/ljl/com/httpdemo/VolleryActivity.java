package ljl.com.httpdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BitmapCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ：
 * Created by geenk-008 on 2016/8/26.17:25
 * 作者：ljl
 * 邮箱：ljl6112@aliyun.com
 * 说明：
 */
public class VolleryActivity extends Activity {

    //    public static final MediaType MEDIA_TYPE_MARKDOWN
//            = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/octet-stream; charset=utf-8");

    //服务器端地址
//private  final String url = "http://192.168.1.132:8080/upload/servlet/UploadServlet";

    //    final String url2 = "http://192.168.1.132:8080/upload/upload/image.json";
//    //手机端要上传的文件地址，首先要保存你手机上存在该文件
//    String filePath = Environment.getExternalStorageDirectory()
//            + "/789.jpg";
//    //从网络上获取的图片之后，保存在手机上的地址
//    final String dowmSavePathImage = Environment.getExternalStorageDirectory() + "/dowmokimage.jpg";

    final String filedowmSavePathText = Environment.getExternalStorageDirectory() + "/okhttpDown.txt";
    final String fieldowmSavePathImage = Environment.getExternalStorageDirectory() + "/okhttpDown.jpg";

    private String filePathphoneImage = Environment.getExternalStorageDirectory()
            + "/372719722.jpeg";
    private final String REQUEST_URL = "http://192.168.1.132:8080/upload/servlet";
    private final String REQUEST_URL_IMAGE = "http://192.168.1.132:8080/upload/servlet/UploadServlet";

    private final String JSON_URL = "http://192.168.1.132:8080/upload/upload/image.json";
    private final String REQUEST_SERVER_URL = "http://e22a.com/h.ZyiANP?cv=AAImNmuI&sm=97ffac";
    private TextView tv_result;
    private ImageView img_result;
    private OkHttpClient client = new OkHttpClient();
    private Request request;
    private Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vollery);
        tv_result = (TextView) findViewById(R.id.text_okhttp);
        img_result = (ImageView) findViewById(R.id.iamge_down_okhttp);

    }

    /**
     * OKHttp GET同步
     *
     * @param view
     */
    public void getTb(View view) {
//        syso("OKHttp GET同步.....");

//        final Request request = new Request.Builder().url(REQUEST_URL + "/Test01").build();
        request = new Request.Builder().url(JSON_URL).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //数据在 response里面
                    response = client.newCall(request).execute();
                    final String message = response.body().string();
                    JSONObject jsobj = new JSONObject(message);
                    final String name = jsobj.getString("name");
                    final String imgurl = jsobj.getString("imgurl");
                    //短的数据 用string来返回    大的数据  用byte 字节流 来处理
                    //用Handler 来刷新UI 或者用下面这种方法
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_result.setText(imgurl);
                            img_result.setImageResource(R.mipmap.ic_launcher);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * OKHttp GET异步
     *
     * @param view
     */
    public void getYb(View view) {
//        syso("OKHttp GET异步.....");
//        request=new Request.Builder().url(REQUEST_URL+"/Test02").build();
        request = new Request.Builder().url(REQUEST_SERVER_URL).build();
        //请求加入到请求队列中
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String message = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(message);
                    }
                });

            }
        });
    }

    /**
     * Okhttp POST 提交表单
     *
     * @param view
     */
    public void post(View view) {
//        syso("OKHttp POST 提交表单.....");
        MultipartBody body = new MultipartBody.Builder("AaB03x")
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", null, new MultipartBody.Builder("BbC04y")
                        .addPart(Headers.of("Content-Disposition", "form-data; filename=\"312369.jpg\""),
                                RequestBody.create(MediaType.parse("image/jpg"), new File(filePathphoneImage)))
                        .build())
                .build();

        request = new Request.Builder()
                .url(REQUEST_URL_IMAGE)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText("上传成功");
                    }
                });
            }
        });

    }

    /**
     * Okhttp 文件下载
     *
     * @param view
     */
    public void down(View view) {
//        syso("OKHttp 文件下载.....");
        //先点击同步GET  在点击下载
        String number = tv_result.getText().toString();
        request = new Request.Builder().url(number).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //第一种方法 直接转化为字节流
                File file = new File(fieldowmSavePathImage);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                byte[] data1 = response.body().bytes();
                FileOutputStream fis = new FileOutputStream(file);
                fis.write(data1, 0, data1.length);
                fis.close();
                //第二种方法 转化为inputStream
                InputStream inputStream = response.body().byteStream();
//                File file=new File(fieldowmSavePathImage);
//                if (file.exists()){
//                    file.delete();
//                }
//                byte[] data2=new byte[1024];
//                int len=0;
//                FileOutputStream  fileout=null;
//                while((len=inputStream.read(data2))!=-1){
//                fis=new FileOutputStream(fieldowmSavePathImage);
//                    fileout.write(data2,0,len);
//                }
//                fileout.close();
//                inputStream.close();


                //拿到返回的输入流，转换为bitmap 直接设置图片显示
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img_result.setImageBitmap(bitmap);
                        tv_result.setText("显示成功");
                    }
                });

            }
        });

    }

    /**
     * 测试按钮
     *
     * @param view
     */
//    public void test(View view) {
//        syso("OKHttp 测试按钮.....");
//        if (getyb == 1) {
//            String number = tv_result.getText().toString();
//            OkHttpUtils.get().url(number).build().execute(new BitmapCallback() {
//                @Override
//                public void onResponse(boolean isFromCache, Bitmap bitmap, Request request, @Nullable Response response) {
//
//                }
//
//                @Override
//                public void onError(Call call, Exception e, int i) {
//
//                }
//
//                @Override
//                public void onResponse(Bitmap bitmap, int i) {
//                    img_result.setImageBitmap(bitmap);
//                }
//            });
//
//        }
//        if (post == 2) {
//            File file = new File(filePathphoneImage);
//            Map<String, String> params = new HashMap<>();
//            params.put("content", "lengjiaolong" + new Date(System.currentTimeMillis()));
//            params.put("file", "372719722.jpeg");
//
//            OkHttpUtils.post().addFile("img", "372719722.jpeg", file).url(REQUEST_URL_IMAGE).params(params).build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),"372719722.jpeg") {
//                @Override
//                public void onError(Call call, Exception e, int i) {
//
//                }
//
//                @Override
//                public void onResponse(File file, int i) {
//                    Toast.makeText(VolleryActivity.this,"成功啦",Toast.LENGTH_LONG).show();
//                }
//            });
//
//
//
//
//
//        }
//
//    }
//
//    private void syso(String string) {
//        System.out.println("输出：" + string);
//    }
//
//
//    private int getyb = 1;
//    private int post = 2;
//    private int dowmfile = 3;
//    private int setimage = 4;
}
