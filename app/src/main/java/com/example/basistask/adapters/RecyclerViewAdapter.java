package com.example.basistask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basistask.R;
import com.example.basistask.data.remote.modelClasses.DatumResponseModelClass;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<DatumResponseModelClass> dataList;

    public RecyclerViewAdapter(Context context,List<DatumResponseModelClass> dataList){
        this.dataList=dataList;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.swipe_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.heading.setText(dataList.get(position).getId());
        holder.content.setText(dataList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView heading,content;

        public MyViewHolder(View itemView){
            super(itemView);
            heading=itemView.findViewById(R.id.heading);
            content=itemView.findViewById(R.id.data_content);
        }
    }

}
