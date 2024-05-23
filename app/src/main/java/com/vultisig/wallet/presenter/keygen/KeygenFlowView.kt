package com.vultisig.wallet.presenter.keygen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vultisig.wallet.R

@Composable
fun KeygenFlowView(navController: NavHostController) {
    val viewModel: KeygenFlowViewModel = hiltViewModel()
    when (viewModel.currentState.value) {
        KeygenFlowState.PEER_DISCOVERY -> {
            KeygenPeerDiscovery(navController, viewModel)
        }

        KeygenFlowState.DEVICE_CONFIRMATION -> {
            DeviceList(navController, viewModel)
        }

        KeygenFlowState.KEYGEN -> {
            GeneratingKey(navController, viewModel.generatingKeyViewModel)
        }

        KeygenFlowState.ERROR -> {
            KeyGenErrorScreen(navController)
        }

        KeygenFlowState.SUCCESS -> {
            Text(text = stringResource(R.string.keygen_flow_views_success))
        }
    }
}