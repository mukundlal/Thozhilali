package thozhilali.com.thozhilali;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android. app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Edit extends Fragment {
    EditText nme, mailId, years, adrs, strt, pin_code, mobile, work, location,exp,wage;
    String fName, mail_id, old, Address, street_name, Pin, mob, work_type, locality,experience,wages;
    TextView sk,ex,wg;
    Button done;
    String fullName, phone, email, skill, user_age, add, street, pin, city,expr,wgs;
    JSONArray jArray;


    public Edit() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit, container, false);
        // Inflate the layout for this fragment
        nme = (EditText) view.findViewById(R.id.textView28);
        mailId = (EditText) view.findViewById(R.id.textView30);
        years = (EditText) view.findViewById(R.id.textView32);
        adrs = (EditText) view.findViewById(R.id.textView34);
        strt = (EditText) view.findViewById(R.id.textView37);
        pin_code = (EditText) view.findViewById(R.id.textView38);
        mobile = (EditText) view.findViewById(R.id.textView40);
        work = (EditText) view.findViewById(R.id.textView42);
        location = (EditText) view.findViewById(R.id.textView13);
        exp=(EditText) view.findViewById(R.id.textViewex);
        wage=(EditText) view.findViewById(R.id.textView46);
        ex =(TextView) view.findViewById(R.id.textViewe);
        wg =(TextView) view.findViewById(R.id.textView45);
        done = (Button) view.findViewById(R.id.btn_edit);
        sk=(TextView) view.findViewById(R.id.textView41);
        if (Login.type.equals("employer")) {
            work.setVisibility(View.GONE);
            ex .setVisibility(View.GONE);
            exp.setVisibility(View.GONE);
            wage.setVisibility(View.GONE);
            wg.setVisibility(View.GONE);
            sk.setVisibility(View.GONE);
        }
        new GetDataAsync().execute();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fName = nme.getText().toString();
                mail_id = mailId.getText().toString();
                mob = mobile.getText().toString();
                Address = adrs.getText().toString();
                old = years.getText().toString();
                street_name = strt.getText().toString();
                work_type = work.getText().toString();
                locality = location.getText().toString();
                Pin = pin_code.getText().toString();
                experience=exp.getText().toString();
                wages=wage.getText().toString();

                new UpdateDetailsAsync().execute();
            }
        });
        return view;
    }
    class GetDataAsync extends AsyncTask<String, String, String> {
        private static final String USER_URL = "userDetails.php";
        ProgressDialog pd;

        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
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
                        skill = json_data.getString("Skills");
                        add = json_data.getString("Address");
                        city = json_data.getString("city");
                        street = json_data.getString("StreetName");
                        pin = json_data.getString("PinCode");
                        wgs=json_data.getString("wages");
                        expr=json_data.getString("experience");

                        if (!fullName.equals("")) {
                            nme.setText(fullName);
                        } else {
                            nme.setText("");
                        }

                        if (!email.equals("")) {
                            mailId.setText(email);
                        } else {
                            mailId.setText("");
                        }
                        if (!phone.equals("")) {
                           mobile.setText(phone);
                        } else {
                           mobile.setText("");
                        }
                        if (!add.equals("")) {
                            adrs.setText(add);
                        } else {
                            adrs.setText("");
                        }
                        if (!user_age.equals("")) {
                            years.setText(user_age);
                        } else {
                           years.setText("");
                        }
                        if (!city.equals("")) {
                            location.setText(city);
                        } else {
                            location.setText("");
                        }
                        if (!street.equals("")) {
                            strt.setText(street);
                        } else {
                            strt.setText("");
                        }

                        if (!pin.equals("")) {
                            pin_code.setText(pin);
                        } else {
                           pin_code.setText("");
                        }
                        if (Login.type.equals("employee")) {
                            if (!skill.equals("")) {
                                work.setText(skill);
                            } else {
                                work.setText("");
                            }
                            if (!wgs.equals("0")) {
                                wage.setText(wgs);
                            } else {
                                wage.setText("");
                            }
                            if (!expr.equals("0")) {
                                exp.setText(expr);
                            } else {
                                exp.setText("");
                            }
                        }

                    }

                } else {
                    Toast.makeText(getActivity(), "error server down", Toast.LENGTH_LONG).show();
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
            pd = new ProgressDialog(getActivity());
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
            args.put("work", work_type);
            args.put("age", old);
            args.put("ex",experience);
            args.put("wage",wages);
            args.put("pin",Pin);
            args.put("type",Login.type);
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

                        Toast.makeText(getActivity(), "Your details Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "error server down", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


}
