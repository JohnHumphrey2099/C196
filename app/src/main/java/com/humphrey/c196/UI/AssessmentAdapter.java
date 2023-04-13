package com.humphrey.c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humphrey.c196.Entity.Assessment;
import com.humphrey.c196.R;

import java.util.List;
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{
        public void setAssessmentList(List<Assessment> assessmentList) {
            this.assessmentList = assessmentList;
        }

        private List<Assessment> assessmentList;
        private final Context context;

        private final LayoutInflater inflater;

        //constructor
        public AssessmentAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        //inner class
        class AssessmentViewHolder extends RecyclerView.ViewHolder{
            private TextView assessmentViewTitle;
            private AssessmentViewHolder(@NonNull View itemView) {
                super(itemView);
                assessmentViewTitle = itemView.findViewById(R.id.assessmentRowTextView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        final Assessment selectedAssessment = assessmentList.get(position);
                        Intent intent = new Intent(context, AssessmentDetail.class);
                        context.startActivity(intent);
                    }
                });
            }
        }
        @NonNull
        @Override
        public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.assessment_recycler_row, parent, false);
            return new AssessmentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
            if(assessmentList != null){
                holder.assessmentViewTitle.setText(assessmentList.get(position).getTitle());
            }
            else{
                holder.assessmentViewTitle.setText("No Assessments Added");
            }
        }

        @Override
        public int getItemCount() {
            return assessmentList.size();
        }
}

