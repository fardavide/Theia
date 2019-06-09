package studio.forface.theia

/** An enum representing how the animation should loop */
sealed class AnimationLoop {

    /** Animation won't start automatically and must be triggered automatically */
    object NoLoop : AnimationLoop()

    /** Animation will be executed only once */
    object Once : AnimationLoop()

    /** Animation will loop forever */
    object Forever : AnimationLoop()

    /** Animation will be executed when the user click on the `ImageView` */
    object OnClick : AnimationLoop()

    /** Animation will be executed once every `duration` */
    class Every( internal val duration: Duration ) : AnimationLoop()
}