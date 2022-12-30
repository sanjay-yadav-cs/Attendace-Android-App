package com.rjcollege.attendance;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rjcollege.attendance.databinding.ActivityMainBinding;
import com.skyfishjy.library.RippleBackground;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_CODE_CAMERA_CHECKIN = 105;
    private static final int REQUEST_CODE_CAMERA_CHECKOUT = 106;
    SharedPreferences sh;
    SharedPreferences.Editor myEdit;
    DatabaseHelper databaseHelper;
    String addressDetails;
private ActivityMainBinding binding;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        databaseHelper = DatabaseHelper.getDB(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);


        sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sh.edit();


        String userName = sh.getString("name","");

        binding.username.setText(userName);


        binding.laction.setText(getLastLocation());




        binding.btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                    return;

                }
                Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(icamera,REQUEST_CODE_CAMERA_CHECKIN);
            }
        });



        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        ImageView imageView=(ImageView)findViewById(R.id.centerImage);
        rippleBackground.startRippleAnimation();


//        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();

        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("EEE, MMM d, yyyy, h:mm:ss a");
                String date = month_date.format(cal.getTime());
                handler.postDelayed(this, 1000);
                binding.date.setText(date);
                binding.laction.setText(getLastLocation());




                int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);

                if(timeOfDay >= 0 && timeOfDay < 12){
                    binding.greeting.setText("Good Morning");
                }else if(timeOfDay >= 12 && timeOfDay < 16){
                    binding.greeting.setText("Good Afternoon");
                }else if(timeOfDay >= 16 && timeOfDay < 21){
                    binding.greeting.setText("Good Evening");
                }else if(timeOfDay >= 21 && timeOfDay < 24){
                    binding.greeting.setText("Good Night");
                }



            }
        };
        r.run();

        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                    return;

                }

                if (sh.getBoolean("checkin",false)) {
                    Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(icamera, REQUEST_CODE_CAMERA_CHECKOUT);
                }


            }
        });


        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEdit.putBoolean("isLogin", false);
                myEdit.remove("name").commit();
                myEdit.remove("mobilenumber").commit();
                myEdit.remove("id").commit();
                myEdit.clear().commit();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                Toast.makeText(MainActivity.this, "Logout Sucessfully !!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        binding.exportdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportdata();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==REQUEST_CODE_CAMERA_CHECKOUT){

                if (sh.getBoolean("checkin",false)) {
                    String address = getLastLocation();
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat month_date = new SimpleDateFormat("EEE, MMM d, yyyy");
                    SimpleDateFormat timeformat = new SimpleDateFormat("h:mm:ss a");
                    String date = month_date.format(cal.getTime());
                    String time = timeformat.format(cal.getTime());
                    int id = sh.getInt("checkinid", -1);
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    byte[] img = ImageTypeConverter.getStringFromBitmap(image);
                    databaseHelper.attencanceDao().CheckOut(id,time,address,img);
                    myEdit.putBoolean("checkin", false);
                    myEdit.apply();
//                    binding.btnCheckIn.setText("Check In");
                    binding.btnCheckIn.setEnabled(true);
//                    binding.btnCheckIn.setBackgroundResource(R.drawable.btn_bg);
                    binding.btnCheckIn.setBackgroundResource(R.drawable.disable_btn);
                    binding.centerImage.setImageResource(R.drawable.check_in);
                    binding.profile.setImageResource(R.drawable.profile);

                    Toast.makeText(this, "Sign Off Sucessfully !!!!", Toast.LENGTH_SHORT).show();


                }

            }
            else if (requestCode==REQUEST_CODE_CAMERA_CHECKIN){


                if (!sh.getBoolean("checkin",false)) {

                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    binding.profile.setImageBitmap(image);

                    String address = getLastLocation();

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat month_date = new SimpleDateFormat("EEE, MMM d, yyyy");
                    SimpleDateFormat timeformat = new SimpleDateFormat("h:mm:ss a");
                    String date = month_date.format(cal.getTime());
                    String time = timeformat.format(cal.getTime());
                    String name = sh.getString("name", "");
                    String mobilenumber = sh.getString("mobilenumber", "");

                    byte[] img = ImageTypeConverter.getStringFromBitmap(image);

                    long id = databaseHelper.attencanceDao().addAttendace(new Attendance(name, mobilenumber, date, time, address, img));
                    myEdit.putBoolean("checkin", true);
                    binding.btnCheckOut.setEnabled(false);
                    binding.btnCheckOut.setBackgroundResource(R.drawable.disable_btn);
                    binding.centerImage.setImageResource(R.drawable.checked);
                    myEdit.putInt("checkinid", (int) id);
                    Toast.makeText(this, "Attendance marked !!!!", Toast.LENGTH_SHORT).show();
//                    binding.btnCheckIn.setText("Check Out");
                    binding.btnCheckIn.setEnabled(false);
                    myEdit.apply();

                }


            }


        }


    }

    private String getLastLocation(){



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null){

                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> address = null;
                        try {
                            address = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            addressDetails = address.get(0).getAddressLine(0);
                            binding.laction.setText(addressDetails);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    }
                }
            });
        }
        else{
            askpermission();
        }

        return addressDetails;
    }

    private void askpermission() {

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == REQUEST_CODE){


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                binding.laction.setText(getLastLocation());

            }else {
                Toast.makeText(MainActivity.this,"Provide the required Permission",Toast.LENGTH_LONG);
            }



        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (sh.getBoolean("checkin",false)){
            binding.centerImage.setImageResource(R.drawable.checked);
            binding.btnCheckIn.setBackgroundResource(R.drawable.disable_btn);
            binding.btnCheckOut.setEnabled(true);
            binding.btnCheckOut.setBackgroundResource(R.drawable.btn_bg);
            byte[] img = databaseHelper.attencanceDao().getCheckinImg(sh.getInt("checkinid",-1));
            if (img!=null){
                binding.profile.setImageBitmap(ImageTypeConverter.getBitmapFromStr(img));
            }
            binding.btnCheckIn.setEnabled(false);
        }else{
            binding.btnCheckIn.setEnabled(true);
            binding.btnCheckIn.setBackgroundResource(R.drawable.btn_bg);
            binding.btnCheckOut.setEnabled(false);
            binding.btnCheckOut.setBackgroundResource(R.drawable.disable_btn);
            binding.centerImage.setImageResource(R.drawable.check_in);
            binding.profile.setImageResource(R.drawable.profile);
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public void exportdata(){
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                EXTERNAL_STORAGE_PERMISSION_CODE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && false == Environment.isExternalStorageManager()) {
            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            startActivity(new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri));
        }

            Toast.makeText(this, "trr", Toast.LENGTH_SHORT).show();
            try {
                Toast.makeText(this, "tryy", Toast.LENGTH_SHORT).show();
                File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(folder, "attendance.xls");
                WritableWorkbook wb = Workbook.createWorkbook(file);
                WritableSheet sht = wb.createSheet("data", 0);
                Label ll = new Label(0, 0, "id");
                Label l2 = new Label(1, 0, "name");
                Label l3 = new Label(3, 0, "mobileNumber");
                Label l4 = new Label(2, 0, "date");
                Label l5 = new Label(4, 0, "checkInTime");
                Label l6 = new Label(5, 0, "checkInLocation");
                Label l7 = new Label(6, 0, "checkInImage");
                Label l8 = new Label(7, 0, "checkOutTime");
                Label l9 = new Label(8, 0, "CheckOutLocation");
                Label l10 = new Label(9, 0, "CheckOutImage");


                ArrayList<Attendance> attendances = (ArrayList<Attendance>) databaseHelper.attencanceDao().getAllAttendace();

                sht.addCell(ll);
                sht.addCell(l2);
                sht.addCell(l3);
                sht.addCell(l4);
                sht.addCell(l5);
                sht.addCell(l6);
                sht.addCell(l7);
                sht.addCell(l8);
                sht.addCell(l9);
                sht.addCell(l10);

                for (int i = 0; i < attendances.size(); i++) {
                    Attendance att = attendances.get(i);

                    sht.addCell(new Label(0, i + 1, String.valueOf(att.getId())));
                    sht.addCell(new Label(1, i + 1, att.getName()));
                    sht.addCell(new Label(2, i + 1, att.getMobileNumber()));
                    sht.addCell(new Label(3, i + 1, att.getDate()));
                    sht.addCell(new Label(4, i + 1, att.getCheckInTime()));
                    sht.addCell(new Label(5, i + 1, att.getCheckInLocation()));
                    WritableImage img = new WritableImage(0, 0, 1, 2, att.getCheckInImage());
                    img.setColumn(6);
                    img.setRow(i + 1);
                    sht.addImage(img);
                    sht.addCell(new Label(7, i + 1, att.getCheckOutTime()));
                    sht.addCell(new Label(8, i + 1, att.getGetCheckOutLocation()));
                    WritableImage img2 = new WritableImage(0, 0, 1, 2, att.getGetGetCheckOutImage());
                    img2.setColumn(9);
                    img2.setRow(i + 1);
                    sht.addImage(img2);

                }

                wb.write();
                wb.close();
                Toast.makeText(this, "Data Saved at " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
//        }else{
//            askstoragepermission();
//        }

    }


}