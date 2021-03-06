package com.whiter.kazimir.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.whiter.kazimir.App;
import com.whiter.kazimir.R;
import com.whiter.kazimir.adapter.PlaceListFragmentPagerAdapter;
import com.whiter.kazimir.databinding.PlaceListActivityBinding;
import com.whiter.kazimir.model.Place;
import com.whiter.kazimir.model.Street;
import com.whiter.kazimir.ui.fragment.PlaceListFragment;
import com.whiter.kazimir.utils.Intents;
import com.whiter.kazimir.viewmodel.PlaceListViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by whiter
 */
public class PlaceListActivity extends AppCompatActivity implements PlaceListFragment.Contract {

    @Inject
    Intents intents;

    private Street street;
    private PlaceListActivityBinding placeListActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeListActivityBinding = DataBindingUtil.setContentView(this, R.layout.place_list_activity);
        App.component().inject(this);
        setSupportActionBar(placeListActivityBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        street = intents.getStreet(getIntent());
        setupViewBinding();
    }

    private void setupViewBinding() {
        List<Place> presentPlaces = street.getPlaces().getPresentPlaces();
        List<Place> pastPlaces = street.getPlaces().getPastPlaces();

        PlaceListFragmentPagerAdapter placeListFragmentPagerAdapter = new PlaceListFragmentPagerAdapter(getSupportFragmentManager());
        placeListFragmentPagerAdapter.addPlaceListFragment(PlaceListFragment.newInstance(pastPlaces), getString(R.string.past));
        placeListFragmentPagerAdapter.addPlaceListFragment(PlaceListFragment.newInstance(presentPlaces), getString(R.string.present));

        PlaceListViewModel placeListViewModel = new PlaceListViewModel.Builder()
                .withTitle(street.getName())
                .withAdapter(placeListFragmentPagerAdapter)
                .withViewPager(placeListActivityBinding.placeListViewPager)
                .withToolbarNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                })
                .build();

        placeListActivityBinding.setPlaceListViewModel(placeListViewModel);
    }

    @Override
    public void showPlace(Place place) {
        intents.startPlaceActivity(this, place, street.getPathString());
    }

    @Override
    public void showPlaceOnMap(Place place) {
        intents.startMapActivity(this, place, street.getPathString());
    }
}
