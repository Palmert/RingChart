package co.palmer.rings.viewmodels;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Observable;

/**
 * File created by Thom Palmer on 2015-03-18.
 */
public class RingViewModel extends Observable implements Parcelable{
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
    private static final int [] colors = {Color.RED, Color.WHITE};
    private static final ArrayList<String> labels = new ArrayList<String>(){{
        add("Completed Reps");
        add("Remaining Reps");
    }};
    private PieData mPieData;
    private String workoutName;
    private String repCount;
    private int workoutReps;
    private int completedReps;
    private int remainingReps;

    public RingViewModel(String workoutName, int remainingReps) {
        this.workoutName = workoutName;
        this.workoutReps = remainingReps;
        this.remainingReps = remainingReps;
        mPieData = new PieData(labels, generateChartData());
        setChanged();
        notifyObservers();
    }

    protected RingViewModel(Parcel in) {
        this.workoutName = in.readString();
        this.workoutReps = in.readInt();
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
        PieDataSet pieDataSet = new PieDataSet(entriesList, workoutName);
        pieDataSet.setColors(colors);
        return pieDataSet;
    }
    
    public void increaseCompletedReps() {
        completedReps += 1;
        if(remainingReps > 0) {
            remainingReps -= 1;
        }
        update();
    }
    public void decreaseCompletedReps() {
        if(completedReps > 0) {
            completedReps -= 1;
        }
        remainingReps +=1;
        update();
    }
    
    public void resetCompletedReps() {
        completedReps = 0;
        remainingReps = workoutReps;
        update();
    }
    
    public PieData getmPieData() {
        return  mPieData;
    }
    
    public String getRepCount() {
        return repCount;
    }
    
    public int getCompletedReps() {
        return completedReps;
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
        dest.writeString(workoutName);
        dest.writeInt(workoutReps);
        dest.writeInt(remainingReps);
        dest.writeInt(completedReps);
    }
}
