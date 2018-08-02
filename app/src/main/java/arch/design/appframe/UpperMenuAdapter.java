package arch.design.appframe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by kurjakov on 30/07/2018.
 */

class UpperMenuAdapter extends RecyclerView.Adapter<UpperMenuAdapter.ViewHolder> {
    private List<ImageDataSet> mImageDataSet;
    private PublishSubject<String> clickSubject = PublishSubject.create();

    public UpperMenuAdapter(List<ImageDataSet> imageDataSetList) {
        this.mImageDataSet = imageDataSetList;
    }

    public Observable<String> getClickEvent() {
        return clickSubject;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public TextView mTitle;

        public ViewHolder(View view) {
            super(view);
            mIcon = (ImageView)view.findViewById(R.id.card_image);
            mTitle = (TextView) view.findViewById(R.id.card_text);
            view.setOnClickListener((View v) -> {
                clickSubject.onNext(mImageDataSet.get(getLayoutPosition()).getName());
            } );
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
    }

    @Override
    public int getItemCount() {
        return mImageDataSet.size();
    }

}
