package com.wip.rayyacub.weathersearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wip.rayyacub.weathersearch.Adapter.WeatherAdapter;
import com.wip.rayyacub.weathersearch.Model.Forecast;
import com.wip.rayyacub.weathersearch.Model.ListOfWeatherFeatures;
import com.wip.rayyacub.weathersearch.Networking.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search)
    SearchView searchForLocation;
    @BindView(R.id.weather_forecast)
    RecyclerView recyclerView;
    public WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        rvInit();
        searchForLocation.setOnQueryTextListener(this);



    }

    private void rvInit() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.HORIZONTAL));
    }

    private void networking(String query) {
        ApiClient.getApiService()
                .getWeatherObservable(query, "5", ApiClient.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Forecast>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Forecast forecast) {
                        List<ListOfWeatherFeatures> features = forecast.getList();
                        weatherAdapter = new WeatherAdapter(MainActivity.this, features);
                        recyclerView.setAdapter(weatherAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        networking(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
