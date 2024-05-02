package com.voltix.wallet.presenter.tokens.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.app.ui.theme.montserratFamily
import com.voltix.wallet.models.Coin
import com.voltix.wallet.models.Coins

@Composable
fun TokenSelectionItem(
    token: Coin
) {
    var checked by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.dimens.small1),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.appColor.oxfordBlue600Main
        )
    ) {
        Row(Modifier.padding(12.dp)) {
            Image(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    )
                    .size(32.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.qr),
                contentDescription = stringResource(R.string.token_logo),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(
                    start = 12.dp
                )
            ) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = token.ticker,
                    color = MaterialTheme.appColor.neutral100,
                    style = MaterialTheme.montserratFamily.titleLarge,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = token.priceProviderID,
                    color = MaterialTheme.appColor.neutral100,
                    style = MaterialTheme.montserratFamily.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        end = 12.dp
                    ),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.appColor.neutral0,
                    checkedBorderColor = MaterialTheme.appColor.turquoise800,
                    checkedTrackColor = MaterialTheme.appColor.turquoise800,
                    uncheckedThumbColor = MaterialTheme.appColor.neutral0,
                    uncheckedBorderColor = MaterialTheme.appColor.oxfordBlue400,
                    uncheckedTrackColor = MaterialTheme.appColor.oxfordBlue400
                ),
                checked = checked,
                onCheckedChange = { isChecked ->
                    checked = isChecked
                },
            )
        }
    }
}

@Preview
@Composable
fun TokenSelectionItemPreview() {
    TokenSelectionItem(
        token = Coins.SupportedCoins[0]
    )
}