# Service Always Background without any UI
 Sample application that will run a service on a background (**including API Level 24 or higher**).
 The application will always run on the background

## Requirements
 Android Studio Native Application using java

## Installation
 Download and import to your project
## Usage
This will close the app so that the user wont see any interface:
```java
Log.i("MyActivity", "Service Data Output ");
startService(new Intent(getApplicationContext(),MyServices.class));

//This will close the app after lunch
finish();
System.exit(0);



output after the app is close

2021-03-25 10:50:44.664 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 26
2021-03-25 10:50:44.664 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 19
2021-03-25 10:50:44.665 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 13
2021-03-25 10:50:44.665 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 4
2021-03-25 10:50:44.666 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 26
2021-03-25 10:50:44.666 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 6
2021-03-25 10:50:44.667 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 27
2021-03-25 10:50:44.667 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 10
2021-03-25 10:50:44.668 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 26
2021-03-25 10:50:44.668 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 7
2021-03-25 10:50:44.669 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 14
2021-03-25 10:50:44.670 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 13
2021-03-25 10:50:44.670 25075-25075/com.sample.servicealwaysbackground I/MyActivity: seconds remaining: 26


```

This will Launch the other apps:
```java
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

```

## Notes
 * The objective of this example is to show how the background service work
 * Native Service only work using native code

## License
This project is licensed under the terms of the [MIT License](https://opensource.org/licenses/MIT).
