package arch.design.appframe;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import arch.design.appframe.fragments.MapsFragment;
import arch.design.appframe.fragments.MediaFragment;
import arch.design.appframe.fragments.SettingsFragment;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mMenuRecyclerView;
    private UpperMenuAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Disposable mSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMenuRecyclerView = (RecyclerView) findViewById(R.id.recycler_menu);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mMenuRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new UpperMenuAdapter(prepareDataSet());
        mMenuRecyclerView.setAdapter(mAdapter);
        setupItemClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscribe.dispose();
    }

    /**
     * Init methods
     */
    private List<ImageDataSet> prepareDataSet() {
        List<ImageDataSet> dataSet = new ArrayList<>();
        Resources res = getResources();
        dataSet.add(new ImageDataSet(res.getDrawable(R.drawable.settings_outline), "Settings"));
        dataSet.add(new ImageDataSet(res.getDrawable(R.drawable.music_circle), "Media"));
        dataSet.add(new ImageDataSet(res.getDrawable(R.drawable.google_maps), "Maps"));
        return dataSet;
    }



    private void setupItemClick() {
        mSubscribe = mAdapter.getClickEvent()
                .subscribe(this::replaceFragment);
    }
    public void replaceFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        Log.d(TAG, "Creating/replacing " + tag + " fragment");
        if (fragment == null) {
            Log.d(TAG, "Creating new fragment");
            switch (tag){
                case "Settings":
                    fragment = new SettingsFragment();
                    break;
                case "Media":
                    fragment = new MediaFragment();
                    break;
                case "Maps":
                    fragment = new MapsFragment();
                    break;
            }
        }
        fragmentTransaction.replace(R.id.main_frame, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
