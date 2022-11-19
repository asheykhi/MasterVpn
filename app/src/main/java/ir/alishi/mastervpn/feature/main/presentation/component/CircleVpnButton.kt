package ir.alishi.mastervpn.feature.main.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.alishi.mastervpn.core.extension.drawCircleProgressBar
import ir.alishi.mastervpn.core.extension.drawThumb
import ir.alishi.mastervpn.feature.main.domain.model.OpenVpnStatus
import ir.alishi.mastervpn.ui.theme.MasterVpnTheme

@ExperimentalMaterialApi
@Composable
fun VpnConnectButton(
    vpnState: OpenVpnStatus,
    progress : Int,
    progressColor : Color,
    onPressed: () -> Unit,
    outStrokeWidth: Float = 8f,
    thumbStrokeWidth: Float = 6f
) {

    val animateProgress by animateIntAsState(
        targetValue = progress,
        animationSpec = tween(1200)
    )

    val animateColor by animateColorAsState(
        targetValue = progressColor,
        animationSpec = tween(1500)
    )

    MasterVpnTheme {
        Surface {
            Column(
                Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    elevation = 3.dp,
                    onClick = {
                        onPressed()
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.background(Color.White),
                    ) {

                        Canvas(
                            Modifier
                                .size(50.dp)
                                .padding(10.dp)
                        ) {
                            drawThumb(
                                color = animateColor,
                                thumbStrokeWidth = thumbStrokeWidth
                            )
                        }

                        Canvas(
                            Modifier
                                .size(120.dp)
                                .padding(10.dp)
                        ) {
                            drawCircleProgressBar(
                                color = animateColor,
                                progressStrokeWidth = outStrokeWidth,
                                sweepAngle = animateProgress.toFloat()
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = vpnState.status,
                    style = MaterialTheme.typography.overline.copy(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }

}

