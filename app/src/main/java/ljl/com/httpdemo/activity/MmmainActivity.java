package ljl.com.httpdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import ljl.com.httpdemo.R;
import ljl.com.httpdemo.TreeViewAdapter;
import ljl.com.httpdemo.widget.TreeView;

public class MmmainActivity extends Activity {
    private TreeView treeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        treeView = (TreeView) findViewById(R.id.tree_view);
        treeView.setHeaderView(getLayoutInflater().inflate(R.layout.list_head_view, treeView,
                false));
        treeView.setAdapter(new TreeViewAdapter(this, treeView));
    }


}
