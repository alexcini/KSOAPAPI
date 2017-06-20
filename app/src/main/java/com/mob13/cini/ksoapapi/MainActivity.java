package com.mob13.cini.ksoapapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//* IMPORTS NECESSÁRIOS PARA O SOAP
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.PrivateKey;

import static com.mob13.cini.ksoapapi.R.id.etNum1;
import static com.mob13.cini.ksoapapi.R.id.etNum2;


public class MainActivity extends AppCompatActivity {

    String METHOD_NAME = "Soma";
    String SOAP_ACTION = "";

    String NAMESPACE = "http://heiderlopes.com.br/";
    String SOAP_URL = "http://10.3.1.42:8080/CalculadoraWSService/CalculadoraWS";

    SoapObject request;
    SoapPrimitive calcular;

    private EditText etNum1;
    private EditText etNum2;
    private EditText etSomca;

    ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);

    }

    public void somar(View v) {
        int numero1 = Integer.parseInt(etNum1.getText().toString());
        int numero2 = Integer.parseInt(etNum2.getText().toString());
        CalcularAsync calcularAsync = new CalcularAsync();
        calcularAsync.execute(numero1, numero2);
    }
    private class CalcularAsync extends AsyncTask<Integer, Void, String> {

        @Override
        protected Void doInBackground(Integer... params) {

            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("num1", 1);
            request.addProperty("num2", 1);
            request.addProperty("op", "+");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            //envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_URL);
            try {
                httpTransport.call(SOAP_ACTION, envelope);
                calcular = (SoapPrimitive) envelope.getResponse();
            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pdialog.dismiss();
            Toast.makeText(getApplicationContext(), "Resultado: " + calcular.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(MainActivity.this);
            pdialog.setMessage("Converting...");
            pdialog.show();
        }
    }
}
