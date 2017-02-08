package ljl.com.httpdemo.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.request.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ljl.com.httpdemo.Bean.User;
import ljl.com.httpdemo.R;
import ljl.com.httpdemo.callback.JsonCallback;
import ljl.com.httpdemo.utils.Urls;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ：
 * Created by geenk-008 on 2016/8/30.08:36
 * 作者：ljl
 * 邮箱：ljl6112@aliyun.com
 * 说明：注册
 */
public class MyRegisterActivity extends BaseActivity {
    private final String IMAGE = "http://192.168.1.132:8080/upload/servlet/Test01";
    private EditText my_regist_edit_name, my_regist_edit_passwod, my_regist_edit_passwod2;
    private TextView text_set;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_myregister);
        initviews();
    }

    private void initviews() {
        my_regist_edit_name = (EditText) findViewById(R.id.my_regist_edit_name);
        my_regist_edit_passwod = (EditText) findViewById(R.id.my_regist_edit_passwod);
        my_regist_edit_passwod2 = (EditText) findViewById(R.id.my_regist_edit_passwod2);
        text_set = (TextView) findViewById(R.id.regtst_text);

    }


    public void regist(View v) {

        String username = my_regist_edit_name.getText().toString();
        String password = my_regist_edit_passwod.getText().toString();
        String ok_password = my_regist_edit_passwod2.getText().toString();
//用户数据对应User表，然后转成json 传给服务器
        Gson s = new Gson();
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setImeal(ok_password);
        user.setSex("男");
        String si = s.toJson(user);
        try {
            JSONObject js=new JSONObject(si);
            String username1 = js.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        try {
//            JSONObject js=new JSONObject(si);
//            String name = js.getString("name");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        System.out.println("用户数据：" + si);
        OkHttpUtils.post(Urls.IQG_URL_REGISTER)
                .params("username",username)
                .params("password",password)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        text_set.setText(s);
                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        text_set.setText("清除");
                    }
                });





    }


}

