package com.example.a77011_40_15.a1streetart.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

/**
 * Cette classe permet d'envoyer des SMS soit en passant par l'application standard de Google soit en envoi direct.
 */
public class Texto {

    Texto()
    {
    }

    /**
     * Envoi direct de SMS
     *
     * @param message le corps du SMS
     * @param phonenumber le numéro à adresser
     */
    public void sendNow(String message, String phonenumber)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phonenumber, null, message, null, null);
    }

    /**
     * Envoi "diff&eacute;r&eacute;", en passant un intent &agrave; l'application SMS standard
     *
     * @param message le corps du SMS
     * @param phonenumber le num&eacute;ro &agrave; adresser
     * @param context le context, parce qu'on en a besoin pour lancer l'intent
     */
    public void sendLater(String message, String phonenumber, Context context)
    {
        Intent intentSms = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + phonenumber));
        intentSms.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentSms.putExtra("sms_body", message);
        context.startActivity(intentSms);
    }
}
