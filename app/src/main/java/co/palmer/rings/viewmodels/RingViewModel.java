package co.palmer.rings.viewmodels;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Observable;

import co.palmer.rings.datamodels.Exercise;

/**
 * File created by Thom Palmer on 2015-03-18.
 */
public class RingViewModel extends Observable implements Parcelable{
    private static final String COUNTDOWN = "countDown";
    private static final int [] colors = {Color.RED, Color.WHITE};
    private static final ArrayList<String> labels = new ArrayList<String>(){{
        add("Completed Reps");
        add("Remaining Reps");
    }};
    private PieData mPieData;
    private String countDown;
    private String repCount;
    private Exercise exercise;
    private int currentSet;
    private int completedReps;
    private int remainingReps;
 

    public RingViewModel(Exercise exercise) {
        this.exercise = exercise;
        this.remainingReps = exercise.getExerciseReps();
        this.completedReps = 0;
        mPieData = new PieData(labels, generateChartData());
        setChanged();
        notifyObservers();
    }

    protected RingViewModel(Parcel in) {
        this.exercise = in.readParcelable(Exercise.class.getClassLoader());
        this.currentSet = in.readInt();
        this.remainingReps = in.readInt();
        this.completedReps = in.readInt();
    }
    
    public PieDataSet generateChartData() {
        repCount = String.valueOf(completedReps);
        Entry completedRepsEntry = new Entry(completedReps, 0);
        Entry totalRepsEntry = new Entry(remainingReps, 1);
        ArrayList<Entry> entriesList = new ArrayList<Entry>();
        entriesList.add(completedRepsEntry);
        entriesList.add(totalRepsEntry);
        PieDataSet pieDataSet = new PieDataSet(entriesList, exercise.getExerciseName());
        pieDataSet.setColors(colors);
        return pieDataSet;
    }
    
    public void increaseCompletedReps() {
        completedReps++;
        if(remainingReps > 0) {
            remainingReps--;
        }
        update();
    }
    public void decreaseCompletedReps() {
        if(completedReps > 0) {
            completedReps++;
        }
        remainingReps--;
        update();
    }
    
    public void completeSet() {
        completedReps = 0;
        remainingReps = exercise.getExerciseReps();
        currentSet++;
        CountDownTimer countDownTimer = new CountDownTimer(exercise.getExerciseRest() * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                countDown = String.valueOf(millisUntilFinished / 1000);
                update(COUNTDOWN);
            }

            @Override
            public void onFinish() {
                if(currentSet < exercise.getExerciseSets()) {

                }
            }
            
        };
        update();
        countDownTimer.start();
    }
    
    public String getCountdown() { return countDown; }
    
    public PieData getmPieData() {
        return  mPieData;
    }
    
    public String getRepCount() {
        return repCount;
    }
    
    public int getCompletedReps() {
        return completedReps;
    }

    private void update(String updateType) {
        mPieData.setDataSet(generateChartData());
        setChanged();
        notifyObservers(updateType);
    }
    
    private void update() {
        mPieData.setDataSet(generateChartData());
        setChanged();
        notifyObservers();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(exercise, flags);
        dest.writeInt(currentSet);
        dest.writeInt(remainingReps);
        dest.writeInt(completedReps);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RingViewModel> CREATOR = new Parcelable.Creator<RingViewModel>() {
        @Override
        public RingViewModel createFromParcel(Parcel in) {
            return new RingViewModel(in);
        }

        @Override
        public RingViewModel[] newArray(int size) {
            return new RingViewModel[size];
        }
    };
}
