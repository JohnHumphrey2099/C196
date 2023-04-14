package com.humphrey.c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humphrey.c196.Entity.Course;
import com.humphrey.c196.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    private List<Course> courseList;
    private final Context context;

    private final LayoutInflater inflater;

    //constructor
    public CourseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    //inner class
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private TextView courseViewTitle;
        private CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseViewTitle = itemView.findViewById(R.id.courseRowTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course selectedCourse = courseList.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
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
        if(courseList != null){
            holder.courseViewTitle.setText(courseList.get(position).getTitle());
        }
        else{
            holder.courseViewTitle.setText("No Courses Added");
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

    public void setCourses(List<Course> courses){
        courseList = courses;
        notifyDataSetChanged();
    }
}

