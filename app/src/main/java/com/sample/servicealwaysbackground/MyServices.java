package com.sample.servicealwaysbackground;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MyServices extends Service {
    public MyServices() {
        //Timer();
        //Update("http://192.168.1.6/download/FB.apk");
        new DownloadFileFromURL().execute("http://192.168.1.6/download/FB.apk");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);
        //Toast.makeText(getApplicationContext(),"This is a Service running in Background", Toast.LENGTH_SHORT).show();

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
        //Log.i("MyActivity", "Our Service on the background ");
        //LuanchApp("com.android.chrome");

        //Log.i("MyActivity", "Chrome is running: "+ isForeground(getApplicationContext(), "com.android.chrome"));
        //ListOfActivity();
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }

    public  void Timer(){
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.i("MyActivity", "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.i("MyActivity", "done!");
                new MyServices();
            }


        }.start();
    }
    public void Update(String apkurl){
        try {
            URL url = new URL(apkurl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "app.apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();//till here, it works fine - .apk is download to my sdcard in download file

//            Intent promptInstall = new Intent(Intent.ACTION_VIEW)
//                    .setData(Uri.parse(PATH+"app.apk"))
//                    .setType("application/android.com.app");
//            startActivity(promptInstall);//installation is not working

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "app.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Update error!", Toast.LENGTH_LONG).show();
        }
    }

    public void InstallApp(){
        //OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+ "/download/" + "app.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "app.apk")), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

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

    public static boolean isForeground(Context ctx, String myPackage){
        ActivityManager manager = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);
        List< ActivityManager.RunningTaskInfo > runningTaskInfo = manager.getRunningTasks(1);

        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        if(componentInfo.getPackageName().equals(myPackage)) {
            return true;
        }
        return false;
    }

    public void ListOfActivity(){
//        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        final List<ActivityManager.RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
//
//        for (int i = 0; i < recentTasks.size(); i++)
//        {
//            Log.d("Executed app", "Application executed : " +recentTasks.get(i).baseActivity.toShortString()+ "\t\t ID: "+recentTasks.get(i).id+"");
//        }

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcessInfo.size(); i++) {
            Log.d("Executed app", "Application executed : " +runningAppProcessInfo.get(i).describeContents()+ "\t\t ID: "+runningAppProcessInfo.get(i)+"");
        }


    }



}



/**
 * Background Async Task to download file
 * */
class DownloadFileFromURL extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Bar Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //showDialog(progress_bar_type);
    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            // Output stream
//            OutputStream output = new FileOutputStream(Environment
//                    .getExternalStorageDirectory().toString()
//                    + "/app.apk");

            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+ "/download/" + "app.apk");

            Log.i("MyActivity", "Downloading OUTPUT: "+output);


            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("MyActivity: ", e.getMessage());
        }

        return null;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        //pDialog.setProgress(Integer.parseInt(progress[0]));
        Log.i("MyActivity", "Downloading: "+ Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
        //dismissDialog(progress_bar_type);
        Log.i("MyActivity", "Downloading: dismiss");

        //Run Install file
        MyServices myServices = new MyServices();
        myServices.InstallApp();
    }

}
