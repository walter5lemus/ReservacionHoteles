package com.example.truenoblanco.proyecto2.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.Conexion;
import com.example.truenoblanco.proyecto2.ControladorServicio;
import com.example.truenoblanco.proyecto2.MainActivity;
import com.example.truenoblanco.proyecto2.R;

@SuppressLint("NewApi")
public class SignUp extends Activity {

    DatabaseH helper = new DatabaseH(this);
    Conexion conn;
    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        conn=new Conexion();
    }

    public void onSignUpClick(View v)
    {
        if(v.getId()== R.id.Bsignupbutton)
        {
            EditText name = (EditText)findViewById(R.id.TFname);
            EditText lastn = (EditText) findViewById(R.id.TFapellido);
            EditText email = (EditText)findViewById(R.id.TFemail);
            EditText uname = (EditText)findViewById(R.id.TFuname);
            EditText pass1 = (EditText)findViewById(R.id.TFpass1);
            EditText pass2 = (EditText)findViewById(R.id.TFpass2);

            String url=null;

            String namestr = name.getText().toString();
            String lastname = lastn.getText().toString();
            String emailstr = email.getText().toString();
            String unamestr = uname.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();

            if(namestr.equals("") || lastname.equals("") || emailstr.equals("") || unamestr.equals("")|| pass1str.equals("") || pass2str.equals("")){
                Toast algo = Toast.makeText(SignUp.this, "Ingrese los datos en todos los campos", Toast.LENGTH_SHORT);
                algo.show();
            }
            else{

                if(!pass1str.equals(pass2str))
                {
                    //popup msg
                    Toast pass = Toast.makeText(SignUp.this, "Passwords don't match!", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else
                {
                    url = "";
                    try{
                        url=conn.getURLLocal()+"/etapa2/ws_db_insertarCliente.php?nombres=" + namestr + "&apellidos=" + lastname + "&correo=" + emailstr + "&nick=" + unamestr + "&pass=" + pass1str;

                        int i = ControladorServicio.respuesta(url, this);
                        if (i == 1) {
                            Toast.makeText(this, "Ingresado con Exito", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(this, MainActivity.class);
                            startActivity(a);
                        }
                        else
                            Toast.makeText(this, "Error, No ha sido ingresado", Toast.LENGTH_SHORT).show();

                    }catch(Exception e){
                        Toast.makeText(this, "Error, No ha sido ingresado2", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    }
    }
    protected void onPause(){
        super.onPause();
        finish();       //termina la actividad
    }
}
