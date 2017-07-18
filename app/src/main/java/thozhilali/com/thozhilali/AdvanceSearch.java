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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static thozhilali.com.thozhilali.R.id.tab;

public class AdvanceSearch extends AppCompatActivity {
    Intent intent;

    TabLayout mTabLayout;

    FrameLayout frameLayout;
    FragmentManager fm;
    ImageView photo;
    Button call,review,mail;
    private static final int CALL_REQ=1;
    private static final int CLICK=2;
    Intent call_in,camera;
    String review_data,date,mailTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);
        intent=getIntent();
        mailTo=Searchresult.item;

        mTabLayout = (TabLayout) findViewById(tab);
        frameLayout=(FrameLayout)findViewById(R.id.l1);
        photo=(ImageView)findViewById(R.id.imageView2) ;
        call=(Button)findViewById(R.id.button);
        review=(Button)findViewById(R.id.button2);
        mail=(Button)findViewById(R.id.Mail);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=intent.getStringExtra("ph");
                call_in= new Intent(Intent.ACTION_CALL);
                call_in.setData(Uri.parse("tel:" + phone));
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]
                                {Manifest.permission.CALL_PHONE}, CALL_REQ);
                        return;

                    }
                    else{
                        startActivity(call_in);
                    }
                }
                startActivity(call_in);



            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ml=intent.getStringExtra("ml");
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                String[] recipients = new String[]{""+ml, "",};

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Worker Need");


                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "type the message");

                emailIntent.setType("text/plain");

                startActivity(Intent.createChooser(emailIntent, "Send mail..."));


                           }
        });




        fm=getFragmentManager();
        fm.beginTransaction().replace(R.id.l1,new SearchDetails()).commit();
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

                fm.beginTransaction().replace(R.id.l1,new SearchDetails()).commit();

                break;
            case 1:

                fm.beginTransaction().replace(R.id.l1,new SearchReviews()).commit();
                break;
            default:
                Toast.makeText(this,"Tapped2 " + position, Toast.LENGTH_SHORT).show();
        }
    }
    private void showDialog(){
        LayoutInflater li = LayoutInflater.from(AdvanceSearch.this);
        View promptsView = li.inflate(R.layout. dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                AdvanceSearch.this);

        alertDialogBuilder.setView(promptsView);


        final EditText reviews=(EditText) promptsView.findViewById(R.id.txtDelete);



        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        review_data=reviews.getText().toString();
                         date= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        dialog.dismiss();
                        new  PostReview().execute();


                    }
                })



                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }
    private class PostReview extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        private static final String POST_REVIEW = "Review.php";

        protected void onPreExecute() {
            pd = new ProgressDialog(AdvanceSearch.this);
            pd.setMessage("Wait...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... args) {
            HashMap<String, String> params = new HashMap<>();
            params.put("email", Login.mail);
            params.put("postedTo",mailTo);
            params.put("review", review_data);
            params.put("date", date);
            params.put("type",Login.type);
            Log.d("pp",params.toString());

            return new JSONParser().makeHttpRequest(Constants.API_URL + POST_REVIEW, "POST", params);


        }


        protected void onPostExecute(String json) {
            int success;
            String message;
            pd.dismiss();
            try {
                if (json.length() > 0) {

                    JSONObject jsonObject=new JSONObject(json);


                    success = jsonObject.getInt(Constants.TAG_SUCCESS);
                    message = jsonObject.getString(Constants.TAG_MESSAGE);
                    if (success == 1) {
                        Toast.makeText(getApplicationContext(), "Review posted", Toast.LENGTH_LONG).show();



                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "error server down", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


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
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(),Workerview.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        {
            switch (requestCode) {
                case CALL_REQ:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
                        startActivity(call_in);

                    } else {
                        Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();

                    }
                    break;
                case CLICK:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
                        startActivityForResult(camera,CLICK);

                    } else {
                        Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();

                    }
                    break;

                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode==CLICK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    photo.setImageBitmap(bitmap);
                }
            }
        }
    }
}

