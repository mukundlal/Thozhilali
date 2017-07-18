package thozhilali.com.thozhilali;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.view.SoundEffectConstants.CLICK;
import static thozhilali.com.thozhilali.R.id.tab;

public class Workerview extends AppCompatActivity {
    TabLayout mTabLayout;
    FrameLayout frameLayout;
    FragmentManager fm;
    ImageView photo;
    Intent camera;
    String review_data,mailTo,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workerview);
        mTabLayout = (TabLayout) findViewById(tab);
        frameLayout=(FrameLayout)findViewById(R.id.l1);
        photo=(ImageView)findViewById(R.id.imageView2) ;
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]
                                {Manifest.permission.CAMERA}, CLICK);
                        return;

                    }
                    else {
                        startActivityForResult(camera,CLICK);
                    }
                }
                else {
                    startActivityForResult(camera,CLICK);
                }


            }
        });


        fm=getFragmentManager();
        fm.beginTransaction().replace(R.id.l1,new Details()).commit();
        setupTabLayout();
    }



    private void setupTabLayout() {





        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                onTabTapped(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabTapped(tab.getPosition());

            }
        });
    }

    private void onTabTapped(int position) {

        switch (position) {

            case 0:

                fm.beginTransaction().replace(R.id.l1,new Details()).commit();

                break;
            case 1:

                fm.beginTransaction().replace(R.id.l1,new Reviews()).commit();
                break;
            default:
                Toast.makeText(this,"Tapped2 " + position, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_workerprofile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.search:
                Intent in=new Intent(getApplicationContext(),Home.class);
                in.putExtra("login","login");
                startActivity(in);
                break;
            case R.id.profile:
                AlertDialog.Builder builder = new AlertDialog.Builder(Workerview.this);
                builder.setMessage("sure want to logout?")
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
