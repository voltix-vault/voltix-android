package com.voltix.wallet.presenter.base_components.lists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.app.ui.theme.montserratFamily
import com.voltix.wallet.presenter.list_of_vault_and_details_list.VaultListAndDetailsEvent
import com.voltix.wallet.presenter.list_of_vault_and_details_list.VaultListAndDetailsViewModel

@Composable
fun VaultListItem(
    value: String = "Main Vault"
) {
    val textColor = MaterialTheme.appColor.neutral0
    val backOfRow = MaterialTheme.appColor.oxfordBlue600Main
    val viewModel = hiltViewModel<VaultListAndDetailsViewModel>()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                viewModel.onEvent(VaultListAndDetailsEvent.OnItemClick(value))
                viewModel.onEvent(VaultListAndDetailsEvent.UpdateMainScreen(false))
            }
            .background(MaterialTheme.appColor.oxfordBlue600Main)
            .padding(MaterialTheme.dimens.marginMedium)
    ) {

        Text(
            text = value,
            color = textColor,
            style = MaterialTheme.montserratFamily.titleLarge
        )
        Spacer(Modifier.weight(1.0f))


        Image(
            painter = painterResource(R.drawable.pencil),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(MaterialTheme.dimens.pencilListIcon)
        )
        Spacer(Modifier.width(MaterialTheme.dimens.marginSmall))
        Image(
            painter = painterResource(R.drawable.caret_right),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(MaterialTheme.dimens.pencilListIcon),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VaultListItemPreview() {
    VaultListItem()

}