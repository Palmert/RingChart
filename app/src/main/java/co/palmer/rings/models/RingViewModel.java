package co.palmer.rings.models;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Observable;

/**
 * File created by Thom Palmer on 2015-03-18.
 */
public class RingViewModel extends Observable{
    private static final int [] colors = {Color.RED, Color.WHITE};
    private String workoutName = "Bench Press";
    private static final ArrayList<String> labels = new ArrayList<String>(){{
        add("");
        add("");
    }};
    private String repCount;
    private int completedReps = 0;
    private int totalReps = 10;
    private PieData mPieData;

    public RingViewModel() {
        mPieData = new PieData(labels, generateChartData());
        setChanged();
        notifyObservers();
    }
    
    public PieDataSet generateChartData() {
        repCount = String.valueOf(completedReps);
        Entry completedRepsEntry = new Entry(completedReps, 0);
        Entry totalRepsEntry = new Entry(totalReps, 1);
        ArrayList<Entry> entriesList = new ArrayList<Entry>();
        entriesList.add(completedRepsEntry);
        entriesList.add(totalRepsEntry);
        PieDataSet pieDataSet = new PieDataSet(entriesList, workoutName);
        pieDataSet.setColors(colors);
        return pieDataSet;
    }
    
    public void increaseCompletedReps() {
        completedReps += 1;
        if(totalReps > 0) {
            totalReps -= 1;
        }
        update();
    }
    public void decreaseCompletedReps() {
        if(completedReps > 0) {
            completedReps -= 1;
        }
        totalReps +=1;
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
    
    public void resetCompletedReps() {
        completedReps = 0;
        totalReps = 10;
        update();
    }

    private void update() {
        mPieData.setDataSet(generateChartData());
        setChanged();
        notifyObservers();
    }

}
