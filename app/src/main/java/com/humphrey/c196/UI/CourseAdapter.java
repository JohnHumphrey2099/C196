package com.humphrey.c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humphrey.c196.Database.Repository;
import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.Entity.Term;
import com.humphrey.c196.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }
    public void setTermList(List<Term> termList){
        this.allTerms = termList;
    }
    private List<Course> courseList;
    private int termID;
    private final Context context;
    private final LayoutInflater inflater;
    private List<Term> allTerms;

    //constructor
    public CourseAdapter(Context context, int termID) {
        this.context = context;
        this.termID = termID;
        this.inflater = LayoutInflater.from(context);
    }

    //inner class
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private TextView courseViewTitle;
        private TextView termNameView;
        private CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseViewTitle = itemView.findViewById(R.id.courseRowTextView);
            termNameView = itemView.findViewById(R.id.courseRowTermName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course selectedCourse = courseList.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("id", selectedCourse.getCourseID());
                    intent.putExtra("title", selectedCourse.getTitle());
                    intent.putExtra("start", selectedCourse.getStartDate());
                    intent.putExtra("end", selectedCourse.getEndDate());
                    intent.putExtra("status", selectedCourse.getStatus());
                    intent.putExtra("name", selectedCourse.getInstructorName());
                    intent.putExtra("phone", selectedCourse.getInstructorPhone());
                    intent.putExtra("email", selectedCourse.getInstructorEmail());
                    intent.putExtra("note", selectedCourse.getNote());
                    if(selectedCourse.getCourseID() == 0) {
                        if (selectedCourse.getTermID() != 0) {
                            intent.putExtra("termID", selectedCourse.getTermID());
                        }
                        else {
                            intent.putExtra("termID", termID);
                        }
                    }
                    else{
                        intent.putExtra("termID", selectedCourse.getTermID());
                    }
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.course_recycler_row, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.courseViewTitle.setText(courseList.get(position).getTitle());
        boolean showTerm = false;
            for (Term t : allTerms) {
                if (t.getTermID() == courseList.get(position).getTermID()) {
                    holder.termNameView.setText(t.getTitle());
                    holder.termNameView.setVisibility(View.VISIBLE);
                    showTerm = true;
                    break;
                }
            }
        if (!showTerm) {
            holder.termNameView.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {

        if (courseList != null){
            return courseList.size();
        }
        else{
            return 0;
        }
    }
}

