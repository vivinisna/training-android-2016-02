package com.artivisi.app.android.pembayaran.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.artivisi.app.android.pembayaran.R;
import com.artivisi.app.android.pembayaran.restclient.PembayaranClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by endymuhardin on 4/13/16.
 */
public class RegistrasiGcmService extends IntentService {
    private static final String TAG = "RegIntentService";

    public RegistrasiGcmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;
        try {
            Log.d(TAG, "Memulai registasi GCM");
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            String email = intent.getStringExtra("email");
            registrasiTokenKeServer(email, token);
        } catch (IOException e) {
            Log.e(TAG, "GCM Registration gagal : "+e.getMessage());
        }
        Log.i(TAG, "GCM Registration Token: " + token);
    }

    private void registrasiTokenKeServer(String email, String token){
        PembayaranClient p = new PembayaranClient();
        Log.d(TAG, "Mendaftarkan token ke server");
        p.registrasiToken(email, token);
        Log.d(TAG, "Pendaftaran token sukses");
    }
}
