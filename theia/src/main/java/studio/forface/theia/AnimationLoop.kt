package studio.forface.theia

/** An enum representing how the animation should loop */
enum class AnimationLoop {

    /** Animation won't start automatically and must be triggered automatically */
    NoLoop,

    /** Animation will be executed only once */
    Once,

    /** Animation will loop forever */
    Forever,

    /** Animation will be executed when the user click on the `ImageView` */
    OnClick
}