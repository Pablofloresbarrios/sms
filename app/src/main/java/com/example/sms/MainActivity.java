package com.example.sms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    EditText Edtnombre, Edtnumero, Edtcorreo;
    EditText validacion;
    Button btnRegistrar,btnIniciar;
    //Button buscar;
    int var=0;
    int acu=0;

    //a qui se escribe codigo para permiso
    //private static final int mi_permiso = 0;
    //private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
  //  TextView messageTV, numberTv;
    String palabra_objetivo="Http";//la palabra objetivo debe reconocerse solo ella seguida de un simbolo o espacio
    String palabra_rastreadora="";
    int longitud=0;
    int posicion=0;
    int contador=0;
    int veces_hallada=0;
    int i=0;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    Edtnombre= (EditText)findViewById(R.id.Name);
    validacion = (EditText) findViewById(R.id.validacion);
    Edtnumero= (EditText)findViewById(R.id.Number);
    Edtcorreo= (EditText)findViewById(R.id.Mail);
    btnRegistrar= (Button) findViewById(R.id.Registrarse);
    btnIniciar= (Button) findViewById(R.id.Iniciar);
   // buscar = (Button) findViewById(R.id.buscar);
    btnIniciar.setEnabled(false);

/////////////////////////////////////////////
     /*   messageTV= findViewById(R.id.mensaje);
        numberTv= findViewById(R.id.numero);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            // SI EL EL PERMISO NO ES GARANTIZADO DEBE SER DENEGADO
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS))
            {
                //HACER NADA SI ES DENEGADO

            }

            else
            {
                //EL MENSAJE DEBERA PARECER SOLICTANDO PERMISO O DENEGACION
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, mi_permiso);
            }
        }*/
////////////////////////////////////////////////
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        ejecutarServicio("http://192.168.1.14:80/webservices/insertar.php");//ip local + direccion de webservice
            btnRegistrar.setEnabled(false);

        }
    });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), activity_detector.class);
                startActivityForResult(intent, 0);
            }
        });


/*
        buscar.setOnClickListener(new View.OnClickListener() {
            // private View view; //REVISAR ESTA VARIABLEEEEE COMPARAR CON MAINACTIVITY
            @Override
            public void onClick(View v) {
                //this.view = view;//REVISAR ESTA VARIABLE COMPARAR CON MAINACTIVITY
                buscarMalware("http://192.168.1.9:80/webservices/buscar2.php"); // ?nombre=+validacion.getText()+"" (esto se corto desde el igual de esta linea)//revisar, aqui se pone la palabra que enviara a buscar, o campo
                //para prueba se puso validacion, cuadro para poner palabra que buscara y resultado para dar la respuesta.
                //debe estar asi: //buscarMalware("http://192.168.1.9:80/webservices/buscar.php?NombreMalware="+validacion.getText()+"");
            }
        });
        */
//ultimo desde el otro java
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case mi_permiso: {
                //check estacion de duracion de resultado is mas que 0 o es igualn a oermission granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //ahora broadcast receiver trabajara en segundo plan
                    Toast.makeText(this, "Gracias por permitir", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "No puedo hacer nada hasta que tu permitas el acceso", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

*/
    /////////////////////////////////////////////////////////////////////////////
    /*
    final Myreceiver receiver = new Myreceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);

            for(i=0; i<msg.length();i++)
            {
                while(!Character.isLetter(msg.charAt(i)) && (i+1) <msg.length() )
                    i++;
                posicion = i;
                longitud = 0;
                contador++;

                while(Character.isLetter(msg.charAt(i)) && (i+1) <msg.length() )
                {
                    longitud++;
                    i++;
                }
                palabra_rastreadora=msg.substring(posicion,posicion+longitud);
                //reconozco minusculas y mayusculas de la palabra
                if (palabra_rastreadora.equals(palabra_objetivo)|| palabra_rastreadora.equals(palabra_objetivo.toUpperCase())||
                        palabra_rastreadora.equals(palabra_objetivo.toLowerCase()))
                {
                    veces_hallada++;
                    numberTv.setText(phoneNO);
                }

            }
            if(veces_hallada==0){
                messageTV.setText("El Mensaje es seguro."+" Es el siguiente: "+ msg + "y el contador es: " + contador +
                        ", la posicion es: "+ posicion + "la longitud es: "+ longitud);
                numberTv.setText(phoneNO);
                veces_hallada=0;
                contador=0;
                posicion=0;
                longitud=0;

            }
            else
            {
                messageTV.setText("El Mensaje NO es seguro. "+" NO ABRIR. '"+palabra_objetivo+"'"+"fue encontrado en la posicion: "+posicion);
                numberTv.setText(phoneNO);
                veces_hallada=0;
                contador=0;
                posicion=0;
                longitud=0;

            }
        }
};
    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

*/
/////////////////////////////////////////////////////////////////////////////
    private void ejecutarServicio(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
                btnIniciar.setEnabled(true);
                Edtnombre.setEnabled(false);
                Edtnumero.setEnabled(false);
                Edtcorreo.setEnabled(false);
                acu=acu+ var;
                var++;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"PROBLEMAS CON EL SERVIDOR, CONTACTAR A SOPORTE TECNICO",Toast.LENGTH_SHORT).show();
                btnRegistrar.setEnabled(true);
            }
        })  {
            //@Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros= new HashMap<String,String>();

                int var = MainActivity.this.var;
                parametros.put("IdUser", String.valueOf(acu)); //aqui debe enviar el valor que va acumulando, llena registros borrados tambien, pero en orden ascendente hasta continuar con umeracion regular, esto solo en caso de reinicio de app.
                parametros.put("nombre",Edtnombre.getText().toString());
                parametros.put("numero",Edtnumero.getText().toString());
                parametros.put("correo",Edtcorreo.getText().toString());
                return parametros;

            }

        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /* ultimo de prueba desde el otro java
    public void buscarMalware(String URL) {

        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.contains("correcto")) {
                    Intent intent = new Intent(getApplicationContext(), activity_detector.class);
                    startActivity(intent);
                    //resultado.setText(response);
                    //Toast.makeText(MainActivity.this, "malware encontrado en sms, es peligroso", Toast.LENGTH_SHORT).show();
                    //PROBLEMA AQUIIII, REVISAR YA QUE NO VALIDA
                }
                else {
                    Toast.makeText(MainActivity.this, "no hay malware peligroso", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"PROBLEMAS CON EL SERVIDOR, CONTACTAR A SOPORTE TECNICO",Toast.LENGTH_SHORT).show();
            }
        }){
            //@Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros= new HashMap<String, String>();
                //parametros.put("IdMalware", String.valueOf(acu2));
                parametros.put("NombreMalware",validacion.getText().toString());
                // parametros.put("IdUser", String.valueOf(contador));
                return parametros;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

*/

}