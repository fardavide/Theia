package studio.forface.theia.utils

/** With this we are sure all the branch are required for `when` without assignment */
val <T : Any> T.exhaustive get() = this