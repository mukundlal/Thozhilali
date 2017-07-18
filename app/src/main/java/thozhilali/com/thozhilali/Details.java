package thozhilali.com.thozhilali;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class Details extends Fragment {
    TextView name, mail, age, address, streetName, pinCode, ph, skills, loc,exp,wge,ex,wg,sk;
    String fullName, phone, email, skill, user_age, add, street, pin, city,experience,wage;
    JSONArray jArray;
    Button edit ;

    View view;


    public Details() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.fragment_details, container, false);
        name = (TextView) view.findViewById(R.id.textView28);
        mail = (TextView) view.findViewById(R.id.textView30);
        age = (TextView) view.findViewById(R.id.textView32);
        address = (TextView) view.findViewById(R.id.textView34);
        streetName = (TextView) view.findViewById(R.id.textView37);
        pinCode = (TextView) view.findViewById(R.id.textView38);
        ph = (TextView) view.findViewById(R.id.textView40);
        skills = (TextView) view.findViewById(R.id.textView42);
        loc = (TextView) view.findViewById(R.id.textView13);
        edit = (Button) view.findViewById(R.id.btn_edit);
        sk=(TextView) view.findViewById(R.id.textView41);
        exp=(TextView) view.findViewById(R.id.textViewex);
        wge=(TextView) view.findViewById(R.id.textView46);
        ex =(TextView) view.findViewById(R.id.textViewe);
        wg =(TextView) view.findViewById(R.id.textView45);
        if (Login.type.equals("employer")) {
            skills.setVisibility(View.GONE);
            ex .setVisibility(View.GONE);
            exp.setVisibility(View.GONE);
            wge.setVisibility(View.GONE);
            wg.setVisibility(View.GONE);
            sk.setVisibility(View.GONE);
        }
        new GetDataAsync().execute();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit ef = new Edit();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.l1, ef).commit();
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
                        wage=json_data.getString("wages");
                        experience=json_data.getString("experience");


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
                        if (Login.type.equals("employee")) {
                            if (!skill.equals("")) {
                                skills.setText(skill);
                            } else {
                                skills.setText("");
                            }
                            if (!wage.equals("0")) {
                                wge.setText(wage);
                            } else {
                                wge.setText("");
                            }
                            if (!experience.equals("0")) {
                                exp.setText(experience);
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


}
