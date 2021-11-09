package com.example.adamdeilsinki;

import static com.squareup.picasso.Picasso.get;
import static com.squareup.picasso.Picasso.setSingletonInstance;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //api
    EditText editCity;
    TextView textCity;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apikey = "251e4b3387636e8209caf22171b42ee0";
    DigitalClock digitalClock;
    ImageView night;
    ImageView morning;
    ImageView wallpaper;
    TextView feelslike;
    Double getval;
    Calendar c = Calendar.getInstance();
    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
    int getText;
    // Initializing the ImageView
    ImageView rImage;
    ImageView bImage;
    private DatabaseReference getImage;
    private DatabaseReference getImage2;
    //ImageView bImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getting ImageView by its id
        rImage = findViewById(R.id.rImage);
        bImage = findViewById(R.id.bImage);
        //bImage = findViewById(R.id.bImage);
        //api
        editCity = findViewById(R.id.editCity);
        textCity = findViewById(R.id.textCity);
        digitalClock = findViewById(R.id.digitalClock);
        time();
        feelslike = findViewById(R.id.feelslike);


        // we will get the default FirebaseDatabase instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://adamdegilsinki-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        // we will get a DatabaseReference for the database root node
        //DatabaseReference databaseReference = firebaseDatabase.getReference();

        // Here "image" is the child node value we are getting
        // child node data in the getImage variable
        getImage = databaseReference.child("image");
        getImage2 = databaseReference.child("image2");
        // Adding listener for a single change
        // in the data at this location.
        // this listener will triggered once
        // with the value of the data at the location

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // getting a DataSnapshot for the location at the specified
                // relative path and getting in the link variable
                String link = dataSnapshot.getValue(String.class);

                // loading that data into rImage
                // variable which is ImageView
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/adamdegilsinki.appspot.com/o/weather.jpg?alt=media&token=2f1417c1-ca14-47b7-9ad7-fc07ecc7044b").into(rImage);
                // Picasso.get().load(link).into(bImage);
            }

            // this will called when any problem
            // occurs in getting data
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(MainActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void time() {

        if (timeOfDay >= 0 && timeOfDay < 12) {
            Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            ///    if(ankara)
            Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            // wallpaper.setImageResource(R.drawable.kevin);
        }
    }

    public void getWeather(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<Example> exampleCall = weatherAPI.getWeather(editCity.getText().toString(), apikey);
        exampleCall.enqueue(new Callback<Example>() {
            ;

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "Please Enter a valid City", Toast.LENGTH_LONG).show();
                } else if (!(response.isSuccessful())) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
                }

                Example myData = response.body();
                Main main = myData.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int) (temp - 273.15);
                textCity.setText(String.valueOf(temperature) + "C");

                getImage2.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // getting a DataSnapshot for the location at the specified
                                // relative path and getting in the link variable

                                String link = dataSnapshot.getValue(String.class);

                                // loading that data into rImage
                                // variable which is ImageView

                                //change background photo by city
                                String ankara = editCity.getText().toString();
                                String a = "ankara";
                                String istanbul = editCity.getText().toString();
                                String b = "istanbul";
                                String eskisehir = editCity.getText().toString();
                                String e = "eskisehir";
                                String nevsehir = editCity.getText().toString();
                                String n = "nevsehir";
                                String izmir = editCity.getText().toString();
                                String c = "izmir";
                                String antalya = editCity.getText().toString();
                                String w = "antalya";
                                if (ankara.equals(a)) {
                                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/adamdegilsinki.appspot.com/o/ankara.jpg?alt=media&token=221f408a-2cfb-4496-b235-afb59a57b2c3").into(rImage);
                                }
                                else if (istanbul.equals(b)) {
                                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/adamdegilsinki.appspot.com/o/istanbul.jpg?alt=media&token=621de124-52c6-44e8-ad12-51691106db99").into(rImage);
                                }
                                else if (eskisehir.equals(e)) {
                                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/adamdegilsinki.appspot.com/o/eski%C5%9Fehir.jpg?alt=media&token=4fe62cec-d8c1-42e3-b343-4e72aaf94bbb").into(rImage);
                                }
                                else if (nevsehir.equals(n)) {
                                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/adamdegilsinki.appspot.com/o/nevsehir.jpg?alt=media&token=93100b26-d002-4245-98b4-6568314eed06").into(rImage);
                                }
                                else if (izmir.equals(c)) {
                                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/adamdegilsinki.appspot.com/o/izmir.jpg?alt=media&token=d4e62213-e96b-494a-ace3-eac002b1cf76").into(rImage);
                                }
                                else if (antalya.equals(w)) {
                                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/adamdegilsinki.appspot.com/o/antalya.jpg?alt=media&token=26852d23-81e5-4b18-8dbd-00026698a8b1").into(rImage);
                                }
                            }
                            // this will called when any problem
                            // occurs in getting data
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // we are showing that error message in toast
                                Toast.makeText(MainActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
                            }
                        }

                );

                Double feelsliken = main.getFeelsLike();
                Integer feelslike1 = (int) (feelsliken - 273.15);

                feelslike.setText(String.valueOf(feelslike1));


            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}