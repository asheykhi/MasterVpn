package ir.alishi.mastervpn.core.extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

fun DrawScope.drawThumb(
    thumbStrokeWidth: Float,
    color: Color = Color(0xFFA30022)
) {
    drawArc(
        color = color,
        startAngle = -60f,
        sweepAngle = 300f,
        useCenter = false,
        topLeft = Offset.Zero,
        style = Stroke(
            width = thumbStrokeWidth,
            cap = StrokeCap.Round,
        )
    )

    drawLine(
        color = color,
        start = center,
        end = Offset(
            x = center.x,
            y = Offset.Zero.y - 5 // because of padding
        ),
        cap = StrokeCap.Round,
        strokeWidth = thumbStrokeWidth
    )
}

fun DrawScope.drawCircleProgressBar(
    color: Color = Color.DarkGray,
    progressStrokeWidth: Float,
    sweepAngle: Float,
) {
    drawArc(
        color = Color.LightGray,
        startAngle = -90f,
        sweepAngle = 360f,
        useCenter = false,
        topLeft = Offset.Zero,
        style = Stroke(
            width = progressStrokeWidth,
            cap = StrokeCap.Round,
        )
    )

    drawArc(
        color = color,
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset.Zero,
        style = Stroke(
            width = progressStrokeWidth + 3,
            cap = StrokeCap.Round,
        )
    )
}
