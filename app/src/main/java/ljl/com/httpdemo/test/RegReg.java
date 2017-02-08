//package ljl.com.httpdemo.test;
//
///**
// * ：
// * Created by geenk-008 on 2016/8/31.08:51
// * 作者：ljl
// * 邮箱：ljl6112@aliyun.com
// * 说明：
// */
//    import java.io.IOException;
//    import java.io.InputStream;
//    import java.io.UnsupportedEncodingException;
//    import java.util.ArrayList;
//    import java.util.List;
//
//    import org.apache.http.HttpEntity;
//    import org.apache.http.HttpResponse;
//    import org.apache.http.NameValuePair;
//    import org.apache.http.client.ClientProtocolException;
//    import org.apache.http.client.HttpClient;
//    import org.apache.http.client.entity.UrlEncodedFormEntity;
//    import org.apache.http.client.methods.HttpPost;
//    import org.apache.http.impl.client.DefaultHttpClient;
//    import org.apache.http.message.BasicNameValuePair;
//    import org.yanzi.entities.User;
//
//    import android.app.Activity;
//    import android.app.AlertDialog;
//    import android.content.DialogInterface;
//    import android.content.Intent;
//    import android.os.Bundle;
//    import android.os.Handler;
//    import android.os.Message;
//    import android.util.Log;
//    import android.view.View;
//    import android.view.View.OnClickListener;
//    import android.view.View.OnFocusChangeListener;
//    import android.widget.AdapterView;
//    import android.widget.AdapterView.OnItemSelectedListener;
//    import android.widget.Button;
//    import android.widget.EditText;
//    import android.widget.RadioButton;
//    import android.widget.RadioGroup;
//    import android.widget.Spinner;
//    import android.widget.TextView;
//    /**
//     * Register
//     * @author renzhongfeng
//     * 是注册界面对应的java类，实现用户注册功能 2014/07/27
//     */
//    public class Register extends Activity {
//        /**
//         * TELEPHONE 联系方式选择为手机号 EMAIL 联系方式选择为电子邮件，QQ 联系方式选择为QQ WECHAT 联系方式选择为微信
//         * OTHERS 联系方式选择为其它，usernameCursor 判读用户名输入框是失去光标还是获得光标repasswordCursor
//         * 判读重复密码输入框是失去光标还是获得光标 mySex 接收用户输入的�?�?
//         */
//        public final static int TELEPHONE = 0;
//        public final static int EMAIL = 1;
//        public final static int QQ = 2;
//        public final static int WECHAT = 3;
//        public final static int OTHERS = 4;
//        private EditText userName = null;
//        private EditText password = null;
//        private EditText rePassword = null;
//        private RadioGroup sex = null;
//        private RadioButton male = null;
//        private RadioButton female = null;
//        private Spinner communication = null;
//        private Button register = null;
//        private Button goback = null;
//        private User user = null;
//        private boolean usernameCursor = true;// 判读用户名输入框是失去光标还是获得光标
//        private boolean repasswordCursor = true;// 判读重复密码输入框是失去光标还是获得光标
//        private String mySex = null;
//        private String myCommunication = null;
//        private TextView communication_way_choice = null;
//        private EditText communication_content = null;
//        private Handler handler = new Handler(){
//            public void handleMessage(Message msg) {
//                if (msg.what == 1) {//等于1是说明接收到了注册成功的信息，当注册成功时，服务器会返回1给客户端
//
//                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT)
//                            .show();
//                    Intent register_login = new Intent(Register.this, Login.class);
//                    startActivity(register_login);
//                    finish();
//
//
//                } else {
//                    if (userName.getText().toString() == null) {
//                        Toast.makeText(Register.this, "用户名不能为空", Toast.LENGTH_SHORT)
//                                .show();
//                        userName.requestFocus();
//                    } else{
//                        Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                }
//            };
//        };
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            // TODO Auto-generated method stub
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_register);
//
//            init();
//            initListener();
//        }
//
//        /**
//         * 页面元素初始化
//         */
//        private void init() {
//
//            this.userName = (EditText) this.findViewById(R.id.regi_userName);
//            this.password = (EditText) this.findViewById(R.id.regi_password);
//            this.rePassword = (EditText) this.findViewById(R.id.regi_repassword);
//            this.sex = (RadioGroup) this.findViewById(R.id.regi_sex);
//            this.male = (RadioButton) this.findViewById(R.id.regi_male);
//            this.female = (RadioButton) this.findViewById(R.id.regi_female);
//            this.communication = (Spinner) this
//                    .findViewById(R.id.regi_communication_way);
//            this.register = (Button) this.findViewById(R.id.regi_register);
//            this.goback = (Button) this.findViewById(R.id.regi_goback);
//            this.communication_way_choice = (TextView)findViewById(R.id.communication_way_choice);
//            this.communication_content = (EditText)findViewById(R.id.communication_content);
//        }
//
//        /**
//         * 监听事件的初始化 用户名输入框光标失去监听，密码重新输入框的光标失去监听，性别复选框监听，注册按钮点击监听，返回按钮点击监�?
//         */
//        private void initListener() {
//            this.userName.setOnFocusChangeListener(new CheckUsernameListener());
//            this.rePassword.setOnFocusChangeListener(new RePasswordListener());
//            this.sex.setOnCheckedChangeListener(new RadioGroupSex());
//            this.communication.setOnItemSelectedListener(new SpinnerListener());
//            this.register.setOnClickListener(new RegisterListener());
//            this.goback.setOnClickListener(new ExitListener());
//        }
//
//        /**
//         * CheckUsernameListener
//         * @author renzhongfeng
//         * 当输入完用户名，输入框失去光标后,�?��该用户名在数据库中是否存�?
//         */
//        private class CheckUsernameListener implements OnFocusChangeListener {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                String myUsername = userName.getText().toString();
//                if (!usernameCursor) {
//                    if (isUsernameExisted(myUsername)) {
//                        Toast.makeText(Register.this, "该用户名已经存在，请更改用户名",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            /**
//             * 用于检测用户输入的用户名是否已经注册
//             * 将用户输入的用户名传动到服务器，在用户数据库中查找该用户名，若能够查找到则返回true，否则返回false
//             * @param username
//             *            用户输入的用户名
//             * @return 标记该用户名是否已经存在，存在为true，不存在为false
//             */
//            private boolean isUsernameExisted(String username) {
//                boolean flag = false;
//                return flag;
//            }
//        }
//
//        /**
//         * RadioGroupSex
//         * @author renzhongfeng
//         * 性别复选框的监听类，并将获得的性别赋给成员变量mySex 2014/07/27
//         */
//        private class RadioGroupSex implements RadioGroup.OnCheckedChangeListener {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // TODO Auto-generated method stub
//                if (checkedId == male.getId()) {
//                    mySex = "男";
//                } else {
//                    mySex = "女";
//                }
//            }
//        }
//
//        /**
//         * RePasswordListener
//         * @author renzhongfeng
//         * 重复输入密码失去光标的监听类 2014/07/27
//         */
//        private class RePasswordListener implements OnFocusChangeListener {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                if (repasswordCursor=!repasswordCursor) {
//                    if (!checkPassword(password.getText().toString(), rePassword
//                            .getText().toString())) {
//                        rePassword.setText("");
//                        //rePassword.requestFocus();
//                        Toast.makeText(Register.this, "两次密码不一样，请重新输入",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//
//        }
//        /**
//         * 比较两次输入的密码是否一致
//         * rePassword输入完成后，光标改变监听，和password进行比较，如果不一致，会有提示，并且两次密码密码清空
//         *
//         * @param psw1
//         *            密码框中输入的密码
//         * @param psw2
//         *            重复密码框中输入的密码
//         * @return 两次密码一致返回true，否则返回false
//         */
//        private boolean checkPassword(String psw1, String psw2) {
//            if (psw1.equals(psw2))
//                return true;
//            else
//                return false;
//        }
//
//        /**
//         * SpinnerListener
//         * @author renzhongfeng
//         * 联系方式的spinner组件commnunication监听，获得Item的内�?
//         */
//        private class SpinnerListener implements OnItemSelectedListener {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                // TODO Auto-generated method stub
//                myCommunication = parent.getItemAtPosition(position).toString();
//                communication_way_choice.setText(myCommunication);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//
//            }
//
//        }
//        /**
//         * 执行注册的方法
//         */
//        public void excuteRegister(){
//            new Thread(){
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    super.run();
//                    HttpClient client = new DefaultHttpClient();
//                    List<NameValuePair> list = new ArrayList<NameValuePair>();
//                    NameValuePair pair = new BasicNameValuePair("index", "2");
//                    list.add(pair);
//                    NameValuePair pair1 = new BasicNameValuePair("username", userName.getText().toString());
//                    NameValuePair pair2 = new BasicNameValuePair("password", password.getText().toString());
//                    NameValuePair pair3 = new BasicNameValuePair("sex", mySex);
//                    NameValuePair pair4 = new BasicNameValuePair("communication_way", myCommunication);
//                    NameValuePair pair5 = new BasicNameValuePair("communication_num", communication_content.getText().toString());
//                    list.add(pair1);
//                    list.add(pair2);
//                    list.add(pair3);
//                    list.add(pair4);
//                    list.add(pair5);
//                    try {
//                        HttpEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
//                        Log.i("register----------->", "HttpPost前");
//                        HttpPost post = new HttpPost("http://172.21.8.17:8080/server/Servlet");
//                        Log.i("register----------->", "HttpPost后1");
//                        post.setEntity(entity);
//                        HttpResponse response = client.execute(post);
//                        Log.i("register----------->", "HttpPost前2");
//                        if (response.getStatusLine().getStatusCode() == 200) {
//                            InputStream in = response.getEntity().getContent();
//                            handler.sendEmptyMessage(in.read());//将服务器端返回给客户端的标记直接传输给handler
//                            in.close();
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (ClientProtocolException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                }
//            }.start();
//        }
//        /**
//         * RegisterListener
//         * @author renzhongfeng
//         * 点击注册按钮后，向服务器端发送注册信息，等到服务器返回确认信息后，提示注册成功，并自动返回登陆页�?
//         * 2014/07/28
//         */
//        private class RegisterListener implements OnClickListener {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if (mySex == null || communication_content.getText().toString() == null) {
//                    String title = "提示：";
//                    String message = "您的信息不完整，填写完整信息有助于我们提供更好的服务";
//                    new AlertDialog.Builder(Register.this).setTitle(title)
//                            .setMessage(message)
//                            .setPositiveButton("继续注册", new MakeSureListener())
//                            .setNegativeButton("返回修改", null).show();
//                } else if (checkPassword(password.getText().toString(), rePassword
//                        .getText().toString())) {
//                    excuteRegister();
//                }else{
//                    rePassword.setText("");
//                    //rePassword.requestFocus();
//                    Toast.makeText(Register.this, "两次密码不一样，请重新输入",
//                            Toast.LENGTH_SHORT).show();
//                }
//
//            }
//            /**
//             * 获取用户的注册信息获取用户在页面上填写的信息，并将这些信息封装到User类中
//             * @return User 包含有用户完整注册信息的User包装�?
//             */
//            private User getUser() {
//                User user = new User();
//                user.setPassword(password.getText().toString());
//                user.setUsername(userName.getText().toString());
//                user.setSex(mySex);
//                user.setCommunication(myCommunication);
//                user.setCommunication(communication_content.getText().toString());
//                return user;
//            }
//        }
//
//        /**
//         * ExitListener
//         * @author renzhongfeng
//         * 设置“返回按钮的点击监听，点击后回到登陆界面2014/07/27
//         */
//        private class ExitListener implements OnClickListener {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent register_login = new Intent(Register.this, Login.class);
//                startActivity(register_login);
//                finish();
//            }
//        }
//
//        /**
//         * MakeSureListener
//         * @author renzhongfeng
//         * 确定提示框的确定按钮监听
//         */
//        private class MakeSureListener implements
//                android.content.DialogInterface.OnClickListener {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//                if (checkPassword(password.getText().toString(), rePassword
//                        .getText().toString())) {
//                    excuteRegister();
//                }else{
//                    rePassword.setText("");
//                    //rePassword.requestFocus();
//                    Toast.makeText(Register.this, "两次密码不一样，请重新输入",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//}
