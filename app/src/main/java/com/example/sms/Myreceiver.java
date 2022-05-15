package com.example.sms;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class Myreceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNO="";


    @Override
    public void onReceive(Context context, Intent intent) {
        //retribuir la accion general para ser mostrado en el display

        Log.i(TAG,"Intent Received: " + intent.getAction());
        if(intent.getAction() == SMS_RECEIVED)
        {
            //RETRIBUIR UN MAPA PARA EXTENDER LA DATA DESDE EL INQUILINO
            Bundle dataBundle =intent.getExtras();
            if(dataBundle!=null) {
                //creando PDU (protocol data unit) objeto para el protocolo de transferencia de mensajes
                Object[] mypdu = (Object[]) dataBundle.get("pdus");


                final SmsMessage[] messages = new SmsMessage[mypdu.length];
                // if (messages.toString().startsWith("ok")==true)
                //{
                for (int i = 0; i < mypdu.length; i++) {
                    //for carga las versiones >=23
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = dataBundle.getString("format");
                        //desde el PDU se peude obtener todos los objetos y los objetos mensaje siguiendo la linea de eocdigo
                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);
                    } else {
                        //level del API 23
                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }

                    msg = messages[i].getMessageBody();

                    phoneNO = messages[i].getOriginatingAddress();
                }
    //22-1-22       Toast.makeText(context, "Mensaje: " + msg + "\nNumero: " + phoneNO, Toast.LENGTH_LONG).show();

                //}
            }
        }
    }


}
