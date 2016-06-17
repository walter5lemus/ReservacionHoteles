package com.example.truenoblanco.proyecto2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.DatabaseH;
import com.example.truenoblanco.proyecto2.login.SignUp;


public class MainActivity extends ActionBarActivity {

    Conexion conn;
    DatabaseH helper = new DatabaseH(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        conn=new Conexion();
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
            String url=null;


            url=conn.getURLLocal()+"/etapa2/ws_db_buscarpass.php?nick=" + str + "&pass="+pass;

            int ai = ControladorServicio.respuesta(url, this);
            System.out.println(ai);


            if ( str.equals("") || pass.equals("")){
                Toast temp = Toast.makeText(MainActivity.this, "Ingrese los campos mostrados!", Toast.LENGTH_SHORT);
                temp.show();
            }
            else {

                   if(ai==1)
                   {
                       if(!str.equals("admin")){
                           final ProgressDialog progressDialog = new ProgressDialog(this);
                           progressDialog.setIndeterminate(true);
                           progressDialog.setMessage("Autenticando...");
                           progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                           progressDialog.show();
                           Intent i = new Intent(MainActivity.this, MenuCliente.class);
                           i.putExtra("Username",str);
                           startActivity(i);
                           progressDialog.dismiss();

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
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Espere...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);
            progressDialog.dismiss();
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
