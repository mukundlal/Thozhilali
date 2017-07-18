package thozhilali.com.thozhilali;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android. app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchReviews extends Fragment {
    ListView lv;
    JSONArray jArray;
    ArrayList<ReviewData> arrayList;


    public SearchReviews() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reviews, container, false);
        lv=(ListView)view.findViewById(R.id.review_list);
        arrayList=new ArrayList<>();
        new  GetReviewsAsync().execute();

        return view;
    }
    class GetReviewsAsync extends AsyncTask<String, String, String> {
        private static final String LOGIN_URL = "Reviews.php";
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
            params.put("email",Searchresult.item);
            Log.d("request", params.toString());
            return new JSONParser().makeHttpRequest(Constants.API_URL + LOGIN_URL, "POST", params);


        }


        protected void onPostExecute(String json) {

            pd.dismiss();
            try {
                if (json.length() > 0) {

                    jArray = new JSONArray(json);
                    JSONObject json_data = null;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        String id = json_data.getString("review_id");
                        String mailFrom = json_data.getString("postedby");
                        String mailTo = json_data.getString("postedto");
                        String review = json_data.getString("Review");
                        String date = json_data.getString("Date");

                        ReviewData reviewData = new ReviewData(review,mailFrom,date);
                        arrayList.add(reviewData);
                    }
                    lv.setAdapter(new ReviewAdapter(getActivity(), arrayList));

                } else {
                    Toast.makeText(getActivity(), "error server down", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }


}
