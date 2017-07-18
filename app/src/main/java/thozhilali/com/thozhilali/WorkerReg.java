package thozhilali.com.thozhilali;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class WorkerReg extends AppCompatActivity {
    EditText name, phone, email, psw, cnf,exp,wge;
    Button register;
    String fullName, mobile, username, password, confirm,selected,location,wage;
    Spinner skills;

    ArrayList<Skill> workers;

    JSONArray jArray;
    Skill skill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_reg);
        name = (EditText) findViewById(R.id.editText3);
        phone = (EditText) findViewById(R.id.editText4);
        email = (EditText) findViewById(R.id.editText5);
        register = (Button) findViewById(R.id.button7);
        psw = (EditText) findViewById(R.id.editText6);
        cnf = (EditText) findViewById(R.id.editText7);
        skills= (Spinner) findViewById(R.id.spinner);
        exp=(EditText) findViewById(R.id.editText);
        wge=(EditText) findViewById(R.id.editText2);
        workers = new ArrayList<>();
        new  GetSkillsAsync().execute();
        skills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = name.getText().toString();
                mobile = phone.getText().toString();
                username = email.getText().toString();
                password = psw.getText().toString();
                confirm = cnf.getText().toString();
                location=exp.getText().toString();
                String ep = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                wage=wge.getText().toString();
                if (fullName.equals("") || mobile.equals("") || username.equals("") || password.equals("") || confirm.equals("")) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_LONG).show();
                } else if (!password.equals(confirm)) {
                    Toast.makeText(WorkerReg.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                }else if (mobile.length()<10||mobile.length()>10) {
                    Toast.makeText(WorkerReg.this, "Not A valid Mobile Number ", Toast.LENGTH_SHORT).show();

                }else if(!username.matches(ep)){

                    Toast.makeText(WorkerReg.this, "Invalid Email Address ", Toast.LENGTH_SHORT).show();


                }
                else {
                    new RegAsync().execute();
                    Execute();
                }


            }
        });


    }
    public void Execute(){

    }

    private class RegAsync extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        private static final String SIGNUP = "SignUp.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(WorkerReg.this);
            pd.setMessage("Loading..");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.show();


        }

        @Override
        protected String doInBackground(String... args) {
            HashMap<String, String> params = new HashMap<>();
            params.put("name", fullName);
            params.put("ph", mobile);
            params.put("email", username);
            params.put("psw", password);
            params.put("type","employee");
            params.put("skill",selected);
            params.put("exp",location);
            params.put("wage",wage);
            Log.d("pp",params.toString());
            return new JSONParser().makeHttpRequest(Constants.API_URL + SIGNUP, "POST", params);
        }

        @Override
        protected void onPostExecute(String jsonData) {
            int success;
            String message;
            pd.dismiss();
            try {
                if (jsonData.length() > 0) {
                    JSONObject jsonObject=new JSONObject(jsonData);


                    success = jsonObject.getInt(Constants.TAG_SUCCESS);
                    message = jsonObject.getString(Constants.TAG_MESSAGE);
                    if (success == 1) {


                        Intent ii = new Intent(WorkerReg.this, Login.class);
                        finish();
                        startActivity(ii);
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
    private class GetSkillsAsync extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        private static final String SKILLS = "WorkersType.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(WorkerReg.this);
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
                    JSONObject json_data=null;
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
      skills.setAdapter(spinnerAdapter);




    }

}
