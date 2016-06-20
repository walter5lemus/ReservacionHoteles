package com.example.truenoblanco.proyecto2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truenoblanco.proyecto2.login.UtilPayPal;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.Date;

public class ConfirmacionReservacionCliente extends Activity {

    String persona,user;
    String tipoHabitacion,codHabitacion;
    String fechaInicio, fechaFinal;
    Float precio;
    TextView mensaje,mensaje2,mensaje3;
    Date fecha1;


    Conexion conn;
    @SuppressLint("NewApi")

    private static final int REQUEST_CODE_PAYMENT = 1;

    private static PayPalConfiguration paypalConfig=new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId( UtilPayPal.paypal_sdk_id)
            .acceptCreditCards(true)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Code_Crash")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.paypal.com/webapps/mpp/ua/privacy-full"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.paypal.com/webapps/mpp/ua/useragreement-full"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_reservacion_cliente);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        conn=new Conexion();

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);

        mensaje = (TextView)findViewById(R.id.tvReservaConfor);
        mensaje2 = (TextView) findViewById(R.id.Precio);
        mensaje3=(TextView) findViewById(R.id.precio2);

        /*Button confirmar = (Button) findViewById(R.id.ButtonConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pagar();
            }
        });*/
        try{
        Bundle bundle = getIntent().getExtras();
        persona = bundle.getString("personas");
        fechaInicio = bundle.getString("fechainicio");
        fechaFinal = bundle.getString("fechafinal");
        tipoHabitacion = bundle.getString("tipohabitacion");
        precio = bundle.getFloat("precio");
        codHabitacion = bundle.getString("codhabitacion");
        user = bundle.getString("Username");




        }
        catch(Exception e){
            e.getStackTrace();
        }


        mensaje.setText("RESERVA DESDE "+fechaInicio+ "\nHASTA "+fechaFinal+"\nPARA "+persona+"PERSONAS\nTIPO DE HABITACION "+tipoHabitacion+"\nNUMERO DE HABITACION "+codHabitacion);
        mensaje2.setText("CON UN TOTAL A PAGAR DE \n");
        mensaje3.setText("$"+precio);



    }

    public void insertarTransaccion(View v) {
        try{
            String url = null;
            url ="";
            if (v.getId()==R.id.ButtonConfirmar) url=conn.getURLLocal()+"/etapa2/ws_db_insertarTransaccion.php?codhabitacion=" + codHabitacion + "&fechainicio="+
                    fechaInicio+ "&fechafinal=" + fechaFinal + "&personas=" + persona+ "&username=" + user;


            int i = ControladorServicio.respuesta(url, this);
            if (i==1){
                String url2="";
                url2=conn.getURLLocal()+"/etapa2/ws_db_updateNoDisponible.php?codhabitacion="+codHabitacion;
                ControladorServicio.obtenerRespuestaPeticion(url2,this);
                Toast.makeText(this, "Ingresado con Exito", Toast.LENGTH_SHORT).show();
                Class<?> clase= null;
                try {
                    clase = Class.forName("com.example.truenoblanco.proyecto2.MenuCliente");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Intent inte = new Intent(this,clase);
                inte.putExtra("Username",user);
                this.startActivity(inte);
            }
            else
                Toast.makeText(this, "Error, No ha sido ingresado", Toast.LENGTH_SHORT).show();
        }   catch(Exception e){
            e.getStackTrace();
        }


    }

    public void regresar(View v){
        if (v.getId()==R.id.brnRegresarConfirmacion){

            Class<?> clase= null;
            try {
                clase = Class.forName("com.example.truenoblanco.proyecto2.ReservasCliente");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent inte = new Intent(this,clase);
            inte.putExtra("Username",user);
            startActivity(inte);


        }

    }


    public void pagar(){

        PayPalPayment pago = new PayPalPayment(new BigDecimal(precio),"USD", "Hotel El Papu",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(ConfirmacionReservacionCliente.this, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, pago);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {

                Toast.makeText(getApplicationContext(), "Pago realizado correctamente", Toast.LENGTH_LONG).show();

            }

            else if (resultCode == Activity.RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(), "Pago cancelado. Intente de nuevo", Toast.LENGTH_LONG).show();


            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                Toast.makeText(getApplicationContext(), "Pago cancelado. Intente de nuevo", Toast.LENGTH_LONG).show();

            }
        }
    }



}
