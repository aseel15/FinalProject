package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.model.Person;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TableLayout edtTable;
    private ArrayList<Person> personList;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        personList=new ArrayList<Person>();
        edtTable=findViewById(R.id.CustomersTable);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Customers");
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_Drawer_Open, R.string.navigation_Drawer_Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DisplayUsers();
    }


    public void DisplayUsers(){
      String url = "http://10.0.2.2:80/FinalProject/Search.php";

      RequestQueue queue = Volley.newRequestQueue(AllUser.this);
      JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
            null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i = 0; i < response.length(); i++) {

                try {
                    JSONObject obj = response.getJSONObject(i);

                    if (obj.getString("role").equals("0"))
                    {

                    int id=Integer.parseInt(obj.getString("id"));
                    String name=obj.getString("username");
                    String email=obj.getString("email");
                    String password=obj.getString("password");
                    Person personObj=new Person(id,name,email,password);
                    personList.add(personObj);


                    }

                }catch(JSONException exception){
                    Toast.makeText(AllUser.this, exception.toString(),
                            Toast.LENGTH_SHORT).show();
                }

            }
            showTableLayout();
        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(AllUser.this, error.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    });

        queue.add(request);



    }

    public  void showTableLayout() {

        int rows = personList.size();


        for (int i = 0; i < rows; i++) {


            TableRow tr = new TableRow(this);

            TextView txtGeneric = new TextView(this);
            txtGeneric.setTextSize(18);
            txtGeneric.setWidth(220);
            txtGeneric.setHeight(170);
            txtGeneric.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtGeneric.setText(String.valueOf(personList.get(i).getId()));
            tr.addView(txtGeneric);


            TextView txtGeneric2 = new TextView(this);
            txtGeneric2.setTextSize(18);
            txtGeneric2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtGeneric2.setWidth(650);
            txtGeneric2.setHeight(200);
            txtGeneric2.setPadding(130,0,0,0);
            txtGeneric2.setText(personList.get(i).getEmail());
            tr.addView(txtGeneric2);

            TextView txtGeneric3 = new TextView(this);
            txtGeneric3.setTextSize(18);
            txtGeneric3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtGeneric3.setPadding(140,0,0,0);
            txtGeneric3.setWidth(260);
            txtGeneric3.setHeight(200);
            txtGeneric3.setText(personList.get(i).getUsername());
            tr.addView(txtGeneric3);


            edtTable.addView(tr);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

            case R.id.nav_home_a:
                intent=new Intent(AllUser.this, EmployeeResRoom.class);
                startActivity(intent);
                break;
            case R.id.nav_addroom_a:
                intent=new Intent(AllUser.this, AddRoom.class);
                startActivity(intent);
                break;
            case R.id.nav_services_a:
                intent=new Intent(AllUser.this, AcceptServiceByEmployee.class);
                startActivity(intent);
                break;
            case R.id.nav_trips_a:
                intent=new Intent(AllUser.this, ControlTripAdmin.class);
                startActivity(intent);
                break;

            case R.id.nav_persons_a:
                intent=new Intent(AllUser.this, AllUser.class);
                startActivity(intent);
                break;

            case R.id.nav_add_Person_a:
                intent=new Intent(AllUser.this, addPersonAdmin.class);
                startActivity(intent);
                break;
            case R.id.nav_logout_a:
                intent=new Intent(AllUser.this, LogOut.class);
                startActivity(intent);
                break;
            case R.id.nav_parties_a:
                intent=new Intent(AllUser.this, PlacesEmployeeView.class);
                startActivity(intent);
                break;

            case R.id.nav_addPlace_a:
                intent=new Intent(AllUser.this, addPlace.class);
                startActivity(intent);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}