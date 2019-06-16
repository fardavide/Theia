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
            cacheDuration = 5.mins
            defaultPlaceholderDrawableRes = R.drawable.avd_loading
            loggingEnabled = false
        }

        // Theia by ImageView

        // Small square
        val smallParams = theiaParams { imageUrl = SMALL_IMAGE_URL }

        centerImageView.theia( smallParams ) {
            scaleType = Center
        }
        cropImageView.theia( smallParams ) {
            scaleType = Crop
        }
        fitImageView.theia( smallParams ) {
            scaleType = Fit
        }
        stretchImageView.theia( smallParams ) {
            scaleType = Stretch
        }

        // Huge round
        val hugeParams = theiaParams {
            imageUrl = HUGE_IMAGE_URL
            shape = Rounded( Corners ofDp 32 )
        }

        hugeCenterImageView.theia( hugeParams ) {
            scaleType = Center
        }
        hugeCropImageView.theia( hugeParams ) {
            scaleType = Crop
        }
        hugeFitImageView.theia( hugeParams ) {
            scaleType = Fit
        }
        hugeStretchImageView.theia( hugeParams ) {
            scaleType = Stretch
        }

        // Animated
        val animatedParams = theiaParams { imageDrawableRes = R.drawable.avd_loading }

        animOnceImageView.theia( animatedParams ) {
            animationLoop = Once
        }
        animForeverImageView.theia( animatedParams ) {
            animationLoop = Forever
        }
        animTimedImageView.theia( animatedParams ) {
            animationLoop = Every( 5.secs )
        }
        animClickImageView.theia( animatedParams ) {
            animationLoop = OnClick
        }
    }
}
