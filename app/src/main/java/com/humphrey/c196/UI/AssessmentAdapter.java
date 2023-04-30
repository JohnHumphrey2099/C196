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
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.R;

import java.util.List;
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{


        private List<Assessment> assessmentList;
        private final Context context;

        private final LayoutInflater inflater;
        private int courseID;
        private List<Course> allCourses;

        //constructor
        public AssessmentAdapter(Context context, int courseID) {
            this.context = context;
            this.courseID = courseID;
            this.inflater = LayoutInflater.from(context);
        }

        //inner class
        class AssessmentViewHolder extends RecyclerView.ViewHolder{
            private TextView assessmentViewTitle;
            private TextView assessCourseName;
            private AssessmentViewHolder(@NonNull View itemView) {
                super(itemView);
                assessmentViewTitle = itemView.findViewById(R.id.assessmentRowTextView);
                assessCourseName = itemView.findViewById(R.id.assessRowCourseName);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        final Assessment selectedAssessment = assessmentList.get(position);
                        Intent intent = new Intent(context, AssessmentDetail.class);
                        intent.putExtra("courseID", courseID);
                        intent.putExtra("id", selectedAssessment.getAssessmentID());
                        intent.putExtra("title", selectedAssessment.getTitle());
                        intent.putExtra("type", selectedAssessment.getType());
                        intent.putExtra("start", selectedAssessment.getStartDate());
                        intent.putExtra("end", selectedAssessment.getEndDate());
                        intent.putExtra("position", position);
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
            holder.assessmentViewTitle.setText(assessmentList.get(position).getTitle());
            boolean showCourse = false;
            for (Course c : allCourses) {
                if (c.getCourseID() == assessmentList.get(position).getCourseID()) {
                    holder.assessCourseName.setText(c.getTitle());
                    holder.assessCourseName.setVisibility(View.VISIBLE);
                    showCourse = true;
                    break;
                }
            }
            if (!showCourse) {
                holder.assessCourseName.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            if (assessmentList!= null){
                return assessmentList.size();
            }
            else{
                return 0;
            }
        }
    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
        notifyDataSetChanged();
    }
    public void setAllCourses(List<Course> allCourses){
            this.allCourses = allCourses;
    }
}

