package ru.geekbrains.sergey.kiselev.summerworkouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.sergey.kiselev.summerworkouts.model.Workout;
import ru.geekbrains.sergey.kiselev.summerworkouts.model.WorkoutList;
import ru.geekbrains.sergey.kiselev.summerworkouts.utils.Constants;

public class WorkoutListActivity extends AppCompatActivity implements View.OnClickListener {

    List<Workout> workoutList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        workoutList = WorkoutList.getInstance(this).getAllWorkouts();

        LinearLayout linear = findViewById(R.id.linear_main);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < workoutList.size(); i++) {
            Button btn = new Button(getApplicationContext());
            btn.setText(workoutList.get(i).getTitle());
            btn.setTag(i);
            btn.setOnClickListener(this);
            linear.addView(btn, param);
        }

    }

    @Override
    public void onClick(View v) {
        Intent startDetailActivity = new Intent(WorkoutListActivity.this, WorkoutDetailActivity.class);
        startDetailActivity.putExtra(Constants.WORKOUT_INDEX, (Integer) v.getTag());
        startActivity(startDetailActivity);
    }
}
