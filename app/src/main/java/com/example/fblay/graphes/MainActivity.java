package com.example.fblay.graphes;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;

/**
 *  @author Florian Blay & Lucile Floc
 */
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
        if(id == R.id.reset_graph){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        if(id == R.id.mail_graph){
            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

            View screenView = rootView.getRootView();
            screenView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
            screenView.setDrawingCacheEnabled(false);

            final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
            File dir = new File(dirPath);
            if(!dir.exists())
                dir.mkdirs();
            File file = new File(dirPath, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Save");
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"lucile.floc@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Capture écran graphe");
            startActivity(Intent.createChooser(i, "Capture écran graphe"));

            /*Intent uneIntention;
            String mail = "lucile.floc@gmail.com";
            //uneIntention = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto: " + mail));
            uneIntention = new Intent (Intent.ACTION_SEND);
            uneIntention.putExtra(Intent.EXTRA_EMAIL, mail);
            startActivity(uneIntention);*/
        }
        return super.onOptionsItemSelected(item);
    }
}
