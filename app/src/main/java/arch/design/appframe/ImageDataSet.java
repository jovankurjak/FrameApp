package arch.design.appframe;

import android.graphics.drawable.Drawable;

/**
 * Created by kurjakov on 31/07/2018.
 */

public class ImageDataSet {
    private Drawable mDrawable;
    private String mName;

    public ImageDataSet(Drawable mDrawable, String mName) {
        this.mDrawable = mDrawable;
        this.mName = mName;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
