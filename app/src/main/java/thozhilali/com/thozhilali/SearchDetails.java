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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SearchDetails extends Fragment {
    TextView name, mail, age, address, streetName, pinCode, ph, skills, loc,exp,wge,ex,wg,sk;
    String fullName, phone, email, skill, user_age, add, street, pin, city,experience,wage;
    JSONArray jArray;
    Button edit ;


    public SearchDetails() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_details, container, false);
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
        edit.setVisibility(View.GONE);
        new GetDataAsync().execute();
        // Inflate the layout for this fragment
        return view;
    } class GetDataAsync extends AsyncTask<String, String, String> {
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
            params.put("email", Searchresult.item);

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



                } else {
                    Toast.makeText(getActivity(), "error server down", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}
