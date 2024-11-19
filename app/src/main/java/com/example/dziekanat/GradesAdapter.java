package com.example.dziekanat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dziekanat.dto.GradeDTO;

import java.util.List;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradeViewHolder> {
    private List<GradeDTO> gradeList;

    public GradesAdapter(List<GradeDTO> gradeList) {
        this.gradeList = gradeList;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_item, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        GradeDTO grade = gradeList.get(position);
        holder.className.setText(grade.getClassName());
        holder.grade.setText(String.valueOf(grade.getGrade()));
        holder.date.setText(grade.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView className, grade, date;

        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.textViewClassName);
            grade = itemView.findViewById(R.id.textViewGrade);
            date = itemView.findViewById(R.id.textViewDate);
        }
    }
}
