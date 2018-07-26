package ru.geekbrains.sergey.kiselev.summerworkouts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.sergey.kiselev.summerworkouts.model.Workout;
import ru.geekbrains.sergey.kiselev.summerworkouts.model.WorkoutList;
import ru.geekbrains.sergey.kiselev.summerworkouts.utils.Constants;

public class WorkoutDetailActivity extends AppCompatActivity {

    private TextView title;
    private TextView description;
    private TextView difficult;
    private TextView repsCount;
    private ImageView imageView;
    private ImageView popupMenu;
    private SeekBar repsCountSeekBar;
    private TextView lastRecordRepCount;
    private TextView lastRecordDate;

    private int workoutIndex;
    private Workout workout;

    private static final String TAG = "WorkoutDetAct_DEBUG";
    private static final String DATE_PATTERN = "dd.MM.yyyy hh:mm:ss";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendToLog("onCreate executed");

        setContentView(R.layout.activity_detail_workout);

        Intent intent = getIntent();
        workoutIndex = intent.getIntExtra(Constants.WORKOUT_INDEX, 0);

        initUI();
    }

    private void initUI() {
        workout = WorkoutList.getInstance(this).getWorkout(workoutIndex);

        title = findViewById(R.id.workout_detail_title);
        title.setText(workout.getTitle());

        description = findViewById(R.id.workout_detail_description);
        description.setText(workout.getDescription());

        difficult = findViewById(R.id.workout_detail_difficult);
        difficult.setText(workout.getDifficult());

        repsCount = findViewById(R.id.workout_detail_repeats_count);
        repsCount.setText(String.valueOf(workout.getRepeatsCount()));

        repsCountSeekBar = findViewById(R.id.workout_detail_seek_bar);
        repsCountSeekBar.setProgress(workout.getRepeatsCount());

        repsCountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                repsCount.setText(String.valueOf(progress));
                WorkoutList workoutList = WorkoutList.getInstance(WorkoutDetailActivity.this);
                workout.setRepeatsCount(progress);
                workoutList.setWorkout(workoutIndex, workout);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        popupMenu = findViewById(R.id.workout_detail_popup_menu);
        popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });


        imageView = findViewById(R.id.workout_detail_image);
        imageView.setImageResource(workout.getImageResRef());

        lastRecordRepCount = findViewById(R.id.workout_detail_last_record_repeats);
        lastRecordDate = findViewById(R.id.workout_detail_last_record_date);

        lastRecordRepCount.setText(String.valueOf(workout.getLastRecordRepeats()));
        Date date = workout.getLastRecordDate();
        if (date != null) {
            lastRecordDate.setText(new SimpleDateFormat(DATE_PATTERN, Locale.ROOT).format(date));
        }

    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.workout_detail_popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.workout_detail_popup_save: {
                        saveRecord();
                        return true;
                    }
                    case R.id.workout_detail_popup_clear: {
                        clearRecord();
                        return true;
                    }
                    case R.id.workout_detail_popup_send: {
                        sendRecord();
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void sendRecord() {
        if (workout.getLastRecordRepeats() > 0) {
            String text = String.format(getString(R.string.my_new_record_string), workout.getTitle(),
                    workout.getLastRecordRepeats(),
                    new SimpleDateFormat(DATE_PATTERN, Locale.ROOT).format(workout.getLastRecordDate()));

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");

            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            } else {
                Toast.makeText(this, R.string.nobody_to_send, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, R.string.send_zero, Toast.LENGTH_SHORT).show();
        }

    }

    private void clearRecord() {
        workout.setLastRecordRepeats(0);
        lastRecordRepCount.setText("0");

        workout.setLastRecordDate(null);
        lastRecordDate.setText("");
    }

    private void saveRecord() {
        int repCount = workout.getRepeatsCount();
        Date date = new Date();
        if (repCount > 0 && workout.getLastRecordRepeats() < repCount) {
            workout.setLastRecordRepeats(repCount);
            lastRecordRepCount.setText(String.valueOf(repCount));

            workout.setLastRecordDate(date);
            lastRecordDate.setText(new SimpleDateFormat(DATE_PATTERN, Locale.ROOT).format(date));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendToLog("onStart executed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sendToLog("onPause executed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendToLog("onResume executed");
    }

    @Override
    protected void onStop() {
        super.onStop();
        sendToLog("onStop executed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sendToLog("onRestart executed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendToLog("onDestroy executed");
    }

    private void sendToLog(String mess) {
        Log.i(TAG, mess);
    }
}
