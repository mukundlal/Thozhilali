package thozhilali.com.thozhilali;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    Button sn;
    EditText nm, phn, eml, pss, cfmpss;

    String username, password, mobile, fullName,confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sn = (Button) findViewById(R.id.sn);
        nm = (EditText) findViewById(R.id.nm);
        phn = (EditText) findViewById(R.id.phn);
        eml = (EditText) findViewById(R.id.eml);
        pss = (EditText) findViewById(R.id.pss);
        cfmpss = (EditText) findViewById(R.id.cnfpss);


        sn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                fullName = nm.getText().toString();
                mobile = phn.getText().toString();
                username = eml.getText().toString();
                password = pss.getText().toString();
                confirm = cfmpss.getText().toString();
                String ep = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (fullName.equals("") || mobile.equals("") || username.equals("") ||  confirm.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_LONG).show();

                } else if (!password.equals(confirm)) {
                    Toast.makeText(SignUp.this, "Password mismatch", Toast.LENGTH_SHORT).show();

                }else if (mobile.length()<10||mobile.length()>10){
                    Toast.makeText(SignUp.this, "Not A valid Mobile Number ", Toast.LENGTH_SHORT).show();

                }else if(!username.matches(ep)){

                    Toast.makeText(SignUp.this, "Invalid Email Address ", Toast.LENGTH_SHORT).show();


                } else {
                    new SignUpAsync().execute();
                    Execute();
                }
            }
        });
    }
    public void Execute(){



    }


    private class SignUpAsync extends AsyncTask<String, String, String> {


        ProgressDialog pd;
        private static final String SIGNUP = "SignUp.php";


        protected void onPreExecute() {
            pd = new ProgressDialog(SignUp.this);
            pd.setMessage("Wait...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... args) {
            HashMap<String, String> params = new HashMap<>();
            params.put("name", fullName);
            params.put("ph", mobile);
            params.put("email", username);
            params.put("psw", password);
            params.put("type","employer");
            Log.d("pp",params.toString());

            return new JSONParser().makeHttpRequest(Constants.API_URL + SIGNUP, "POST", params);

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


                        Intent ii = new Intent(SignUp.this, Login.class);
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
}
