package studio.forface.theia

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

sealed class ResponseWrapper<Image>( val image: Image )

class BitmapResponse(image: Bitmap ) : ResponseWrapper<Bitmap>( image )

class DrawableResponse(image: Drawable) : ResponseWrapper<Drawable>( image )

typealias TheiaResponse = ResponseWrapper<*>