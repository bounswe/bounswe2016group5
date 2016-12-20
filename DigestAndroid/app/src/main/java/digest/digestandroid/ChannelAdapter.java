package digest.digestandroid;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import digest.digestandroid.Models.Channel;
import digest.digestandroid.api.VolleySingleton;

/**
 * Created by sahin on 19.12.2016.
 */

public class ChannelAdapter extends RecyclerView
        .Adapter<ChannelAdapter.DataObjectHolder>{

    private static String LOG_TAG = "ChannelAdapter";
    private ArrayList<Channel> channelDataset;
    private static ChannelAdapter.ChannelClickListener channelClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView1;
        TextView textView2;

        public DataObjectHolder(View itemView){
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.channel_text_view1);
            textView2 = (TextView) itemView.findViewById(R.id.channel_text_view2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            channelClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ChannelAdapter.ChannelClickListener channelClickListener){
        this.channelClickListener = channelClickListener;
    }
    public ChannelAdapter(ArrayList<Channel> channelDataset){
        this.channelDataset = channelDataset;
        Log.d("In channel adapter",""+channelDataset.size());
        Log.d("In channel adapter",""+channelDataset);
    }

    @Override
    public ChannelAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_card_view_row,parent,false);
        ChannelAdapter.DataObjectHolder dataObjectHolder = new ChannelAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.DataObjectHolder holder, int position){

        holder.textView1.setText(channelDataset.get(position).getName());
        holder.textView2.setText(""+channelDataset.get(position).getId());

    }

    public void addItem(Channel dataObj, int index){
        channelDataset.add(index,dataObj);
        notifyItemInserted(index);
    }
    public void deleteItem(int index){
        channelDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount(){
        return channelDataset.size();
    }

    public interface ChannelClickListener {
        public void onItemClick(int position, View v);
    }
}
