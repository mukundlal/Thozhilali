package thozhilali.com.thozhilali;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SEMI on 4/30/2017.
 */

public class ReviewAdapter extends BaseAdapter {
    private static final String ADD = "AddCharity.php";
    String type;
    ArrayList<ReviewData> arrayList;
    private LayoutInflater inflater;
    Context ctx;
    ReviewData CD = new ReviewData();

    public ReviewAdapter(Context context, ArrayList<ReviewData> arrayList) {
        this.arrayList = arrayList;
        ctx = context;
        inflater = LayoutInflater.from(context);
    }

    @Override

    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        CD = arrayList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.review_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.textView26);
            viewHolder.review = (TextView) convertView.findViewById(R.id.textView16);
            viewHolder.date = (TextView) convertView.findViewById(R.id.textView17);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.name.setText(CD.getReviewed_by());
        viewHolder.review.setText(CD.getReview());
        viewHolder.date.setText(CD.getDate());

        return convertView;
    }

    static class ViewHolder {
        TextView name, review, date;
        ImageView photo;
        Button add, delete;
    }

}
