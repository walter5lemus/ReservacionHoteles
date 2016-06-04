package com.example.truenoblanco.proyecto2.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.MainActivity;
import com.example.truenoblanco.proyecto2.R;

/**
 * Created by priya on 3/10/2015.
 */
public class SignUp extends Activity {

    DatabaseH helper = new DatabaseH(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void onSignUpClick(View v)
    {
        if(v.getId()== R.id.Bsignupbutton)
        {
            EditText name = (EditText)findViewById(R.id.TFname);
            EditText email = (EditText)findViewById(R.id.TFemail);
            EditText uname = (EditText)findViewById(R.id.TFuname);
            EditText pass1 = (EditText)findViewById(R.id.TFpass1);
            EditText pass2 = (EditText)findViewById(R.id.TFpass2);

            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String unamestr = uname.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();

            if(namestr.equals("") || emailstr.equals("") || unamestr.equals("")|| pass1str.equals("") || pass2str.equals("")){
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
                    //insert the detailes in database
                    Contact c = new Contact();
                    c.setName(namestr);
                    c.setEmail(emailstr);
                    c.setUname(unamestr);
                    c.setPass(pass1str);

                    helper.insertContact(c);

                    Toast pass = Toast.makeText(SignUp.this, "Datos ingresados con exito", Toast.LENGTH_SHORT);
                    pass.show();
                    Intent i = new Intent(SignUp.this, MainActivity.class);
                    startActivity(i);
                }




            }




        }

    }


}
