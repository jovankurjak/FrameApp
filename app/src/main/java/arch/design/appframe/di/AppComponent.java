package arch.design.appframe.di;

import android.app.Application;

import arch.design.appframe.AppFrame;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by kurjakov on 15/08/2018.
 */

@Component( modules = {
        AndroidSupportInjectionModule.class,
        AppComponentModule.class,
        ActivityBuilder.class
})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(AppFrame appFrame);

    @Override
    default void inject(DaggerApplication instance) {

    }

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
