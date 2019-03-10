# Theia

[![Download](https://api.bintray.com/packages/4face/Theia/studio.forface.theia/images/download.svg)](https://bintray.com/4face/Theia/studio.forface.theia/_latestVersion)  ![MinSDK](https://img.shields.io/badge/MinSDK-14-f44336.svg)  [![star this repo](http://githubbadges.com/star.svg?user=4face-studi0&repo=Theia&style=flat&color=fff&background=4caf50)](https://github.com/4face-studi0/Theia)  [![fork this repo](http://githubbadges.com/fork.svg?user=4face-studi0&repo=Theia&style=flat&color=fff&background=4caf50)](https://github.com/4face-studi0/Theia/fork)

**Theia** is a *lightweight* image loader for Android.

<u>With Cache System!</u>

## Installation

###### Groovy

`implementation "studio.forface.theia:theia:last_version"`

###### Kotlin-DSL

`implementation( "studio.forface.theia:theia:last_version" )`

## Usage

### Base

###### From `FragmentActivity`, `Fragment`, `View` or  `RecyclerView.ViewHolder`

```kotlin
newTheiaInstance {
    target = myImageView
    imageUrl = "https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg"
}
```

For avoid to create many instances for a single component, keep a single reference to `Theia`

E.g. `private val theia by lazy { newTheiaInstance }`

###### From `ImageView`

```kotlin
myImageView.theia {
    imageUrl = "https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg"
}
```

###### From *Any*

```kotlin
newTheiaUnhandleInstance( resources ) {
    target = myImageView
    imageUrl = "https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg"
}
```

In this case, requests need to be purged manually with `theia.purgeRequests()`

### Params

* `image`:  The `AsyncImageSource` for the image to load into `target`

  Default is *null*.

  This param in **"partially" required**, if no value is passed, `Theia` will try to use `palceholder`, if also `placeholder` is null, an `ImageSourceNotSetException` will be thrown

  Extensions:

  * `imageBitmap`
  * `imageDrawable`
  * `imageDrawableRes`
  * `imageFile`
  * `asyncImageFile`
  * `imageUrl`


* `target`: The `ImageView` where to load the `image`

  This param is **required**, if no value is passed, a `TargetNotSetException` will be thrown

  On `PerTargetedTheia` this value is already set, since the instance can only be retrieved from an `ImageView` and can't be changed

* `placeholder`: he image to load as placeholder for async requests.

  Default is `TheiaConfig.defaultPlaceholder` ( `null` )

  It will be ignored for *successful* `Sync` requests, but it will be used anyway if:

  * there is an error loading `image` and no `error` is supplied
  * `error` is `Async`

  Extensions:

  - `placeholderBitmap`
  - `placeholderDrawable`
  - `placeholderDrawableRes`
  - `placeholderFile`

* `error`: The image to load if some error occurred while loading `image`

  Default is `TheiaConfig.defaultError` ( `null` )

  Extensions:

  - `errorBitmap`
  - `errorDrawable`
  - `errorDrawableRes`
  - `errorFile`
  - `asyncErroreFile`
  - `errorUrl`

* `scaleError`: If _true_  `error` will respect `scaleType`, else use `TheiaConfig.defaultScaleError` ( `TheiaParams.ScaleType.Center` )

  Default is `TheiaConfig.defaultScaleError` ( `false` )

* `scalePlaceholder`: If _true_  `placeholder` will respect `scaleType`, else use `TheiaConfig.defaultScalePlaceholder` ( `TheiaParams.ScaleType.Center` )

  Default is `TheiaConfig.defaultScalePlaceholder` ( `false` )

* `scaleType`: The `TheiaParams.ScaleType` to apply to `image`

  Default is: `TheiaParams.defaultScaleType` ( `TheiaParams.ScaleType.Center` )

  Supported types:

  * `Center`: The image will be resized to be centered in the `ImageView`
    Proportions will be preserved, the image will be fully visible and so one dimension will be smaller than `ImageView`

  * `Crop`: The image will cropped to the size of the `ImageView`

    No resizing will be applied

  * `Fit`: The image will be resized to fit the `ImageView`

    Proportions will be preserved, the image will be partially visible on its width or height as long as the `ImageView` has not the same proportions as the image.

  * `Stretch`: The image will be stretched to fill the `ImageView`

    Proportions won't be preserved and the image will be distorted

* `shape`: The `TheiaParams.Shape` to apply to `image`

  Default is `TheiaConfig.defaultShape` ( `TheiaParams.Shape.Square` )

  Supported shapes:

  * `Round`
  * `Square`

* `useCache`: If *true* cache will be used for this request. 

  Default is `TheiaConfig.defaultUseCache` ( `true` )

* `extraTransformations`: Add a new [TheiaTransformation] to [extraTransformations] within plus operator.

  E.g.

  ```
  theia {
      + SomeCustomTransformation
      + AnotherTransformation
  }
  ```

### Loggin & Configuration

`Theia` can be configured via `TheiaConfig`, which also can be invoked with a lambda with itself as receiver:

Both `TheiaConfig.defaultShape = Round` and `TheiaConfig { defaultShape = Round }` are valid. The invocation syntax can be use for set multiple values

E.g.

```kotlin
TheiaConfig {
    defaultShape = Round
    defaultScaleError = true
}
```

##### Configuration params:

* `cacheDuration`: A `Duration` representing how long the cached images should be used before use fresh data. 

  Default is 1 month ( `1.months` )

* `defaultCacheDirectory`: The default `File` directory where to store cache.
  Note: changing default directory may require `permission.READ_EXTERNAL_STORAGE` and `permission.WRITE_EXTERNAL_STORAGE`
  Default is `initDefaultCacheDir` ( `context.cacheDir` )

* `defaultError`: The default image to be load as error if no other value is set. 

  Default is _null_

* `defaultPlaceholder`: The default image to be used as placeholder if no other value is set. 

  Default is _null_


* `defaultScaleError`: If _true_ `error`s will respect the `TheiaParams.scaleType`, else use. `defaultScaleType `

  Default is *false*


* `defaultScalePlaceholder`: If _true_ `placeholder`s will respect the `TheiaParams.scaleType`, else use. `defaultScaleType `

  Default is *false*

* `defaultShape`: The default `TheiaParams.Shape` to use if none is specified explicitly. 

  Default is `TheiaParams.Shape.Square`

* `defaultUseCache`: If *true* use cache. 

  Default is *true*


* `httpClient`: The `HttpClient` for execute web calls

  Default is *HttpClient()*

* `logginEnabled`: Whether the logging should be enabled. 

  Default is `BuildConfig.DEBUG`

* `logger`: A `TheiaLogger` for print messages. 

  Default is `DefaultTheiaLogger`

#### Logging

Define a custom logger by creating a subclass of `TheiaLogger` and set in `TheiaConfig.logger`

When defining your Logger, remember to use `logEnabled` for handle different outputs, since we want to give a minimal feedback for critical errors, even if log is disabled.

I.e.

```kotlin
override fun error( ex: TheiaException ) {
    if ( logEnabled ) ex.printStackTrace()
    else Log.d( THEIA_NAME, ex.actualMessage )
}
```

### Extra

#### Cache

For clean cache manually, use: `theia.cleanCache( 5.days )`

It willl delete all the `File`s with extension `CACHE_EXT` with `File.lastModified` older than `olderThan` from `TheiaConfig.defaultCacheDirectory`
`olderThan`  is `Duration` for query files to delete. Default is `0.mins` so all the caches will be deleted.

#### Android components

There are also some custom framework components that provide a single instance of `Theia`:

* `TheiaActivity`
* `TheiaFragment`
* `TheiaViewHolder` ( inherit from `RecyclerView.ViewHolder` )