package com.ighub.fellowship40app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;

    private SmartMaterialSpinner spProvince;
    private SmartMaterialSpinner spEmptyItem;
    private List<String> provinceList;
    String id, fullname;
    String[] fName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        network();

//        spinner = findViewById(R.id.spinner);

        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                network();
            }
        });
    }

    private void network() {
        AndroidNetworking.get("https://new-covid.herokuapp.com/api/v1/supervisors")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.d("resp2", response.toString());

                        for(int i=0; i <response.length();  i++)
                        {
                            try {
                                JSONObject json_data = response.getJSONObject(i);
                                id = json_data.getString("id");
                                fullname = json_data.getString("fullname");

                                Log.d("resp4", "id: "+id + " fullname: "+ fullname);

                                initSpinner();
                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("err2", error.toString());
                    }
                });
    }

    private void initSpinner() {
        spProvince = findViewById(R.id.spinner1);
//        spEmptyItem = findViewById(R.id.sp_empty_item);


        provinceList = new ArrayList<>();

            provinceList.add(fullname);

            spProvince.setItem(provinceList);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, provinceList.get(position), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
