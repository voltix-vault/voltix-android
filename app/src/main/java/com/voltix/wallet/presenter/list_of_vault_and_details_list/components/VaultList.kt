package com.voltix.wallet.presenter.list_of_vault_and_details_list.components


import MultiColorButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.app.ui.theme.montserratFamily
import com.voltix.wallet.presenter.base_components.lists.VaultListItem

class Item(val value: String,  ) {
    val title = value
}
@Composable
fun VaultList(navController: NavHostController) {
    val vaultListItems =listOf(
        Item("Main Vault",),
        Item("Savings Vault",),
        Item("Ethereum Vault",)
    )

    val textColor = MaterialTheme.colorScheme.onBackground
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.appColor.oxfordBlue800)
//            .padding(MaterialTheme.dimens.marginMedium)
    ) {


        LazyColumn(
//            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
        ) {
            itemsIndexed(vaultListItems) { index, item ->
                VaultListItem(item.value)
            }
        }

        Column(
            modifier = Modifier
                .align(BottomCenter),
            horizontalAlignment = CenterHorizontally
        ) {

            MultiColorButton(
                text = stringResource(id = R.string.add_new_vault),
                minHeight = MaterialTheme.dimens.minHeightButton,
                backgroundColor = MaterialTheme.appColor.turquoise800,
                textColor = MaterialTheme.appColor.oxfordBlue800,
                startIconColor = MaterialTheme.appColor.oxfordBlue800,
                startIcon = R.drawable.plus,
                textStyle = MaterialTheme.montserratFamily.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.buttonMargin,
                        end = MaterialTheme.dimens.buttonMargin
                    )
            ) {}
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginSmall))
            MultiColorButton(
                text = stringResource(id = R.string.join_airdrop),
                backgroundColor = MaterialTheme.appColor.oxfordBlue800,
                textColor = MaterialTheme.appColor.turquoise800,
                startIconColor = MaterialTheme.appColor.oxfordBlue800,
                borderSize = 1.dp,
                textStyle = MaterialTheme.montserratFamily.titleLarge,
                minHeight = MaterialTheme.dimens.minHeightButton,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.buttonMargin,
                        end = MaterialTheme.dimens.buttonMargin
                    )

            ) {}
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimens.marginMedium)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun VaultListPreview() {
    val navController = rememberNavController()
    VaultList( navController)
}