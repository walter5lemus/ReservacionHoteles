package com.example.truenoblanco.proyecto2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.DatabaseH;
import com.example.truenoblanco.proyecto2.login.SignUp;


public class MainActivity extends ActionBarActivity {

    DatabaseH helper = new DatabaseH(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.Blogin)
        {
            EditText a = (EditText)findViewById(R.id.TFusername);
            String str = a.getText().toString();
            EditText b = (EditText)findViewById(R.id.TFpassword);
            String pass = b.getText().toString();
            String password = helper.searchPass(str);

            if ( str.equals("") || pass.equals("")){
                Toast temp = Toast.makeText(MainActivity.this, "Ingrese los campos mostrados!", Toast.LENGTH_SHORT);
                temp.show();
            }
            else {

                   if(pass.equals(password))
                   {
                       if(!str.equals("admin")){
                           Intent i = new Intent(MainActivity.this, MenuCliente.class);
                           i.putExtra("Username",str);
                           startActivity(i);
                           final ProgressDialog progressDialog = new ProgressDialog(this);
                           progressDialog.setIndeterminate(true);
                           progressDialog.setMessage("Autenticando...");
                           progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                           progressDialog.show();
                       }else{

                       }

                   }
                   else
                   {
                       Toast temp = Toast.makeText(MainActivity.this, "Usuario y contrasena incorrecta!", Toast.LENGTH_SHORT);
                       temp.show();
                   }
               }



        }
        if(v.getId() == R.id.Bsignup)
        {
            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);

        }

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
}
