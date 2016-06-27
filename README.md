TextOverlay
-----------

All about activities have a version info widget somewhere. This library provides one that can be
used as a dialog or fragment. It honors default styling rules, Material-style:

![Dialog](doc/img/versioninfo-sample-dialog.png)
![Fragment](doc/img/versioninfo-sample-fragment.png)


Usage
-----

You can start / stop the text overlay service the default way in your activities:

```java
    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, TextOverlayService.class));
    }

    @Override
    protected void onPause() {
        stopService(new Intent(this, TextOverlayService.class));
        super.onPause();
    }
```

A more convenient way is to use the include activity lifecycle callbacks in your
app's application sub-class:

```java
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new TextOverlayActivityLifecycleCallbacks());
    }
}
```

Check out the sample app in `app/` to see it in action.


Download
--------

```groovy
compile 'saschpe:textoverlay:1.0.0'
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].


License
-------

    Copyright 2016 Sascha Peilicke

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
