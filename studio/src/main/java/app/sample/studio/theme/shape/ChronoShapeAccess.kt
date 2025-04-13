package app.sample.studio.theme.shape

abstract class ChronoShapeAccess {

    val none by lazy { ChronoShapes.None }

    /** DP: 8*/
    val small by lazy { ChronoShapes.Small }

    /** DP: 12 */
    val medium by lazy { ChronoShapes.Medium }

    /** DP: 16 */
    val large by lazy { ChronoShapes.Large }

    /** DP: 20 */
    val extraLarge by lazy { ChronoShapes.ExtraLarge }

    val full by lazy { ChronoShapes.Full }

}
