package com.voltix.wallet.presenter.list_of_vault_and_details_list

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.presenter.base_components.tabbars.animated_tabbar.AnimatedTopBar
import com.voltix.wallet.presenter.list_of_vault_and_details_list.VaultListAndDetailsEvent.UpdateMainScreen
import com.voltix.wallet.presenter.list_of_vault_and_details_list.components.VaultDetailsList
import com.voltix.wallet.presenter.list_of_vault_and_details_list.components.VaultList

@Composable
fun VaultListAndDetailsList(navController: NavHostController) {
    val context = LocalContext.current

    val viewModel = hiltViewModel<VaultListAndDetailsViewModel>()
    val state = viewModel.state

    val isMainListVisible = state.isMainListVisible
    val selectedItem = state.selectedItem

    BackHandler {
        viewModel.onEvent(UpdateMainScreen(true))
    }


    Column(
        modifier = Modifier
            .background(MaterialTheme.appColor.oxfordBlue800)
            .padding(MaterialTheme.dimens.marginMedium)
    ) {

        AnimatedTopBar(
            navController = navController,
            centerText = state.centerText.asString(context),
            startIcon = R.drawable.hamburger_menu,
            endIcon = R.drawable.clockwise,
            isBottomLayerVisible = isMainListVisible,
            hasCenterArrow = true,
            onCenterTextClick = {
                viewModel.onEvent(UpdateMainScreen(true))
            },
            onBackClick = {
                viewModel.onEvent(UpdateMainScreen(true))
            }
        )

        Box {

            VaultList(navController = navController)

            this@Column.AnimatedVisibility(
                visible = isMainListVisible.not(),
                enter = slideInVertically() + fadeIn()
            ) {
                VaultDetailsList(navController = navController,selectedItem)
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun VaultListAndDetailsListPreview() {
    val navController = rememberNavController()
    VaultListAndDetailsList( navController)
}