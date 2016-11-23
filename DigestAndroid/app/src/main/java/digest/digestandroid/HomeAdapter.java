package digest.digestandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import digest.digestandroid.Models.Topic;

/**
 * Created by sahin on 22.11.2016.
 */

public class HomeAdapter extends RecyclerView
        .Adapter<HomeAdapter.DataObjectHolder>{

    private static String LOG_TAG = "HomeAdapter";
    private ArrayList<Topic> homeDataset;
    private static HomeClickListener homeClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public DataObjectHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.home_card_image);
            textView1 = (TextView) itemView.findViewById(R.id.home_text_view1);
            textView2 = (TextView) itemView.findViewById(R.id.home_text_view2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            homeClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(HomeClickListener homeClickListener){
        this.homeClickListener = homeClickListener;
    }
    public HomeAdapter(ArrayList<Topic> homeDataset){
        this.homeDataset = homeDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_card_view_row,parent,false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position){
        //holder.imageView.
        holder.textView1.setText(homeDataset.get(position).getTitle());
        holder.textView2.setText(homeDataset.get(position).getBody());
    }

    public void addItem(Topic dataObj, int index){
        homeDataset.add(index,dataObj);
        notifyItemInserted(index);
    }
    public void deleteItem(int index){
        homeDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount(){
        return homeDataset.size();
    }

    public interface HomeClickListener {
        public void onItemClick(int position, View v);
    }

}
