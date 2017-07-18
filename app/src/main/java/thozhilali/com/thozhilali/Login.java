package thozhilali.com.thozhilali;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText t1;
    EditText t2;
    Button b1;
    Button b2;
    String username, password;
    ProgressDialog pd;
    static String type;
    static String mail,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1 = (Button) findViewById(R.id.log);
        b2 = (Button) findViewById(R.id.sin);
        t1 = (EditText) findViewById(R.id.led1);
        t2 = (EditText) findViewById(R.id.led2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = t1.getText().toString();
                password = t2.getText().toString();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "please enter valid username and password", Toast.LENGTH_LONG).show();

                } else {
                    new LoginAsync().execute();
                }
            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return super.onCreateOptionsMenu(menu);
    } @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.home:
                Intent in=new Intent(getApplicationContext(),Home.class);
                in.putExtra("login","login");
                startActivity(in);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private class LoginAsync extends AsyncTask<String, String, String> {
        private static final String LOGIN_URL = "Login.php";

        protected void onPreExecute() {
            pd = new ProgressDialog(Login.this);
            pd.setMessage("Wait...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... args) {
            HashMap<String, String> params = new HashMap<>();
            params.put("email", username);
            params.put("psw", password);

            return new JSONParser().makeHttpRequest(Constants.API_URL + LOGIN_URL, "POST", params);


        }


        protected void onPostExecute(String json) {
            int success;
            String message;
            pd.dismiss();
            try {
                if (json.length() > 0) {

                    JSONObject jsonObject = new JSONObject(json);


                    success = jsonObject.getInt(Constants.TAG_SUCCESS);
                    message = jsonObject.getString(Constants.TAG_MESSAGE);

                    if (success == 1) {
                        type = jsonObject.getString("type");
                        mail = jsonObject.getString("email");
                        phone=jsonObject.getString("ph");
                      /*  if(type.equals("employer"))
                            startActivity( new Intent(Login.this,UserHome.class));
                        else*/ startActivity(new Intent(Login.this,Workerview.class));


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





