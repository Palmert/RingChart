package co.palmer.rings.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import co.palmer.rings.R;
import co.palmer.rings.fragments.RingFragment;


public class RingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, RingFragment.newInstance())
                    .commit();
        }
    }
}
