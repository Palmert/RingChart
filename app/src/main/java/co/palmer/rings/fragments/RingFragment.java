package co.palmer.rings.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.palmer.rings.R;
import co.palmer.rings.datamodels.Exercise;
import co.palmer.rings.helpers.ChartTouchListener;
import co.palmer.rings.viewmodels.RingViewModel;


public class RingFragment extends Fragment implements OnChartGestureListener,Observer {
    public static final String VIEW_MODEL = "viewModel";
    public static final String EXERCISE = "exercise";
    private RingViewModel mRingViewModel;
    @InjectView(R.id.ring_txt_set_count)
    TextView setCountTxt;
    @InjectView(R.id.ring_chart)
    PieChart ringChart;


    public static RingFragment newInstance(Exercise exercise) {
        RingFragment ringFragment = new RingFragment();
        Bundle exerciseBundle = new Bundle();
        exerciseBundle.putParcelable(EXERCISE, exercise);
        ringFragment.setArguments(exerciseBundle);
        return ringFragment;
    }

    public RingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            mRingViewModel = new RingViewModel((Exercise)getArguments().getParcelable(EXERCISE), getActivity());
        } else {
            mRingViewModel = savedInstanceState.getParcelable(VIEW_MODEL);
            mRingViewModel.setContext(getActivity());
        }
        mRingViewModel.addObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ring, container, false);
        ButterKnife.inject(this, view);
        initializeRingChart();
        update(null, null);
        return view;
    }

    private void initializeRingChart() {

        ringChart.setHoleRadius(80);
        ringChart.setDescription(null);
        ringChart.setTouchEnabled(true);
        ringChart.setUsePercentValues(true);
        ringChart.setDrawCenterText(true);
        ringChart.setCenterTextColor(Color.RED);
        ringChart.setCenterTextSize(getResources().getDimension(R.dimen.abc_text_size_headline_material));
        ringChart.setOnTouchListener(new ChartTouchListener(ringChart,this));
        ringChart.setOnChartGestureListener(this);
        ringChart.setHoleColor(Color.LTGRAY);
        ringChart.setRotationEnabled(false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data == null) {
            ringChart.setData(mRingViewModel.getmPieData());
            ringChart.setCenterText(mRingViewModel.getRepCount());
            setCountTxt.setText(mRingViewModel.getSetCount());
            if (mRingViewModel.getCompletedReps() == 0) {
                ringChart.animateXY(1000, 1000);
            } else {
                ringChart.animateY(100);
            }
        } else {
            ringChart.setCenterText(mRingViewModel.getCountdown());
            ringChart.animateXY(1000, 1000);
        }
            
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(VIEW_MODEL, mRingViewModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        mRingViewModel.completeSet();
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {   
        mRingViewModel.decreaseCompletedReps();
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        mRingViewModel.increaseCompletedReps();
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        if(me1.getY() > me2.getY()) {
            mRingViewModel.increaseCompletedReps();
        } else {
            mRingViewModel.decreaseCompletedReps();
        }
    }
}
