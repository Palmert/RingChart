package co.palmer.rings.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * File created by Thom Palmer on 2015-03-18.
 */
public class Exercise implements Parcelable {

    private String exerciseName;
    private int exerciseReps;
    private int exerciseRest;
    private int exerciseSets;
    private int exerciseWeight;
    
    public Exercise(String exerciseName, int exerciseReps, int exerciseRest, int exerciseSets, int exerciseWeight) {
        this.exerciseName = exerciseName;
        this.exerciseReps = exerciseReps;
        this.exerciseRest = exerciseRest;
        this.exerciseSets = exerciseSets;
        this.exerciseWeight = exerciseWeight;
    }
    
    protected Exercise(Parcel in) {
        this.exerciseName = in.readString();
        this.exerciseReps = in.readInt();
        this.exerciseRest = in.readInt();
        this.exerciseSets = in.readInt();
        this.exerciseWeight = in.readInt();
    }
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getExerciseReps() {
        return exerciseReps;
    }

    public void setExerciseReps(int exerciseReps) {
        this.exerciseReps = exerciseReps;
    }

    public int getExerciseRest() {
        return exerciseRest;
    }

    public void setExerciseRest(int exerciseRest) {
        this.exerciseRest = exerciseRest;
    }

    public int getExerciseSets() {
        return exerciseSets;
    }

    public void setExerciseSets(int exerciseSets) {
        this.exerciseSets = exerciseSets;
    }

    public int getExerciseWeight() {
        return exerciseWeight;
    }

    public void setExerciseWeight(int exerciseWeight) {
        this.exerciseWeight = exerciseWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exerciseName);
        dest.writeInt(exerciseReps);
        dest.writeInt(exerciseRest);
        dest.writeInt(exerciseSets);
        dest.writeInt(exerciseWeight);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}
