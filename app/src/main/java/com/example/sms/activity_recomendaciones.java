package com.example.sms;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_recomendaciones extends AppCompatActivity {

//Button reco;
Button regresar;
TextView recodatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);
        //reco= (Button) findViewById(R.id.Iniciar);
        regresar=(Button) findViewById(R.id.regresar);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        recibirdatos();

        regresar.setOnClickListener(new View.OnClickListener() { //cuidado en repetir mismo setonclick.. en otro activity sino se detiene el app.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), activity_detector.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    private void recibirdatos(){
        Bundle extras=getIntent().getExtras();
        String v1=extras.getString("valor1");
        //String v2=extras.getString("valor2");

        recodatos=(TextView) findViewById(R.id.recodatos);
        recodatos.setText(v1);

    }

}