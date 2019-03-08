# Theia

[![Download](https://api.bintray.com/packages/4face/Theia/studio.forface.theia/images/download.svg)](https://bintray.com/4face/Theia/studio.forface.theia/_latestVersion)  ![MinSDK](https://img.shields.io/badge/MinSDK-14-f44336.svg)  [![star this repo](http://githubbadges.com/star.svg?user=4face-studi0&repo=ViewStateStore&style=flat&color=fff&background=4caf50)](https://github.com/4face-studi0/Theia)  [![fork this repo](http://githubbadges.com/fork.svg?user=4face-studi0&repo=Theia&style=flat&color=fff&background=4caf50)](https://github.com/4face-studi0/Theia/fork)



**Theia** is a *lightweight* image laoder for Android.

<u>Caching is currently not supported yet</u>



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

###### From `ImageView`

```kotlin
myImageView.theia {
    imageUrl = "https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg"
}
```

##### From *Any*

```kotlin
newTheiaUnhandleInstance( resources ) {
    target = myImageView
    imageUrl = "https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg"
}
```



### Params

* `image`:  The `AsyncImageSource` for the image to load into `target`

  Extension:

  * `imageBitmap`
  * `imageDrawable`
  * `imageDrawableRes`
  * `imageFile`
  * `asyncImageFile`
  * `imageUrl`



* `target`: The `ImageView` where to load the `image`



* `placeholder`: he image to load as placeholder for async requests.
  It will be ignored for *successful* `Sync` requests, but it will be used anyway if:

  * there is an error loading `image` and no `error` is supplied

  * `error` is `Async`



* `error`: The image to load if some error occurred while loading `image`



* `scaleError`: If _true_  `error` will respect `scaleType`, else use `TheiaConfig.defaultScaleError` ( `TheiaParams.ScaleType.Center` )



* `scalePlaceholder`: If _true_  `placeholder` will respect `scaleType`, else use `TheiaConfig.defaultScalePlaceholder` ( `TheiaParams.ScaleType.Center` )



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



* `extraTransformations`: Add a new [TheiaTransformation] to [extraTransformations] within plus operator.

  E.g.

  ```
  theia {
      + SomeCustomTransformation
      + AnotherTransformation
  }
  ```



### Configuration
