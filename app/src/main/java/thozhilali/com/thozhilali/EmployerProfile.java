package thozhilali.com.thozhilali;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EmployerProfile extends AppCompatActivity {
    TextView name, mail, age, address, streetName, pinCode, ph,   loc;
    String fullName, phone, email,  user_age, add, street, pin, city;
    JSONArray jArray;
    Button edit, done;
    EditText nme, mailId, years, adrs, strt, pin_code, mobile,work,   location;
    String fName, mail_id, old, Address, street_name, Pin, mob,  locality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);
        name = (TextView)   findViewById(R.id.textView28);
        mail = (TextView)  findViewById(R.id.textView30);
        age = (TextView)  findViewById(R.id.textView32);
        address = (TextView)  findViewById(R.id.textView34);
        streetName = (TextView)  findViewById(R.id.textView37);
        pinCode = (TextView)  findViewById(R.id.textView38);
        ph = (TextView)  findViewById(R.id.textView40);

        loc = (TextView)  findViewById(R.id.textView13);
        edit = (Button)  findViewById(R.id.btn_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setContentView(R.layout.edit_details);
                nme = (EditText)  findViewById(R.id.textView28);
                mailId = (EditText)  findViewById(R.id.textView30);
                years = (EditText) findViewById(R.id.textView32);
                adrs = (EditText)  findViewById(R.id.textView34);
                strt = (EditText)  findViewById(R.id.textView37);
                pin_code = (EditText) findViewById(R.id.textView38);
                mobile = (EditText)  findViewById(R.id.textView40);
                work = (EditText)  findViewById(R.id.textView42);
                location = (EditText) findViewById(R.id.textView13);
                done = (Button)  findViewById(R.id.btn_edit);
                work.setVisibility(View.GONE);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fName = nme.getText().toString();
                        mail_id = mailId.getText().toString();
                        mob = mobile.getText().toString();
                        Address = adrs.getText().toString();
                        old = years.getText().toString();
                        street_name = strt.getText().toString();

                        locality = location.getText().toString();
                        Pin = pin_code.getText().toString();
                        new UpdateDetailsAsync().execute();
                    }
                });
            }
        });

        new  GetDataAsync().execute();

    }
    class GetDataAsync extends AsyncTask<String, String, String> {
        private static final String USER_URL = "userDetails.php";
        ProgressDialog pd;

        protected void onPreExecute() {
            pd = new ProgressDialog(getApplicationContext());
            pd.setMessage("Loading...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... args) {
            HashMap<String, String> params = new HashMap<>();
            params.put("email", Login.mail);

            return new JSONParser().makeHttpRequest(Constants.API_URL + USER_URL, "POST", params);


        }


        protected void onPostExecute(String json) {

            pd.dismiss();
            try {

                if (json.length() > 0) {


                    jArray = new JSONArray(json);
                    JSONObject json_data = null;
                    for (int i = 0; i < jArray.length(); i++) {

                        json_data = jArray.getJSONObject(i);
                        fullName = json_data.getString("Name");
                        email = json_data.getString("Email");
                        phone = json_data.getString("Mobile");
                        user_age = json_data.getString("Age");
                        add = json_data.getString("Address");
                        city = json_data.getString("city");
                        street = json_data.getString("StreetName");
                        pin = json_data.getString("PinCode");


                        if (!fullName.equals("")) {
                            name.setText(fullName);
                        } else {
                            name.setText("");
                        }

                        if (!email.equals("")) {
                            mail.setText(email);
                        } else {
                            mail.setText("");
                        }
                        if (!phone.equals("")) {
                            ph.setText(phone);
                        } else {
                            ph.setText("");
                        }
                        if (!add.equals("")) {
                            address.setText(add);
                        } else {
                            address.setText("");
                        }
                        if (!user_age.equals("")) {
                            age.setText(user_age);
                        } else {
                            age.setText("");
                        }
                        if (!city.equals("")) {
                            loc.setText(city);
                        } else {
                            loc.setText("");
                        }
                        if (!street.equals("")) {
                            streetName.setText(street);
                        } else {
                            streetName.setText("");
                        }

                        if (!pin.equals("")) {
                            pinCode.setText(pin);
                        } else {
                            pinCode.setText("");
                        }


                    }

                } else {
                    Toast.makeText(getApplicationContext(), "error server down", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private class UpdateDetailsAsync extends AsyncTask<String, String, String> {
        private static  final String UPDATE="Edit.php";
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getApplicationContext());
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.setMessage("Loading....");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> args = new HashMap<>();
            args.put("email", Login.mail);
            args.put("name", fName);
            args.put("ph", mob);
            args.put("address", Address);
            args.put("city", locality);
            args.put("street", street_name);

            args.put("age", old);
            args.put("pin",Pin);
            args.put("type","employee");
            return new JSONParser().makeHttpRequest(Constants.API_URL + UPDATE, "POST", args);
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            int success;
            String message;
            pd.dismiss();
            try {
                if (json.length() > 0) {
                    JSONObject jsonObject = new JSONObject(json);

                    success = jsonObject.getInt(Constants.TAG_SUCCESS);
                    message = jsonObject.getString(Constants.TAG_MESSAGE);
                    if (success == 1) {

                        Toast.makeText(getApplicationContext(), "Your details Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext() , "error server down", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
