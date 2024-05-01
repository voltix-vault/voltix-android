package com.voltix.wallet.presenter.base_components.lists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.app.ui.theme.montserratFamily

@Composable
fun CreateItem(
    value: String = "Choose Chains",
    startIcon: Int? = null,
) {
    val textColor = MaterialTheme.appColor.turquoise600Main
    val startIconColor = MaterialTheme.appColor.turquoise600Main
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(MaterialTheme.dimens.marginMedium)
    ) {

        Image(
            painter = painterResource(startIcon ?: R.drawable.plus),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(MaterialTheme.dimens.pencilListIcon),
            colorFilter = ColorFilter.tint(color =  startIconColor)
        )
        Spacer(Modifier.width(MaterialTheme.dimens.marginSmall))
        Text(
            text = value,
            color = textColor,
            style = MaterialTheme.montserratFamily.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateItemPreview() {
    CreateItem("Choose Chains")
}