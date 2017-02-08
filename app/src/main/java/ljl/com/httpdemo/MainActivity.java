package ljl.com.httpdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends Activity {
    private TextView uploadInfo;

    //服务器端地址
    final String url = "http://192.168.1.132:8080/upload/servlet/UploadServlet";
    final String url2 = "http://192.168.1.132:8080/upload/upload/image.json";
    //手机端要上传的文件地址，首先要保存你手机上存在该文件
    String filePath = Environment.getExternalStorageDirectory()
            + "/789.jpg";

    //从网络上获取的图片之后，保存在手机上的地址
    final String dowmSavePath = Environment.getExternalStorageDirectory() + "/dowmokimage.jpg";

    final AsyncHttpClient httpClient = new AsyncHttpClient();

    private ImageView image;
    public  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                image.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        image= (ImageView) findViewById(R.id.iamge_down);
//        uploadInfo = (TextView) findViewById(R.id.upload_info);
//        File file=new File(filePath);
//        if (file.exists()) {
//            Bitmap bmp=BitmapFactory.decodeFile(filePath);
//            image.setImageBitmap(bmp);
//            uploadInfo.setText("已经自动添加啦..");
//        }
    }

    public void ok(View v) {
        uploadFile();
    }

    public void uploadFile() {


        RequestParams param = new RequestParams();
        try {
            param.put("file", new File(filePath));
            param.put("content", "lengjiaolong");

            httpClient.post(url, param, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    uploadInfo.setText("正在上传...");

                }

                @Override
                public void onSuccess(String arg0) {
                    super.onSuccess(arg0);
                    getJsonData();
                    uploadInfo.setText(arg0);
                }

                @Override
                public void onFailure(Throwable arg0, String arg1) {
                    super.onFailure(arg0, arg1);
                    httpClient.get(url2,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(int i, String s) {
                            super.onSuccess(i, s);
                            try {
                                JSONObject js=new JSONObject(s);
                                String imageurl = js.getString("imgurl");
                                downImage(imageurl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    uploadInfo.setText("上传失败！");

                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "上传文件不存在！", Toast.LENGTH_LONG).show();
        }
    }

    private void getJsonData(){
        httpClient.get(url2,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                try {

                    JSONObject js=new JSONObject(s);
                    String imageurl = js.getString("imgurl");
                    downImage(imageurl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void downImage(String downurl) {
        httpClient.get(downurl,new BinaryHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                uploadInfo.setText("正在下载...");
            }

            @Override
            public void onSuccess(byte[] bytes) {
                super.onSuccess(bytes);
                uploadInfo.setText("下载成功...");
                Bitmap btm=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                Message ms=new Message();
                ms.obj=btm;
                handler.sendMessage(ms);
                File file=new File(dowmSavePath);
                if (file.exists()){
                    file.delete();
                }
                try {
                    file.createNewFile();

                    OutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}