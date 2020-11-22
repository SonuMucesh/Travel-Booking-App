package com.example.travelbookingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import org.json.JSONException;
import org.json.JSONObject;


public class SplashScreen extends AppCompatActivity {

    VideoView videoView;
    public static JSONObject JOB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        videoView = findViewById(R.id.videoView);

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.planeanimation);
        videoView.setVideoURI(video);
        videoView.setOnCompletionListener(mp -> startNextActivity());
        videoView.start();
    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        startActivity(new Intent(SplashScreen.this, FlightResults.class));
        finish();
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Amadeus amadeus = Amadeus
                    .builder("CLIENT_ID", "CLIENT_SECRET")
                    .build();

            try {
                FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                        Params.with("originLocationCode", "LHR")
                                .and("destinationLocationCode", "BOM")
                                .and("departureDate", "2020-11-01")
                                //.and("returnDate", "2020-11-08")
                                .and("adults", 1)
                                .and("max", 5));

                JOB = new JSONObject(flightOffersSearches[0].getResponse().getBody());

            } catch (ResponseException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

//        @Override
//        protected void onPostExecute(Void aVoid) {
//            startActivity(new Intent(SplashScreen.this, FlightResults.class));
//        }
    }
}
