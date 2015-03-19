package co.palmer.rings.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.palmer.rings.R;
import co.palmer.rings.datamodels.Exercise;
import co.palmer.rings.fragments.RingFragment;


public class RingActivity extends ActionBarActivity {
    private Exercise mExercise;
    @InjectView(R.id.activity_toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        ButterKnife.inject(this);
        toolbar.setBackgroundColor(Color.RED);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        mExercise = new Exercise("Bench Press", 8, 5, 3, 185);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, RingFragment.newInstance(mExercise))
                    .commit();
        }
    }
}
