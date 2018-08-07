package arch.design.appframe.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import arch.design.appframe.data.ImageDataSet;
import arch.design.appframe.R;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by kurjakov on 30/07/2018.
 */

public class UpperMenuAdapter extends RecyclerView.Adapter<UpperMenuAdapter.ViewHolder> {
    private static final String TAG = "UpperMenuAdapter";
    private List<ImageDataSet> mImageDataSet;
    private PublishSubject<String> clickSubject = PublishSubject.create();
    private Disposable mSubscribe;

    public UpperMenuAdapter(List<ImageDataSet> imageDataSetList) {
        this.mImageDataSet = imageDataSetList;
        setItemClick();
    }

    public Observable<String> getClickEvent() {
        return clickSubject;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public TextView mTitle;

        public ViewHolder(View view) {
            super(view);
            mIcon = (ImageView) view.findViewById(R.id.card_image);
            mTitle = (TextView) view.findViewById(R.id.card_text);

            mSubscribe = RxView.clicks(view)
                    .debounce(400, TimeUnit.MILLISECONDS)
                    .subscribe(o -> clickSubject.onNext(mImageDataSet.get(getLayoutPosition()).getName()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageDataSet imageDataSet = mImageDataSet.get(position);
        holder.mTitle.setText(imageDataSet.getName());
        holder.mIcon.setImageDrawable(imageDataSet.getDrawable());
        Log.d(TAG, "onBindViewHolder: " + position);
    }

    @Override
    public int getItemCount() {
        return mImageDataSet.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mSubscribe.dispose();
    }

    void setItemClick() {
        mSubscribe = this.getClickEvent().subscribe(this::changeLayoutFocus);
    }


    private void changeLayoutFocus(String menuItem) {
        Log.d(TAG, "Cliecked: " + menuItem);
    }
}
