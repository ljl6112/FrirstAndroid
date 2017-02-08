package ljl.com.httpdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;



import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ljl.com.httpdemo.R;

public class TestActivity extends AppCompatActivity {

    @Bind(R.id.image) ImageView imageView;
    @Bind(R.id.edit) EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn1)
    public void btn1(View view) {
    }

    @OnClick(R.id.btn2)
    public void btn2(View view) {
    }

    @OnClick(R.id.btn3)
    public void btn3(View view) {
    }
}
