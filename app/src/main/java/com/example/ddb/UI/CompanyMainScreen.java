package com.example.ddb.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ddb.Data.Action;
import com.example.ddb.Data.NotifyDataChange;
import com.example.ddb.Data.Parcel_dataSource_Maneger.RegisteredPackagesDS;
import com.example.ddb.Data.Users;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.Parcel_Type;
import com.example.ddb.Entities.User;
import com.example.ddb.R;
import com.example.ddb.UI.AddParcelProcces.AddParcelMain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompanyMainScreen extends AppCompatActivity {

    private Company company;
    private List<Parcel> parcels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPercelList();
        setContentView(R.layout.activity_company_main_screen);

        // hide the Action Bar and the Status bar
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            getActionBar().hide();
        }
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // set the company name
        try {
            Bundle b = getIntent().getExtras();
            String str = b.getString("userID");
            company = (Company) Users.getUser(str);
            TextView tv = findViewById(R.id.company_name_tv);
            tv.setText(company.getName() + "!");
        } catch (Exception e) {
            TextView tv = findViewById(R.id.company_name_tv);
            tv.setText(e.getMessage());
        }

        // set the blessing text
        int[] blessings = {R.string.blessing_1, R.string.blessing_2, R.string.blessing_3};
        TextView hello_tv = findViewById(R.id.hello_tv);
        hello_tv.setText(blessings[new Random().nextInt((blessings.length))]);
        // log out button
        Button log_out_btn = findViewById(R.id.log_out_btn);
        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("skfjkj")
                        .setMessage(R.string.log_out)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                RegisteredPackagesDS.stopNotifyToParcelList();
            }
        });

        // add parcel fab
        FloatingActionButton add_parcel_fab = findViewById(R.id.add_parcel_fab);
        add_parcel_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddParcelMain.class);
                RegisteredPackagesDS.stopNotifyToParcelList();
                startActivity(intent);
            }
        });
    }

    private void getPercelList(){
        RegisteredPackagesDS.notifyToParcelList(new NotifyDataChange<List<Parcel>>() {
            @Override
            public void OnDataChanged(List<Parcel> obj) {

                for (Parcel parcel:obj) {
                    if(!parcels.contains(parcel))
                        parcels.add(parcel);
                }
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }

}
