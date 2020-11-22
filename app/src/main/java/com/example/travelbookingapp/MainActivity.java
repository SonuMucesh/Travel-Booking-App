package com.example.travelbookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.ContextMenu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import android.view.MenuItem;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private TextView DepartureDate;
    private TextView ArrivalDate;
    private DatePickerDialog datePickerDialog;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar;
    private TextView DepartureAirport;
    private TextView ArrivalAirport;
    private TextView DepartureCity;
    private TextView ArrivalCity;
    private TextView Class;
    private TextView Travellers;
    private Button BookingButton;
    private ArrayList<String> AIRPORTS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadJSONFromAsset();

//        String[] AIRPORTS = new String[] {
//                "Belgium", "France", "Italy", "Germany", "Spain"
//        };



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, AIRPORTS);

        AutoCompleteTextView textView = findViewById(R.id.actv);
        textView.setAdapter(adapter);

        String date_n = new SimpleDateFormat("DD/MM/YY", Locale.getDefault()).format(new Date());

        DepartureDate = findViewById(R.id.DepartureDate);
        DepartureDate.setText(date_n);

        ArrivalDate = findViewById(R.id.ArrivalDate);
        ArrivalDate.setText(date_n);

        DepartureDate.setOnClickListener(view -> {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (datePicker, year, month, day) -> DepartureDate.setText(day + "/" + (month + 1) + "/" + year), year, month, dayOfMonth);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        ArrivalDate.setOnClickListener(view -> {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (datePicker, year, month, day) -> ArrivalDate.setText(day + "/" + (month + 1) + "/" + year), year, month, dayOfMonth);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        DepartureCity = findViewById(R.id.DepartureCity);
        ArrivalCity = findViewById(R.id.ArrivalCity);
        ArrivalAirport = findViewById(R.id.ArrivalAirport);
        DepartureAirport = findViewById(R.id.DepartureAirport);
        registerForContextMenu(DepartureAirport);
        registerForContextMenu(ArrivalAirport);

        Travellers = findViewById(R.id.Travellers);
        Class = findViewById(R.id.Class);
        registerForContextMenu(Travellers);
        registerForContextMenu(Class);

        BookingButton = findViewById(R.id.BookingButton);

        BookingButton.setOnClickListener(v -> {
            Intent activityChangeIntent = new Intent(MainActivity.this, SplashScreen.class);
            MainActivity.this.startActivity(activityChangeIntent);
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v == DepartureAirport)
        {
            menu.setHeaderTitle("Choose your option");
            getMenuInflater().inflate(R.menu.airportmenu, menu);
        }
        if(v == ArrivalAirport)
        {
            getMenuInflater().inflate(R.menu.aairportmenu, menu);
        }
       if(v == Travellers)
       {
           getMenuInflater().inflate(R.menu.passengersmenu, menu);
       }
       if(v == Class)
       {
           getMenuInflater().inflate(R.menu.classmenu, menu);
       }

    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("Airports.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i=0; i<jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                AIRPORTS.add(object.get("code") + ": " + object.get("name"));
            }

        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ADT1:
                Travellers.setText("Adult: 1");
                return true;
            case R.id.ADT2:
                Travellers.setText("Adult: 2");
                return true;
            case R.id.ADT3:
                Travellers.setText("Adult: 3");
                return true;
            case R.id.CHD1:
                Travellers.setText("Child: 1");
                return true;
            case R.id.CHD2:
                Travellers.setText("Child: 2");
                return true;
            case R.id.CHD3:
                Travellers.setText("Child: 3");
                return true;
            case R.id.INF1:
                Travellers.setText("Infant: 1");
                return true;
            case R.id.INF2:
                Travellers.setText("Infant: 2");
                return true;
            case R.id.INF3:
                Travellers.setText("Infant: 3");
                return true;
            case R.id.LHR:
                DepartureAirport.setText("LHR");
                DepartureCity.setText("London");
                return true;
            case R.id.BOM:
                DepartureAirport.setText("BOM");
                DepartureCity.setText("Mumbai");
                return true;
            case R.id.LHR2:
                ArrivalAirport.setText("LHR");
                ArrivalCity.setText("London");
                return true;
            case R.id.BOM2:
                ArrivalAirport.setText("BOM");
                ArrivalCity.setText("Mumbai");
                return true;
            case R.id.Eco:
                Class.setText("Economy");
                return true;
            case R.id.PEco:
                Class.setText("Premium Economy");
                return true;
            case R.id.FClass:
                Class.setText("First Class");
                return true;
            case R.id.BClass:
                Class.setText("Buisness Class");
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

}
