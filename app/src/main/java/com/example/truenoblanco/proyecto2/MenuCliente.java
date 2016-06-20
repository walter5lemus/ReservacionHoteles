package com.example.truenoblanco.proyecto2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.SignUp;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class MenuCliente extends AppCompatActivity {

    String user;
    int pago;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("Username");
        pago=bundle.getInt("pago");
    }

    public void reservar(View d){
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Espere...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Para realizar una reservación necesitas crear un usuario.", Toast.LENGTH_SHORT);
            toast1.show();

            Intent i = new Intent(MenuCliente.this,MainActivity.class);
            startActivity(i);
            progressDialog.show();
        }
        else{
            Intent inicioReservar = new Intent(this,ReservasCliente.class);
            inicioReservar.putExtra("Username",user);
            startActivity(inicioReservar);
            progressDialog.dismiss();
        }
    }

    public void promociones(View d){
        Intent iniPromociones=new Intent(this,Promociones.class);
        iniPromociones.putExtra("Username",user);
        startActivity(iniPromociones);
    }

    public void TipoHabitaciones(View v){
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

   public void ConsultarTransacciones(View v){
        Intent intent = new Intent(this, ConsultarTransacciones.class);
       intent.putExtra("Username",user);
        startActivity(intent);
    }

    public void salirMenuCliente(View d){
        Intent salir = new Intent(this, MainActivity.class);
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();

        super.onPause();
        finish();

        startActivity(salir);
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage("Esta seguro que decea salir?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        FacebookSdk.sdkInitialize(getApplicationContext());
                        LoginManager.getInstance().logOut();
                        MenuCliente.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /*Mata un Activity

    protected void onPause(){
        super.onPause();
        finish();       //termina la actividad
    }*/
}
