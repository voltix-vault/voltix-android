package com.voltix.wallet.presenter.base_components.lists

import MiddleEllipsisText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.app.ui.theme.menloFamily
import com.voltix.wallet.app.ui.theme.montserratFamily

@Composable
fun VaultDetailsListItem(
    cryptoIcon: Int = R.drawable.crypto_bitcoin,
    cryptoName: String = "Bitcoin",
    assets: String = "3 assets",
    isAssets: Boolean = false,
    value: String = "\$65,899",
    code: String = "0xF42b6DE07e40cb1D4a24292bB89862f599Ac5"
) {
    val textColor = MaterialTheme.appColor.neutral0
    val backOfAsset = MaterialTheme.appColor.oxfordBlue400
    val transparentAsset = MaterialTheme.appColor.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.appColor.oxfordBlue600Main)
            .padding(MaterialTheme.dimens.marginMedium)
    ) {
        Image(
            painter = painterResource(cryptoIcon),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(MaterialTheme.dimens.circularListIcon)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.marginSmall))
        Column {
            Row {
                Text(
                    text = cryptoName,
                    color = textColor,
                    style = MaterialTheme.montserratFamily.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.weight(1.0f))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = when {
                                    isAssets -> backOfAsset
                                    else -> transparentAsset
                                },
                                cornerRadius = CornerRadius(25.0f),
                            )
                        },
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.marginSmall),
                        text = assets,
                        color = textColor,
                        style = MaterialTheme.menloFamily.bodyLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = value,
                    color = textColor,
                    style = MaterialTheme.menloFamily.titleLarge,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginMedium))
            MiddleEllipsisText(text = code, maxWidth = 90)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VaultDetailsListItemPreview() {
    val navController = rememberNavController()
    VaultDetailsListItem()

}