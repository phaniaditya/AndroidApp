package com.example.a5e025799h.next_genshopping;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {


    WebView webView;
    GoogleMap mGoogleMap;
    Results result;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webView = (WebView) findViewById(R.id.webView);

        result = getIntent().getParcelableExtra("result");
        webView.loadUrl(result.businessURL);

        if(googleServicesAvailable()){
            initMap();
        }

        auth = FirebaseAuth.getInstance();

    }

    private void initMap(){

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        }else{
            Toast.makeText(this, "can't connect to play services", Toast.LENGTH_LONG).show();

        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng ll = new LatLng(result.latitude, result.longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,15);
        mGoogleMap.animateCamera(update);

        MarkerOptions options = new MarkerOptions().title(result.title).snippet(result.phone).position(ll);
        mGoogleMap.addMarker(options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:
                logoutUser();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser(){
        auth.signOut();
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(DetailsActivity.this, MainActivity.class));
            finish();
        }

    }
//use this at the end of project
    /*public void track(View view) {
        if (view.getId() == R.id.track) {
         startActivity(new Intent(DetailsActivity.this, TrackActivity.class));

        }
    }*/
    public void shop(View view){
        if(view.getId()==R.id.shop){
            startActivity(new Intent(DetailsActivity.this, ShopActivity.class));
        }
    }

}
