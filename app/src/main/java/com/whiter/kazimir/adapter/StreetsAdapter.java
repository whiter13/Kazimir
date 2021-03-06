package com.whiter.kazimir.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.whiter.kazimir.R;
import com.whiter.kazimir.model.Place;
import com.whiter.kazimir.model.Street;
import com.whiter.kazimir.ui.view.SplitImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by whiter
 */
public class StreetsAdapter extends RecyclerView.Adapter<StreetsAdapter.ViewHolder> {

    private List<Street> streets;

    private Context context;

    public StreetsAdapter(Context context){
        streets = new ArrayList<>();
        this.context = context;
    }

    public void swapStreets(List<Street> streets){
        this.streets.clear();
        this.streets.addAll(streets);
        notifyDataSetChanged();
    }

    public Street getStreet(int position){
        return streets.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.street_row, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Street street = streets.get(position);
        List<Place> presentPlaces = street.getPlaces().getPresentPlaces();
        List<Place> pastPlaces = street.getPlaces().getPastPlaces();
        String mediumPast = pastPlaces.get(0).getPhotos().get(0).getImages().getMedium();
        String mediumPresent = presentPlaces.get(0).getPhotos().get(0).getImages().getMedium();

        Glide.with(context).load(mediumPast).into(holder.splitImageView.getLeftImage());
        Glide.with(context).load(mediumPresent).into(holder.splitImageView.getRightImage());

        holder.streetName.setText(street.getName());
    }

    @Override
    public int getItemCount() {
        return streets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.street_photo)
        SplitImageView splitImageView;

        @InjectView(R.id.street_name)
        TextView streetName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
