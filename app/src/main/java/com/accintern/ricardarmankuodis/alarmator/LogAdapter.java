package com.accintern.ricardarmankuodis.alarmator;

/*

 */
//

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/** */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder>{

    private List<LogMessage> mData;
    private Context mContext;

    public LogAdapter(List<LogMessage> data,Context context){
        mData=data;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO bind data
        LogMessage msg = mData.get(position);
        holder.textView.setText(msg.getMessage());

        int imageId=0;

        switch(msg.getCategory()){
            case -1:
                // Lifecycle
                imageId=R.drawable.ic_power_settings_new_black_24dp;
                break;
            case 1:
                // Alarm
                imageId=R.drawable.ic_alarm_black_24dp;
                break;
            case 2:
                // Battery
                imageId=R.drawable.ic_battery_charging_full_black_24dp;
                break;
        }

        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(imageId));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.msgTextView);
            imageView= (ImageView) itemView.findViewById(R.id.msgImageView);
        }
    }
}
