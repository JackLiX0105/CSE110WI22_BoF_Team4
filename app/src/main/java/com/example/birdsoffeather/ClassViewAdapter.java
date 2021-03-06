package com.example.birdsoffeather;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsoffeather.model.db.Courses;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ViewHolder> {

    private List<Courses> classes;
    private final Consumer<Courses> onClassRemoved;
    private Boolean deleteEnable;
    private boolean hideButton;

    public ClassViewAdapter(boolean hideButton, Boolean deleteEnable,List <Courses> classes, Consumer<Courses> onClassRemoved) {
        this.classes = classes;
        this.onClassRemoved = onClassRemoved;
        this.deleteEnable = deleteEnable;
        this.hideButton = hideButton;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View classesView = inflater.inflate(R.layout.classes_row, parent,false);
        ViewHolder viewHolder;
        if(deleteEnable) {
            viewHolder = new ViewHolder(classesView, this::removeClass, onClassRemoved);
        }
        else {
            viewHolder = new ViewHolder(classesView, hideButton);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setCourses(classes.get(position));
        Courses currClass = classes.get(position);
        String[] splitStr = currClass.course.split(" ");

        //set the ViewHolder according to the courses we get
        if(splitStr.length != 0) {
            TextView yrTextView = holder.year;
            yrTextView.setText(splitStr[0]);
            TextView qtTextView = holder.quarter;
            qtTextView.setText(splitStr[1]);
            TextView sjTextView = holder.subject;
            sjTextView.setText(splitStr[2]);
            TextView courTextView = holder.course;
            courTextView.setText(splitStr[3]);
            TextView szTextView = holder.size;
            szTextView.setText(splitStr[4].substring(0, 1));
        }
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public void addClass(Courses newClass) {
        this.classes.add(newClass);
        this.notifyItemInserted(this.classes.size()-1);
    }

    public void removeClass(int position) {
        this.classes.remove(position);
        this.notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView year, quarter, subject, course, size;
        private Courses courses;
        //ViewHolder with delete button
        public ViewHolder(View itemView, Consumer<Integer> removeClass,Consumer<Courses> onClassRemoved) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.year_row_txt);
            quarter = (TextView) itemView.findViewById(R.id.quarter_row_txt);
            subject = (TextView) itemView.findViewById(R.id.subject_row_txt);
            course  = (TextView) itemView.findViewById(R.id.course_row_txt);
            size    = (TextView) itemView.findViewById(R.id.size_row_txt);
            itemView.findViewById(R.id.removeClassButton);
            itemView.setOnClickListener((view) -> {
                removeClass.accept(this.getAdapterPosition());
                onClassRemoved.accept(courses);

            });
        }

        //ViewHolder without delete button
        public ViewHolder(View itemView, boolean hideButton) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.year_row_txt);
            quarter = (TextView) itemView.findViewById(R.id.quarter_row_txt);
            subject = (TextView) itemView.findViewById(R.id.subject_row_txt);
            course = (TextView) itemView.findViewById(R.id.course_row_txt);
            size    = (TextView) itemView.findViewById(R.id.size_row_txt);
            Button deleteButton = (Button)itemView.findViewById(R.id.removeClassButton);
            deleteButton.setText("->");
            deleteButton.setOnClickListener( (view) -> {
               goOnclick(view);
            });;
            if(hideButton){
                deleteButton.setVisibility(View.INVISIBLE);
            }
        }

        public void setCourses(Courses courses) {
            this.courses = courses;
        }

        public void goOnclick(View view){
            Context context = view.getContext();
            Intent intent = new Intent(context, ClassDetialsActivity.class);
            intent.putExtra("year", this.year.getText().toString());
            intent.putExtra("quarter", this.quarter.getText().toString());
            intent.putExtra("subject", this.subject.getText().toString());
            intent.putExtra("course", this.course.getText().toString());
            context.startActivity(intent);
        }
    }
}
