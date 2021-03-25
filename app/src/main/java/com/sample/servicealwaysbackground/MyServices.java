package com.sample.servicealwaysbackground;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
public class MyServices extends Service {
    public MyServices() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);
        //Toast.makeText(getApplicationContext(),"This is a Service running in Background", Toast.LENGTH_SHORT).show();
        Timer();
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        Log.i("MyActivity", "Our Service on the background ");
        //LuanchApp("com.android.chrome");

        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }

    public  void Timer(){
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.i("MyActivity", "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.i("MyActivity", "done!");
            }
        }.start();
    }
    public void LuanchApp(String packageName){
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i("MyActivity", "Start APP");
            startActivity(intent);
        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            Log.i("MyActivity", "Download APP ");
            startActivity(intent);
        }
    }


}
