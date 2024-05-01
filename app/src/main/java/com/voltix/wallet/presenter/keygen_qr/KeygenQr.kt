package com.voltix.wallet.presenter.keygen_qr

import MultiColorButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.app.ui.theme.menloFamily
import com.voltix.wallet.app.ui.theme.montserratFamily
import com.voltix.wallet.presenter.common.TopBar
import com.voltix.wallet.presenter.keygen_qr.components.DeviceInfo
import com.voltix.wallet.presenter.navigation.Screen


@Composable
fun KeygenQr(navController: NavHostController) {
    val textColor = MaterialTheme.appColor.neutral0
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.appColor.oxfordBlue800)
            .padding(MaterialTheme.dimens.marginMedium)
    ) {
        TopBar(
            centerText = "Keygen", startIcon = R.drawable.caret_left,
            navController = navController
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
        Text(
            text = "2 of 3 Vault",
            color = textColor,
            style = MaterialTheme.montserratFamily.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        Text(
            text = "Pair with other devices:",
            color = textColor,
            style = MaterialTheme.montserratFamily.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1.0f))

        Image(painter = painterResource(id = R.drawable.qr),
            contentScale = ContentScale.FillWidth,
            contentDescription = "devices",
            modifier = Modifier
                .width(MaterialTheme.dimens.qrWidthAndHeight)
                .height(MaterialTheme.dimens.qrWidthAndHeight)
//                .weight(4.0f)
                .drawBehind {
                    drawRoundRect(
                        color = Color("#33e6bf".toColorInt()), style = Stroke(
                            width = 8f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(50f, 50f), 0.0f)
                        ), cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
                .padding(20.dp))

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginMedium))

        val configuration = LocalConfiguration.current
        val width =  configuration.screenWidthDp
        val isTablet = width > 600

        if (isTablet)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ConnectionType("WiFi", R.drawable.wifi, true)
                ConnectionType("Hotspot", R.drawable.hotspot)
                ConnectionType("Cellular", R.drawable.cellular)
            }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginSmall))

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            DeviceInfo(R.drawable.ipad, "iPad", "1234h2i34h")
            DeviceInfo(R.drawable.iphone, "iPhone", "623654ghdsg")
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Image(painter = painterResource(id = R.drawable.wifi), contentDescription = null)
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginSmall))
        Text(
            modifier = Modifier.fillMaxWidth(0.70f),
            text = stringResource(id = R.string.keygen_qr),
            color = textColor,
            style = MaterialTheme.menloFamily.titleLarge.copy(
                textAlign = TextAlign.Center,
//                lineHeight = 10.sp
            )
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginMedium))
//        Button(onClick = {
//            navController.navigate(
//                Screen.DeviceList.route.replace(
//                    oldValue = "{count}", newValue = "2"
//                )
//            )
//        }, modifier = Modifier.fillMaxWidth().padding(MaterialTheme.dimens.marginMedium)) {
//            Text(text = "Start")
//        }
        MultiColorButton(
            text = stringResource(R.string.continue_res),
            backgroundColor = MaterialTheme.appColor.turquoise600Main,
            textColor = MaterialTheme.appColor.oxfordBlue600Main,
            minHeight = MaterialTheme.dimens.minHeightButton,
            textStyle = MaterialTheme.montserratFamily.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.dimens.buttonMargin,
                    end = MaterialTheme.dimens.buttonMargin
                )
        ) {
            navController.navigate(
                Screen.DeviceList.route.replace(
                    oldValue = "{count}", newValue = "2"
                )
            )
        }
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.dimens.marginMedium)
        )
    }
}

@Composable
private fun ConnectionType(type: String, icon: Int, isSelected: Boolean = false) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) MaterialTheme.appColor.oxfordBlue200 else MaterialTheme.appColor.oxfordBlue400)
            .padding(
                vertical = MaterialTheme.dimens.marginExtraSmall.div(4),
                horizontal = MaterialTheme.dimens.marginExtraSmall
            ),
        verticalAlignment = CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.appColor.turquoise600Main,
            modifier = Modifier.size(MaterialTheme.dimens.medium1)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.marginExtraSmall))
        Text(
            text = type,
            color = MaterialTheme.appColor.neutral0,
            style = MaterialTheme.montserratFamily.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeygenQrPreview() {
    val navController = rememberNavController()
    KeygenQr(navController)

}