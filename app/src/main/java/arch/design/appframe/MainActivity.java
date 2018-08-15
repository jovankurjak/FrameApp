package arch.design.appframe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import javax.inject.Inject;

import arch.design.appframe.adapter.UpperMenuAdapter;
import arch.design.appframe.fragments.MapsFragment;
import arch.design.appframe.fragments.MediaFragment;
import arch.design.appframe.fragments.SettingsFragment;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.Disposable;

public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mMenuRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Disposable mSubscribe;

    @Inject
    UpperMenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMenuRecyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
        mMenuRecyclerView.setAdapter(mAdapter);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mMenuRecyclerView.setLayoutManager(mLayoutManager);

        setupItemClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscribe.dispose();
    }

    /**
     * Init methods
     * <p>
     * -prepare data
     * -do binding to get clicks from recyclerview(UpperMenuAdapter)
     */


    private void setupItemClick() {
        mSubscribe = mAdapter.getClickEvent()
                .subscribe(this::replaceFragment);
    }

    /**
     * @param tag fragment tag to create or replace
     */
    public void replaceFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        Log.d(TAG, "Creating/replacing " + tag + " fragment");
        if (fragment == null) {
            Log.d(TAG, "Creating new fragment");
            switch (tag) {
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
