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
 * Created by SEMI on 4/26/2017.
 */

public class WorkerAdapter  extends BaseAdapter {


    ArrayList<WorkerDetails> arrayList;
    private LayoutInflater inflater;
    Context ctx;
   WorkerDetails CD=new WorkerDetails();

    public WorkerAdapter(Context context,ArrayList<WorkerDetails> arrayList) {
        this.arrayList = arrayList;
        ctx=context;
        inflater=LayoutInflater.from(context);
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
        CD=arrayList.get(position);
        if(convertView==null) {
            convertView =inflater.inflate(R.layout.worker,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.location=(TextView)convertView.findViewById(R.id.textView43);
            viewHolder.name=(TextView)convertView.findViewById(R.id.textView14);
            viewHolder.photo=(ImageView)convertView.findViewById(R.id.imageView3);



            convertView.setTag(viewHolder);

        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();

        }
        viewHolder.name.setText(CD.getName());
        viewHolder.location.setText( CD.getCity());




        return convertView;
    }
    static class ViewHolder{
        TextView name,location ;
        ImageView photo;
        Button add,delete;
    }


}
