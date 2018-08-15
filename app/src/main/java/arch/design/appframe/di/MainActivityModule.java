package arch.design.appframe.di;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import arch.design.appframe.MainActivity;
import arch.design.appframe.R;
import arch.design.appframe.adapter.UpperMenuAdapter;
import arch.design.appframe.data.ImageDataSet;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kurjakov on 15/08/2018.
 */

@Module
public class MainActivityModule {

    @Provides
    UpperMenuAdapter provideUpperMenuAdapter(MainActivity activity) {
        return new UpperMenuAdapter(provideImageDataSetList(activity));
    }

    @Provides
    List<ImageDataSet> provideImageDataSetList(Context context){
        List<ImageDataSet> dataSet = new ArrayList<>();
        Resources res = context.getResources();
        dataSet.add(new ImageDataSet(res.getDrawable(R.drawable.settings_outline), "Settings"));
        dataSet.add(new ImageDataSet(res.getDrawable(R.drawable.music_circle), "Media"));
        dataSet.add(new ImageDataSet(res.getDrawable(R.drawable.google_maps), "Maps"));
        return dataSet;
    }
}
