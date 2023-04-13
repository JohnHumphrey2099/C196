package com.humphrey.c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humphrey.c196.DAO.TermDAO;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.R;


import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{
    public void setTermList(List<Term> termList) {
        this.termList = termList;
    }

    private List<Term> termList;
    private final Context context;

    private final LayoutInflater inflater;

    //constructor
    public TermAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    //inner class
    class TermViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TermViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.termRowTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term selectedTerm = termList.get(position);
                    Intent intent = new Intent(context, TermDetail.class);
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.term_recycler_row, parent, false);
        return new TermAdapter.TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(termList != null){
        holder.textViewTitle.setText(termList.get(position).getTitle());
        }
        else{
            holder.textViewTitle.setText("No Terms Added");
        }
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }
}
