package com.example.travelbookingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Airline;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.travelbookingapp.SplashScreen.*;

public class FlightResults extends AppCompatActivity {
    //a list to store all the products
    List<Flight> flightList;

    //the recyclerview
    RecyclerView recyclerView;
    public static String[] carrierCode;
    String[] departureString;
    String[] arrivalString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flightresults_recycler_items);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        flightList = new ArrayList<>();

        try {
            JSONArray array = JOB.getJSONArray("data");

            System.out.println(array.length());

            for (int i=0;i<=array.length()-1;i++)
            {
                JSONArray array1 = array.getJSONObject(i).getJSONArray("itineraries");

                for (int x=0;x<1;x++){
                    JSONArray array2 = array1.getJSONObject(x).getJSONArray("segments");
                    System.out.println(array2.get(x).toString());
                    departureString = array2.getJSONObject(x).getString("departure").split(",");
                    arrivalString = array2.getJSONObject(x).getString("arrival").split(",");
                    carrierCode = array2.getJSONObject(x).getString("carrierCode").split(",");
                }

                AsyncTaskRunner runner = new AsyncTaskRunner();
                //runner.execute();
                //System.out.println(runner.execute().get());

                flightList.add(new Flight(
                        array.getJSONObject(i).getString("id"),
                        array1.getJSONObject(0).getString("duration"),
                        runner.execute().get(),
                        departureString[2].substring(6,16) + "\n" + departureString[2].substring(17,25),
                        arrivalString[2].substring(6,16) + "\n" + arrivalString[2].substring(17,25),
                        "€"+ array.getJSONObject(i).getJSONObject("price").getString("total")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //creating recyclerview adapter
        FlightsAdapter adapter = new FlightsAdapter(this, flightList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, String> {
        String Airline = "";
        @Override
        protected String doInBackground(Void... voids) {
            Amadeus amadeus = Amadeus
                    .builder("CLIENTID", "CLIENT_SECRETE")
                    .build();
            try {
                Airline[] airlines = amadeus.referenceData.airlines.get(Params
                        .with("airlineCodes", carrierCode[0]));
                 Airline = airlines[0].getBusinessName();


            } catch (ResponseException e) {
                e.printStackTrace();
            }
            return Airline;
        }

    }
}

