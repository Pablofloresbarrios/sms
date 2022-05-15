package com.example.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class activity_detector extends AppCompatActivity {

    TextView messageTV, numberTv;
    EditText validacion;
    TextView analizador;
    private static final int mi_permiso = 0;
    String palabra_objetivo = "Http:";//la palabra objetivo debe reconocerse solo ella seguida de un simbolo o espacio
    String palabra_rastreadora = "";
    String resultado;
    String analisis = "";
    int longitud = 0;
    int encontradobase = 0;
    int posicion = 0;
    int contador = 0;
    int acumulador =0;
    int veces_hallada = 0;
    int vali=0;
    int i = 0;
    int a=0;
    int var2 = 0;
    int acu2 = 0;
    Button buscar,reco;
    String valor1="El mensaje contiene palabras con enlaces y webs de páginas no seguras, es recomendable no ingresar a los enlaces contenidos.";
    //String valor2="El mensaje contiene una web reportada como maliciosa, NO INGRESAR";
   private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detector);
        messageTV = findViewById(R.id.mensaje);
        numberTv = findViewById(R.id.numero);
        analizador = findViewById(R.id.analizador);
        validacion = findViewById(R.id.validacion); //para prueba
        buscar = findViewById(R.id.buscar);
        reco = findViewById(R.id.reco);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buscarMalware("http://192.168.1.14:80/webservices/buscar3.php?NombreMalware="+ validacion.getText()+""); // ?nombre=+validacion.getText()+"" (esto se corto desde el igual de esta linea)//revisar, aqui se pone la palabra que enviara a buscar, o campo
            }
        });

        reco.setOnClickListener(new View.OnClickListener() { //cuidado en repetir mismo setonclick.. en otro activity sino se detiene el app.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), activity_recomendaciones.class);
               // if(vali==1)
                intent.putExtra("valor1",valor1);
                //if(vali==2)
                //{intent.putExtra("valor2",valor2);}
                startActivityForResult(intent, 0);
            }
        });

        buscar.setVisibility(View.GONE);
        reco.setVisibility(View.GONE);

        validacion.setVisibility(View.GONE);
    }


    final Myreceiver receiver = new Myreceiver() {
        @Override

        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            //msg es el cuerpo del mensaje

            validacion.setText(msg);


            buscar.performClick();

            while(encontradobase==2 || encontradobase==0) {

                    if (msg.indexOf(palabra_objetivo, posicion) >= 0 || msg.indexOf(palabra_objetivo.toLowerCase(), posicion) >= 0 ||
                            msg.indexOf(palabra_objetivo.toUpperCase(), posicion) >= 0 ) {
                        //contador = 1;
                        messageTV.setText("ATAQUE PHISHING, NO ABRIR!");
                        numberTv.setText("El numero es: " + phoneNO); //puse contador en vez de phone para validar que llegue a esta iteracion.
                        analizador.setText("El mensaje es: " + msg);
                        messageTV.setTextColor(Color.RED);
                        encontradobase = 1;
                        //vali=1;
                        Toast.makeText(activity_detector.this, "ATAQUE PHISHING, NO ABRIR SMS!", Toast.LENGTH_SHORT).show();
                        reco.setVisibility(View.VISIBLE);

                    } else                 //if(msg.indexOf(palabra_objetivo,posicion)<0|| msg.indexOf(palabra_objetivo.toLowerCase(),posicion)<0 || msg.indexOf(palabra_objetivo.toUpperCase(),posicion)<0)
                    {
                        //contador = 2;
                        messageTV.setText("El Mensaje es seguro");//, la palabra objetivo es: " + palabra_objetivo + "fue encontrado en la posicion: " + posicion + "la cantidad de digitos son: " + acumulador);
                        numberTv.setText("El numero es: " + phoneNO);
                        analizador.setText("El mensaje es: " + msg);
                        messageTV.setTextColor(Color.parseColor("#689F38"));
                        veces_hallada = 0;
                        //vali=0;
                        //contador = 0;
                        //posicion = 0;
                        //longitud = 0;
                        //acumulador = 0;
                        encontradobase = 1;
                        Toast.makeText(activity_detector.this, "SMS seguro", Toast.LENGTH_SHORT).show();
                        reco.setVisibility(View.GONE);
                    }
                }

            }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    public void buscarMalware(String URL) {

        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("correcto")) {
                    //buscar.performClick();
                    contador=0;
                    messageTV.setText("ATAQUE PHISHING, NO ABRIR!");//"El Mensaje NO es seguro. " + " NO ABRIR. '" + palabra_objetivo + "'" + "fue encontrado en la posicion: " + posicion);
                    //analizador.setText(palabra_rastreadora);
                    //Toast.makeText(activity_detector.this, "ATAQUE PHISHING, NO ABRIR SMS!", Toast.LENGTH_SHORT).show();
                    //numberTv.setText("El numero es: ");
                    encontradobase=0;
                    //vali=2;
                    messageTV.setTextColor(Color.RED);

                }
                else // (response.contains("No funciona"))
                {
                    //messageTV.setText("NO Encontrado en base");
                    ////    numberTv.setEnabled(false);
                    encontradobase=2;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity_detector.this,"PROBLEMAS CON EL SERVIDOR, CONTACTAR A SOPORTE TECNICO",Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros= new HashMap<String, String>();
                //parametros.put("IdMalware", String.valueOf(acu2));
                ////parametros.put("NombreMalware",analizador.getText().toString());
                parametros.put("NombreMalware",analizador.getText().toString());
               // parametros.put("IdUser", String.valueOf(contador));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }


    }
