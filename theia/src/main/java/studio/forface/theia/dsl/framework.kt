@file:Suppress("unused")

package studio.forface.theia.dsl

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import studio.forface.theia.Theia

/*
 * A set of abstract classes that inherit from Framework components and that can get an instance of `Theia`, since
 * calling `newTheiaInstance` will create every time a new instance
 *
 * Author: Davide Giuseppe Farella
 */

/** A components that holds an instance of [Theia] */
interface TheiaComponent {

    /** A strong reference to a single instance of [Theia] for the [TheiaComponent] */
    val theia: Theia
}

/**
 * An abstract `Activity` that holds a single strong instance of [Theia], since calling `getTheia` will create
 * every time a new instance
 *
 * NOTE: Use this is NOT required for benefit of [Theia], if you prefer, you cant simply implement
 * `val theia by lazy { newTheiaInstance() }` by in your `Activity`
 */
abstract class TheiaActivity: AppCompatActivity(), TheiaComponent {

    /** A strong reference to a single instance of [Theia] for this component */
    override val theia by lazy { newTheiaInstance }
}

/**
 * An abstract [Fragment] that holds a single strong instance of [Theia], since calling `getTheia` will create
 * every time a new instance
 *
 * NOTE: Use this is NOT required for benefit of [Theia], if you prefer, you cant simply implement
 * `val theia by lazy { newTheiaInstance() }` by in your [Fragment]
 */
abstract class TheiaFragment: Fragment(), TheiaComponent {

    /** A strong reference to a single instance of [Theia] for this component */
    override val theia by lazy { newTheiaInstance }
}

/**
 * An abstract [RecyclerView.ViewHolder] that holds a single strong instance of [Theia], since calling `getTheia`
 * will create every time a new instance
 *
 * NOTE: Use this is NOT required for benefit of [Theia], if you prefer, you cant simply implement
 * `val theia by lazy { newTheiaInstance() }` by in your [RecyclerView.ViewHolder]
 */
abstract class TheiaViewHolder( itemView: View ): RecyclerView.ViewHolder( itemView ), TheiaComponent {

    /** A strong reference to a single instance of [Theia] for this component */
    override val theia by lazy { newTheiaInstance }
}