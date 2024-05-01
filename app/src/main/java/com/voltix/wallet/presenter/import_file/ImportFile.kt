package com.voltix.wallet.presenter.import_file

import MultiColorButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.voltix.wallet.presenter.navigation.Screen

@Composable
fun ImportFile(navController: NavHostController, hasFile: Boolean) {
    val textColor = MaterialTheme.appColor.neutral0

    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.appColor.oxfordBlue800)
            .padding(MaterialTheme.dimens.marginMedium)
    ) {
        TopBar(centerText = "Import",navController = navController)
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Enter your previously created vault share",
            color = textColor,
            style = MaterialTheme.menloFamily.bodyMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginSmall))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(MaterialTheme.dimens.small2))
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
            .clickable {
                navController.navigate(Screen.ImportFile.route.replace(oldValue = "{has_file}", newValue = true.toString()))
            }
            .drawBehind {
                drawRoundRect(
                    color = Color("#33e6bf".toColorInt()), style = Stroke(
                        width = 8f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f), 0f)
                    ), cornerRadius = CornerRadius(16.dp.toPx())
                )
            }

        ) {
            Column(Modifier.align(Center), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painterResource(id = R.drawable.file), contentDescription = "file"
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
                Text(
                    text = "Upload file, text or image",
                    color = textColor,
                    style = MaterialTheme.menloFamily.bodySmall
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
        if (hasFile)
            LazyColumn(modifier = Modifier.padding(horizontal = MaterialTheme.dimens.marginSmall)) {
                item {
                    Row(verticalAlignment = CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.file),
                            contentDescription = "file icon",
                            modifier = Modifier.width(MaterialTheme.dimens.medium1)
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
                        Text(
                            text = "voltix-vault-share-jun2024.txt",
                            color = textColor,
                            style = MaterialTheme.menloFamily.bodyMedium
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        Icon(
                            painter = painterResource(id = R.drawable.x),
                            contentDescription = "X",
                            tint = textColor
                        )

                    }
                }
            }

        Spacer(modifier = Modifier.weight(1.0F))

        MultiColorButton(
            text = stringResource(id = R.string.continue_res),
            backgroundColor = MaterialTheme.appColor.turquoise800,
            textStyle = MaterialTheme.montserratFamily.titleLarge,
            minHeight = MaterialTheme.dimens.minHeightButton,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.dimens.buttonMargin,
                    end = MaterialTheme.dimens.buttonMargin
                )
        ) {
            navController.navigate(Screen.Setup.route)
        }
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.dimens.marginMedium)
        )

    }
}
@Preview(showBackground = true)
@Composable
fun ImportFilePreview() {
    val navController = rememberNavController()
    ImportFile(navController,true)

}