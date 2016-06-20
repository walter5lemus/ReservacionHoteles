package com.example.truenoblanco.proyecto2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.Contact;
import com.example.truenoblanco.proyecto2.login.DatabaseH;
import com.example.truenoblanco.proyecto2.login.SignUp;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    static List<Contact> listaClientes;
    Conexion conn;
    DatabaseH helper = new DatabaseH(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /*info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/
                Intent i = new Intent(MainActivity.this, MenuCliente.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                info.setText("Intento de acceder cancelado.");
            }

            @Override
            public void onError(FacebookException error) {
                info.setText("Intento de acceder fallido.");
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        conn=new Conexion();
        listaClientes = new ArrayList<Contact>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

                if ( str.equals("") || pass.equals("")){
                    Toast temp = Toast.makeText(MainActivity.this, "Ingrese los campos mostrados!", Toast.LENGTH_SHORT);
                    temp.show();
                }
                else {

                    if(password==1)
                    {
                        int pago=0;
                        FacebookSdk.sdkInitialize(getApplicationContext());
                        LoginManager.getInstance().logOut();
                        Intent i = new Intent(MainActivity.this, MenuCliente.class);
                        i.putExtra("Username",str);
                        i.putExtra("pago",pago);
                        startActivity(i);
                    }
                    else
                    {
                        Toast temp = Toast.makeText(MainActivity.this, "Usuario y contrasena incorrecta!", Toast.LENGTH_SHORT);
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