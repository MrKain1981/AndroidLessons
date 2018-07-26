package ru.geekbrains.sergey.kiselev.summerworkouts.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.sergey.kiselev.summerworkouts.R;

public class WorkoutList {
    private static List<Workout> workouts;

    private static WorkoutList ourInstance;

    public static WorkoutList getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new WorkoutList(context);
        }
        return ourInstance;
    }

    private WorkoutList(Context context) {
        initMockList(context);
    }

    private void initMockList(Context context) {
        workouts = new ArrayList<>();

        workouts.add(new Workout(context.getString(R.string.pull_up_title), context.getString(R.string.pull_up_description), R.mipmap.push_up,
                context.getString(R.string.workout_difficult_easy), 0, 0));
        workouts.add(new Workout(context.getString(R.string.push_up_title), context.getString(R.string.push_up_description), R.mipmap.pull_up,
                context.getString(R.string.workout_difficult_easy), 0, 0));
        workouts.add(new Workout(context.getString(R.string.squats_title), context.getString(R.string.squats_description), R.mipmap.squats,
                context.getString(R.string.workout_difficult_easy), 0, 0));
    }

    public Workout getWorkout(int index) {
        return workouts.get(index);
    }

    public List<Workout> getAllWorkouts() {
        return workouts;
    }

    public void setWorkout(int index, Workout workout) {
        workouts.set(index, workout);
    }
}
