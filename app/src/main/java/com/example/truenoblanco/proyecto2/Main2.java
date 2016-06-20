package com.example.truenoblanco.proyecto2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.Contact;
import com.example.truenoblanco.proyecto2.login.DatabaseH;
import com.example.truenoblanco.proyecto2.login.SignUp;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;


public class Main2 extends ActionBarActivity {

    static List<Contact> listaClientes;
    Conexion conn;
    DatabaseH helper = new DatabaseH(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        conn=new Conexion();
        listaClientes = new ArrayList<Contact>();
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

            try {
                url=conn.getURLLocal()+"/etapa2/ws_db_buscarpass.php?nick=" + str + "&pass="+pass;
                int password;

                password = ControladorServicio.respuesta(url,this);
                System.out.println(password);

                if ( str.equals("") || pass.equals("")){
                    Toast temp = Toast.makeText(Main2.this, "Ingrese los campos mostrados!", Toast.LENGTH_SHORT);
                    temp.show();
                }
                else {

                    if(password==1)
                    {
                        FacebookSdk.sdkInitialize(getApplicationContext());
                        LoginManager.getInstance().logOut();
                        Intent i = new Intent(Main2.this, MenuCliente.class);
                        i.putExtra("Username",str);
                        startActivity(i);

                    }
                    else
                    {
                        Toast temp = Toast.makeText(Main2.this, "Usuario y contrasena incorrecta!", Toast.LENGTH_SHORT);
                        temp.show();
                    }
                }

            } catch (Exception e) {
                Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
            }

        }
        if(v.getId() == R.id.Bsignup)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Espere...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            Intent i = new Intent(Main2.this, SignUp.class);
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

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage("Recuerda que para realizar una reservación necesitas crear un usuario aun así quieres salir?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Main2.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    protected void onPause(){
        super.onPause();
        finish();       //termina la actividad
    }
}