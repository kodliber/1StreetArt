package com.example.a77011_40_15.a1streetart.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

public class Texto {

    Texto()
    {
    }

    public void sendNow(String message, String phonenumber)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phonenumber, null, message, null, null);
    }

    public void sendLater(String message, String phonenumber, Context context)
    {
        Intent intentSms = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + phonenumber));
        intentSms.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentSms.putExtra("sms_body", message);
        context.startActivity(intentSms);
    }
}
