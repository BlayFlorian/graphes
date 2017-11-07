package com.example.fblay.graphes;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private DrawableGraph graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graph = (DrawableGraph) findViewById(R.id.signature_canvas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.e("item:", "fef" + id);
        if (id == R.id.cNode || id == R.id.cArc || id == R.id.cModify) {
            item.setChecked(true);
            TextView t=(TextView)findViewById(R.id.modeText);
            t.setText(item.getTitle());
            switch (id) {
                case R.id.cNode: graph.setMode(0);
                    break;
                case R.id.cArc: graph.setMode(1);
                    break;
                case R.id.cModify: graph.setMode(2);
                    break;
                default: graph.setMode(-1);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
