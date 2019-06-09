@file:Suppress("unused")

package studio.forface.theia.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import studio.forface.theia.*
import studio.forface.theia.AnimationLoop.*
import studio.forface.theia.TheiaParams.ScaleType.*
import studio.forface.theia.TheiaParams.Shape.Rounded
import studio.forface.theia.dsl.imageDrawableRes
import studio.forface.theia.dsl.imageUrl
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.theia

private const val GIF_IMAGE_URL = "https://sirv.sirv.com/website/HELLO.gif"
private const val SMALL_IMAGE_URL = "https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg"
private const val MEDIUM_IMAGE_URL = "https://sample-videos.com/img/Sample-png-image-1mb.png"
private const val HUGE_IMAGE_URL = "http://www.ricoh-imaging.co.jp/english/products/q-s1/ex/img/bod_mainImg_01.jpg"

class MainActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        TheiaConfig {
            cacheDuration = 5.days
            defaultPlaceholderDrawableRes = R.drawable.avd_loading
            loggingEnabled = true
        }

        // Theia by ImageView

        // Small square
        centerImageView.theia {
            imageUrl = MEDIUM_IMAGE_URL
            scaleType = Center
        }
        cropImageView.theia {
            imageUrl = MEDIUM_IMAGE_URL
            scaleType = Crop
        }
        fitImageView.theia {
            imageUrl = MEDIUM_IMAGE_URL
            scaleType = Fit
        }
        stretchImageView.theia {
            imageUrl = MEDIUM_IMAGE_URL
            scaleType = Stretch
        }

        // Huge round
        val roundedShape = Rounded( Corners ofDp 32 )
        hugeCenterImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            scaleType = Center
            shape = roundedShape
        }
        hugeCropImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            scaleType = Crop
            shape = roundedShape
        }
        hugeFitImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            scaleType = Fit
            shape = roundedShape
        }
        hugeStretchImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            scaleType = Stretch
            shape = roundedShape
        }

        // Animated
        animOnceImageView.theia {
            imageDrawableRes = R.drawable.avd_loading
            animationLoop = Once
        }
        animForeverImageView.theia {
            imageDrawableRes = R.drawable.avd_loading
            animationLoop = Forever
        }
        animTimedImageView.theia {
            imageDrawableRes = R.drawable.avd_loading
            animationLoop = Every( 5.secs )
        }
        animClickImageView.theia {
            imageDrawableRes = R.drawable.avd_loading
            animationLoop = OnClick
        }
    }
}
