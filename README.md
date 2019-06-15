# Theia

[![Download](https://api.bintray.com/packages/4face/Theia/studio.forface.theia/images/download.svg)](https://bintray.com/4face/Theia/studio.forface.theia/_latestVersion)  ![MinSDK](https://img.shields.io/badge/MinSDK-14-f44336.svg)  [![star this repo](http://githubbadges.com/star.svg?user=4face-studi0&repo=Theia&style=flat&color=fff&background=4caf50)](https://github.com/4face-studi0/Theia)  [![fork this repo](http://githubbadges.com/fork.svg?user=4face-studi0&repo=Theia&style=flat&color=fff&background=4caf50)](https://github.com/4face-studi0/Theia/fork)

**Theia** is a *lightweight* image loader for Android.

<u>With Cache System and animated `Drawable`s!</u>

### [Full Doc here](https://4face-studi0.github.io/Theia/theia/)

![Theia gif](https://thumbs.gfycat.com/HiddenOblongAlaskanmalamute-size_restricted.gif)

## Wiki
[Full wiki here!](https://github.com/4face-studi0/Theia/wiki)

## Installation 

###### Groovy

`implementation "studio.forface.theia:theia:last_version"`

###### Kotlin-DSL

`implementation( "studio.forface.theia:theia:last_version" )`

## Usage

### Get instance
A **Theia** instance can be retrieved in many ways:

* from `FragmentActivity`, `Fragment`, `View` or `RecyclerView.ViewHolder`

  via `newTheiaInstance` ( provides instance of `Theia` ).

  In this case a new instance will be created ad every invocation, so it's better to keep a strong reference to it ( e.g. `private val theia by lazy ( newTheiaInstance )` )

  The requests will be bonded to the lifecycle of the receiver component

* from `ImageView`

  via `theia` ( provides instance of `PreTargetedTheia` )

  Also here a new instance will be created every time, but we don't need a strong reference, since usually only a single request is needed for an `ImageView`

  The request will be bonded to the lifecycle of the `ImageView`

* everywhere

  via `newTheiaUnhandledInstance` ( provides instance of `UnhandledTheia` and need a `Context` )

  Also here a new instance will be created ad every invocation, so it's better to keep a strong reference to it ( e.g. `private val theia by lazy ( newTheiaUnhandledInstance )` )

  The request won't be bonded to any lifecycle, so they need to be purged manually via `theia.purgeRequests()`

* using a custom component

  There are some custom framework components that provide a single instance of `Theia` ( `theia` ):

   * `TheiaActivity`
   * `TheiaFragment`
   * `TheiaViewHolder` ( inherit from `RecyclerView.ViewHolder` )

### Basic request
A request can be made using a `Theia` instance via the `load` function or `invoke` operator ( e.g. `theia { ... }` )
Every request need at least 2 params:
* an **image** ( or a **placeholder** )
* a **target** ( already defined on `PreTargetedTheia` )

A couple of request examples:
```kotlin
imageView.theia {
    imageUrl = "http://url.to/some_image.jpg"
    placeHolderDrawableRes = R.drawable.ic_placeholder
    errorUrl = "http://url.to/image_not_found.jpg"
}
```
From `Fragment`
```kotlin
newTheiaInstance {
    target = imageView
    imageFile = File( pathToImage )
    scaleType = Stretch
    shape = Round
    useCache = false
}
```
[More detail about request here](https://github.com/4face-studi0/Theia/wiki/Request-params)
