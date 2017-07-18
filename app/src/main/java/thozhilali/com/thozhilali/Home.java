package thozhilali.com.thozhilali;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity {
    Spinner et1;
    EditText et2;
    Button bt1;
    Button bt2;
    Button bt3;
    String location, worker_type;
    ArrayList<Skill> workers;
    String log;
    JSONArray jArray;
    Intent intent;
    Skill skill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        et1 = (Spinner) findViewById(R.id.ed);
        et2 = (EditText) findViewById(R.id.ed2);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        intent = getIntent();
        log = intent.getStringExtra("login");

        workers = new ArrayList<>();
        new GetSkills().execute();
        et1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                worker_type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, WorkerReg.class));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Login.class));
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                location = et2.getText().toString();


                Intent i = new Intent(Home.this, Searchresult.class);
                i.putExtra("worker", worker_type);
                i.putExtra("loc", location);
                i.putExtra("log", log);
                startActivity(i);
            }

        });


    }

    class GetSkills extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        private static final String SKILLS = "WorkersType.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Home.this);
            pd.setMessage("searching....");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pd.dismiss();
            try {
                if (json.length() > 0) {

                    jArray = new JSONArray(json);
                    JSONObject json_data = null;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        String id = json_data.getString("id");
                        String skillName = json_data.getString("skillName");
                        skill = new Skill(Integer.parseInt(id), skillName);
                        workers.add(skill);


                    }
                    populateSpinner();

                } else {
                    Toast.makeText(getApplicationContext(), "error server down", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> map = new HashMap<>();

            return new JSONParser().makeHttpRequest(Constants.API_URL + SKILLS, "POST", map);
        }
    }

    private void populateSpinner() {


        List<String> labels = new ArrayList<String>();

        for (int i = 0; i < workers.size(); i++) {
            labels.add(workers.get(i).getSkill());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);

        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et1.setAdapter(spinnerAdapter);


    }



}