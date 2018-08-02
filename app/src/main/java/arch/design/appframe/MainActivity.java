package arch.design.appframe;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
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
                .subscribe(s -> Toast.makeText(getApplicationContext(), "Clicked on " + s, Toast.LENGTH_LONG).show());
    }

}
