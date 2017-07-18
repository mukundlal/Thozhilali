package thozhilali.com.thozhilali;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Searchresult extends AppCompatActivity {
    Button srt;
    Button fltr;
    Intent intent;
    String worker,loc,sort_type;
static String item;
    ListView list;
    JSONArray jArray;
    ArrayList<WorkerDetails>arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);
        intent=getIntent();
        worker=intent.getStringExtra("worker");
        sort_type=intent.getStringExtra("log");
        loc=intent.getStringExtra("loc");
        list=(ListView)findViewById(R.id.search_list);
        arrayList=new ArrayList<>();
        new SearchAsync().execute();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item=arrayList.get(position).getMail();
                if(sort_type.equals("login")){
                    String pho=arrayList.get(position).getPhone();
                    String mail=arrayList.get(position).getMail();
                    Intent intent=new Intent(getApplicationContext(),AdvanceSearch.class);
                    intent.putExtra("ph",pho);
                    intent.putExtra("ml",mail);

                    startActivity(intent);


                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Searchresult.this);
                    builder.setMessage("please Login To see Details")
                            .setCancelable(true)
                            .setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                }
                            });


                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });



    }
    private class SearchAsync extends AsyncTask<String,String,String>{
        ProgressDialog pd;
       private static final String SEARCH="SearchResult.php";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(Searchresult.this);
            pd.setMessage("searching....");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            super.onPostExecute(jsonObject);
            pd.dismiss();
            try {
                if (jsonObject.length() > 0) {

                    jArray = new JSONArray(jsonObject);
                    JSONObject json_data = null;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        String name = json_data.getString("Name");
                        String mail  = json_data.getString("Email");
                        String age = json_data.getString("Age");
                        String address = json_data.getString("Address");
                        String pin = json_data.getString("PinCode");
                        String city=json_data.getString("city");
                        String skill=json_data.getString("Skills");
                        String street=json_data.getString("StreetName");
                        String phone=json_data.getString("Mobile");


                        WorkerDetails workerDetails = new WorkerDetails(name,skill,phone,mail,address,street,city,pin,age);
                        arrayList.add(workerDetails);
                    }
                    list.setAdapter(new WorkerAdapter(getApplicationContext(), arrayList));

                } else {
                    Toast.makeText(getApplicationContext(), "error server down", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String>map=new HashMap<>();
            map.put("loc",loc);
            map.put("work",worker);
            return new JSONParser().makeHttpRequest(Constants.API_URL+SEARCH,"POST",map);
        }
    }
    }



