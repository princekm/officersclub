package com.example.officersclub;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.officersclub.db.Bill;
import com.example.officersclub.db.DBHelper;
import com.example.officersclub.db.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fragments.AccountFragment;
import fragments.CanteenFragment;
import fragments.ClubFragment;
import fragments.LocationFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ClubFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener, LocationFragment.OnFragmentInteractionListener, CanteenFragment.OnFragmentInteractionListener {

    final int MY_PERMISSIONS_REQUEST_READ_SMS=1;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        View view = findViewById(R.id.fragment_main);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Snackbar.make(view, "Permision granted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Snackbar.make(view, "Permission denied", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void prepareMenuData() {

        Resources resources =getResources();//.getString(R.string.app_name);


        MenuModel menuModel = new MenuModel("Club",R.drawable.ic_menu_club, true, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Canteen",R.drawable.ic_menu_canteen, false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("BAR",R.drawable.ic_menu_bar, false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Gym",R.drawable.ic_menu_gym, false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Library",R.drawable.ic_menu_library, false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Pool",R.drawable.ic_menu_pool, false, false);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }
        menuModel = new MenuModel("Account",R.drawable.ic_menu_account ,true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);



        menuModel = new MenuModel("Location",R.drawable.ic_menu_location, true, false); //Menu of Python Tutorials
        headerList.add(menuModel);
    }
    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    private void readMessagesToDB(){

        final String SMS_URI_INBOX = "content://sms/inbox";
        try {
            Uri uri = Uri.parse(SMS_URI_INBOX);
            String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
            Cursor cur = getContentResolver().query(uri, projection, "address='ADBELCLB'", null, "date desc");
            if (cur.moveToFirst()) {
                int indexBody = cur.getColumnIndex("body");
                do {

                    String message = cur.getString(indexBody);
                    Bill bill=new Bill(message);
                    dbHelper.insertBillDetails(bill);
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
            } // end if
        }
        catch (
                SQLiteException ex) {
            Log.d("SQLiteException", ex.getMessage());
        }
    }
    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    MenuModel model = headerList.get(groupPosition);
                    setTitle(model.menuName);
                    Fragment fragment;
                    switch(model.resID)
                    {
                        case R.drawable.ic_menu_club:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, ClubFragment.newInstance("q","q"), "ClubFragment").commit();//.addToBackStack(null);
                            break;
                        case R.drawable.ic_menu_account:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, AccountFragment.newInstance("q","q"), "AccountFragment").commit();//.addToBackStack(null);
                            onBackPressed();
                            break;

                        case R.drawable.ic_menu_location:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, LocationFragment.newInstance("q","q"), "LocationFragment").commit();//.addToBackStack(null);


//                            MapView mapView = findViewById(R.id.map_view);
//                            mapView.onCreate(mapViewBundle);
//                            mapView.getMapAsync(this);

                            onBackPressed();

                            break;

                    }
                    if (!headerList.get(groupPosition).hasChildren) {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(headerList.get(groupPosition).url);
//                        onBackPressed();

                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                        setTitle(model.menuName);
                        switch(model.resID)
                        {
                            case R.drawable.ic_menu_canteen:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, CanteenFragment.newInstance("q","q"), "CanteenFragment").commit();//.addToBackStack(null);
                                onBackPressed();

                                break;
                        }

                }

                return false;
            }
        });
    }

    public static DBHelper dbHelper;
    private User user;
    private NavigationView navigationView;
    private TextView userNameView;
    private TextView staffNoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }




        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setUserDetails();
        readMessagesToDB();
    }
private void setUserDetails()
{
    if(dbHelper.getAllUsers().size()==0)

        Snackbar.make(navigationView, "User not configured", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    else {
        Snackbar.make(navigationView, "User configured", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        ArrayList<User> list =dbHelper.getAllUsers();
        user=list.get(0);
        if(user!=null) {
            View view = navigationView.getHeaderView(0);
            userNameView = view.findViewById(R.id.user_db);
            if (view != null) {
                userNameView.setText(user.getName());
            }
            staffNoView = view.findViewById(R.id.staffno_db);
            if (staffNoView != null) {
                staffNoView.setText(user.getStaffNo());
            }
        }
    }

}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_club) {
//            // Handle the camera action
//        } else if (id == R.id.nav_account) {
//
//        } else if (id == R.id.nav_location) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateDetails(View view) {

        EditText name=(EditText)findViewById(R.id.user);
        EditText staffNo=(EditText)findViewById(R.id.staffno);
String message;
        if(name.getText().length()==0&&staffNo.getText().length()==0)
          message="Username and StaffNo are empty.";
        else if(name.getText().length()==0)
            message="Username is empty.";
        else if(staffNo.getText().length()==0)
            message="StaffNo is empty.";
        else {
                User user= new User(name.getText().toString(),new Integer(staffNo.getText().toString()));
            long result=dbHelper.insertUser(user);
            if(result!=-1) {
                message = "Updated successfully" ;
                userNameView.setText(user.getName());
                staffNoView.setText(user.getStaffNo());
            }
            else
                message="Couldn't insert user";



        }
        Snackbar.make(view,message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

    }
}
