package studio.forface.theia.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import studio.forface.theia.TheiaConfig
import studio.forface.theia.TheiaParams.ScaleType.*
import studio.forface.theia.TheiaParams.Shape.Round
import studio.forface.theia.cache.mins
import studio.forface.theia.dsl.imageUrl
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.theia
import studio.forface.theia.invoke

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
            defaultPlaceholderDrawableRes = R.drawable.ic_favorite
            loggingEnabled = true

            defaultUseCache = false
            defaultForceBitmap = true
        }

        // Theia by ImageView

        centerImageView.theia {
            imageUrl = GIF_IMAGE_URL
            scaleType = Center
        }
        cropImageView.theia {
            imageUrl = GIF_IMAGE_URL
            scaleType = Crop
        }
        fitImageView.theia {
            imageUrl = GIF_IMAGE_URL
            scaleType = Fit
        }
        stretchImageView.theia {
            imageUrl = GIF_IMAGE_URL
            scaleType = Stretch
        }

        hugeCenterImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            scaleType = Center
            shape = Round
        }
        hugeCropImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            scaleType = Crop
            shape = Round
        }
        hugeFitImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            scaleType = Fit
            shape = Round
        }
        hugeStretchImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            scaleType = Stretch
            shape = Round
        }
    }
}
