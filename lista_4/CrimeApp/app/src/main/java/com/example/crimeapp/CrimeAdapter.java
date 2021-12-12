package com.example.crimeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.CrimeViewHolder> {
    public List<Crime> crimes;
    private LayoutInflater inflater;
    private Context context;


    public CrimeAdapter(Context context, List<Crime> crimes){
        inflater = LayoutInflater.from(context);
        this.crimes = crimes;
        this.context = context;
    }

    public class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView crimeName;
        public TextView crimeDate;
        public ImageView handcuffs;
        public CrimeAdapter adapter;

        public CrimeViewHolder(@NonNull View itemView,CrimeAdapter adapter)
        {
            super(itemView);
            crimeName = itemView.findViewById(R.id.crimeName);
            crimeDate = itemView.findViewById(R.id.crimeDate);
            handcuffs = itemView.findViewById(R.id.handcuffs);
            this.adapter = adapter;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Crime current = crimes.get(position);
            Intent intent = new Intent(context, CrimeActivity.class);
            intent.putExtra("id", current.getId().toString());
            context.startActivity(intent);
        }
    }


    @NonNull
    @Override
    public CrimeAdapter.CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.crime_list_item,parent,false);
        return new CrimeViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeAdapter.CrimeViewHolder holder, int position) {
        Crime current = crimes.get(position);
        holder.crimeName.setText(current.getTitle());
        holder.crimeDate.setText(current.getDate().toString());
        if(current.getSolved()==true){
            holder.handcuffs.setVisibility(View.VISIBLE);
        }else{
            holder.handcuffs.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return crimes.size();
    }

}