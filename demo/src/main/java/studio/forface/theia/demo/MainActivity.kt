package studio.forface.theia.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import studio.forface.theia.TheiaParams.ScaleType.*
import studio.forface.theia.TheiaParams.Shape.Round
import studio.forface.theia.dsl.*

private const val SMALL_IMAGE_URL = "https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg"
private const val MEDIUM_IMAGE_URL = "https://sample-videos.com/img/Sample-png-image-1mb.png"
private const val HUGE_IMAGE_URL = "http://www.ricoh-imaging.co.jp/english/products/q-s1/ex/img/bod_mainImg_01.jpg"

class MainActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        // Theia by ImageView

        centerImageView.theia {
            imageUrl = SMALL_IMAGE_URL
            placeholderDrawableRes = R.drawable.ic_favorite
            scaleType = Center
        }
        cropImageView.theia {
            imageUrl = SMALL_IMAGE_URL
            placeholderDrawableRes = R.drawable.ic_favorite
            scaleType = Crop
        }
        fitImageView.theia {
            imageUrl = SMALL_IMAGE_URL
            placeholderDrawableRes = R.drawable.ic_favorite
            scaleType = Fit
        }
        stretchImageView.theia {
            imageUrl = SMALL_IMAGE_URL
            placeholderDrawableRes = R.drawable.ic_favorite
            scaleType = Stretch
        }

        hugeCenterImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            placeholderDrawableRes = R.drawable.ic_favorite
            scaleType = Center
            shape = Round
        }
        hugeCropImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            placeholderDrawableRes = R.drawable.ic_favorite
            scaleType = Crop
            shape = Round
        }
        hugeFitImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            placeholderDrawableRes = R.drawable.ic_favorite
            scaleType = Fit
            shape = Round
        }
        hugeStretchImageView.theia {
            imageUrl = HUGE_IMAGE_URL
            placeholderDrawableRes = R.drawable.ic_favorite
            scaleType = Stretch
            shape = Round
        }
    }
}
