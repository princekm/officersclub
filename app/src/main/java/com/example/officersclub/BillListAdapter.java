package com.example.officersclub;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.officersclub.db.Bill;

import java.util.ArrayList;

public class BillListAdapter extends ArrayAdapter<Bill> implements View.OnClickListener{

    private ArrayList<Bill> dataSet;
    Context mContext;

    // View lookup cache

    public BillListAdapter(ArrayList<Bill> data, Context context) {
        super(context, R.layout.bill_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Bill dataModel=(Bill)object;

        switch (v.getId())
        {
         default:
                Snackbar.make(v, "Release date " +dataModel.getBillNo(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bill dataModel = dataSet.get(position);
        // Check if an existing view is being reused, otherwise inflate the view


        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.bill_item, parent, false);
        }
            TextView txtBillNo= (TextView) view.findViewById(R.id.billno);
            TextView txtBillInfo = (TextView) view.findViewById(R.id.billinfo);
        TextView txtBillAmount = (TextView) view.findViewById(R.id.billamount);
            txtBillNo.setText(new Integer(position+1).toString());
            txtBillInfo.setText("Bill no:"+dataModel.getBillNo()+","+dataModel.getDate());
            txtBillAmount.setText(dataModel.getAmount());



//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

   //     viewHolder.info.setOnClickListener(this);
     //   viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return view;
    }
}
