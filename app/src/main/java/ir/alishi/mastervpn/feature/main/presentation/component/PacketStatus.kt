package ir.alishi.mastervpn.feature.main.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PacketCapture(
    duration: String = "00:00:00",
    lastPacketReceive: String = "0",
    download: String = "0 kb",
    upload: String = "0 kb"
) {

    Card(
        modifier = Modifier.padding(10.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(0xE980CBC4)

    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Duration : $duration",
                style = txtStyle(FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Last Packet Received : $lastPacketReceive",
                style = txtStyle(FontWeight.Light)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Download : $download",
                style = txtStyle(FontWeight.Bold).copy(
                    textAlign = TextAlign.Start
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Upload : $upload",
                style = txtStyle(FontWeight.Bold).copy(
                    textAlign = TextAlign.Start
                )
            )
        }

    }
}

private fun txtStyle(weight: FontWeight) = TextStyle(
    textAlign = TextAlign.Start,
    textDecoration = TextDecoration.None,
    textDirection = TextDirection.Ltr,
    fontFamily = FontFamily.SansSerif,
    fontWeight = weight
)

