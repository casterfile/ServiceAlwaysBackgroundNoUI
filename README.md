# Service Always Background
 Sample application that will run a service on a background (**including API Level 24 or higher**).
 The application will always run on the background

## Requirements
 Android Studio Native Application using java

## Installation
 Download and import to your project
## Usage
The following example demonstrates how to open a file:
```java
public class MyServices extends Service {
    public MyServices() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);
        Toast.makeText(getApplicationContext(),"This is a Service running in Background",
                Toast.LENGTH_SHORT).show();
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
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }
}

output

2021-03-25 02:26:31.104 20071-20071/com.sample.servicealwaysbackground I/MyActivity: Our Service on the background 
2021-03-25 02:26:31.109 20071-20071/com.sample.servicealwaysbackground I/MyActivity: Our Service on the background 
2021-03-25 02:26:31.112 20071-20071/com.sample.servicealwaysbackground I/MyActivity: Our Service on the background 
2021-03-25 02:26:31.116 20071-20071/com.sample.servicealwaysbackground I/MyActivity: Our Service on the background 
2021-03-25 02:26:31.120 20071-20071/com.sample.servicealwaysbackground I/MyActivity: Our Service on the background 
2021-03-25 02:26:31.127 20071-20071/com.sample.servicealwaysbackground I/MyActivity: Our Service on the background 

```



## Notes
 * The objective of this example is to show how the background service work
 * Native Service only work using native code

## License
This project is licensed under the terms of the [MIT License](https://opensource.org/licenses/MIT).
