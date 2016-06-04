package com.example.truenoblanco.proyecto2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.example.truenoblanco.proyecto2.login.UtilPayPal;

import java.math.BigDecimal;

public class ConfirmacionReservacionCliente extends AppCompatActivity {

    String persona, habitacion, dias, promo, tipoHabitacion,fecha,precio;
    TextView mensaje,mensaje2,mensaje3;

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

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);

        mensaje = (TextView)findViewById(R.id.tvReservaConfor);
        mensaje2 = (TextView) findViewById(R.id.Precio);
        mensaje3=(TextView) findViewById(R.id.precio2);

        Button confirmar = (Button) findViewById(R.id.ButtonConfirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pagar();
            }
        });

        Bundle bundle = getIntent().getExtras();

        persona = bundle.getString("personas");
        habitacion = bundle.getString("habitacion");
        dias = bundle.getString("dias");
        promo = bundle.getString("promo");
        tipoHabitacion = bundle.getString("tipoHab");
        fecha = bundle.getString("fecha");
        precio = bundle.getString("precio");

        mensaje.setText("RESERVA POR "+dias+ " DIAS \n COMENZANDO EL DIA "+fecha+"\n HABITACION PARA "+persona +" PERSONAS \nHABITACION # "+tipoHabitacion+" \nCANTIDAD DE HABITACIONES " +habitacion+ " \nPROMOCION: "+promo);
        mensaje2.setText("CON UN TOTAL A PAGAR DE");
        mensaje3.setText("$ "+precio);

    }

    public void iniciar(View v){
        Intent intent = new Intent(this, MenuCliente.class);
        startActivity(intent);

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
