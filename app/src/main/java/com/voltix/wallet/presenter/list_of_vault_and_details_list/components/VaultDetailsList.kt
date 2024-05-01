package com.voltix.wallet.presenter.list_of_vault_and_details_list.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.presenter.base_components.lists.CreateItem
import com.voltix.wallet.presenter.base_components.lists.VaultDetailsListItem

class DetailsItem(  cryptoIcon: Int, cryptoName: String,assets: String ,isAssets: Boolean ,value: String ,code: String ) {
    val cryptoIcon=cryptoIcon
    val cryptoName=cryptoName
    val assets=assets
    val isAsset=isAssets
    val value=value
    val code=code
}
class CreateItemData(  value: String,   ) {
    val value=value
}
@Composable
fun VaultDetailsList(navController: NavHostController,selectedItem:String) {
    val vaultDetailsListItems =listOf(
        DetailsItem( cryptoIcon = R.drawable.crypto_bitcoin,
            cryptoName = "Bitcoin", assets = "1.1", isAssets = false,
            value = "\$65,899", code = "bc1psrjtwm7682v...6nhx2uwfgcfelrennd",),
        DetailsItem( cryptoIcon = R.drawable.crypto_ethereum,
            cryptoName = "Ethereum", assets = "3 assets", isAssets = true,
            value = "\$65,899", code = "0x0cb1D4a24292...bB89862f599Ac5B10F4",),
        DetailsItem( cryptoIcon = R.drawable.crypto_solana,
            cryptoName = "Solana", assets = "2 assets", isAssets = true,
            value = "\$65,899", code = "ELPecyZbKieSzNUn...AGPZma6q7r8DYa7",),
        DetailsItem( cryptoIcon = R.drawable.crypto_thorchain,
            cryptoName = "THORChain", assets = "12,000.12", isAssets = false,
            value = "\$65,899", code = "thor1cfelrennd7...pcvqq7v6w7682v6nhx",),
        CreateItemData(  "Choose Chains"  )
    )

    val textColor = MaterialTheme.colorScheme.onBackground
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.appColor.oxfordBlue800)
            .padding(MaterialTheme.dimens.marginMedium)
    ) {


        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(vaultDetailsListItems) { index, item ->
                if(index<vaultDetailsListItems.count()-1)
                {
                    VaultDetailsListItem(
                        cryptoIcon = (item as DetailsItem).cryptoIcon,
                        cryptoName = (item as DetailsItem).cryptoName,
                        assets = (item as DetailsItem).assets,
                        isAssets =  (item as DetailsItem).isAsset,
                        value = (item as DetailsItem).value,
                        code = (item as DetailsItem).code,
                    )
                }else
                {
                    CreateItem(value = (item as CreateItemData).value)
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(MaterialTheme.dimens.small3)
                .align(BottomCenter)
                .size(MaterialTheme.dimens.circularMedium1)
                .clip(CircleShape)
                .background(color = MaterialTheme.appColor.turquoise600Main),
            contentAlignment = Alignment.Center
        ) {
            val painter = painterResource(R.drawable.camera)

            Image(
                painter = painterResource(R.drawable.camera),
                contentDescription = "camera",
                contentScale = ContentScale.Fit,

                modifier = Modifier
                    .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height)
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small1),
                        colorFilter = ColorFilter.tint(color =  MaterialTheme.appColor.oxfordBlue800)


            )
        }

    }
}
@Preview(showBackground = true)
@Composable
fun VaultDetailsListPreview() {
    val navController = rememberNavController()
    VaultDetailsList( navController,"" )
}