package com.vultisig.wallet.ui.screens.keysign

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vultisig.wallet.R
import com.vultisig.wallet.presenter.common.KeepScreenOn
import com.vultisig.wallet.presenter.keysign.KeysignState
import com.vultisig.wallet.presenter.keysign.KeysignViewModel
import com.vultisig.wallet.ui.components.UiBarContainer
import com.vultisig.wallet.ui.components.UiSpacer
import com.vultisig.wallet.ui.components.library.UiCirclesLoader
import com.vultisig.wallet.ui.navigation.Screen
import com.vultisig.wallet.ui.screens.TransactionDoneView
import com.vultisig.wallet.ui.theme.Theme
import com.vultisig.wallet.ui.theme.dimens

@Composable
internal fun Keysign(
    navController: NavController,
    viewModel: KeysignViewModel,
) {
    LaunchedEffect(key1 = Unit) {
        // kick it off to generate key
        viewModel.startKeysign()
    }

    UiBarContainer(
        navController = navController,
        title = stringResource(id = R.string.keysign)
    ) {
        KeysignScreen(
            state = viewModel.currentState.collectAsState().value,
            errorMessage = viewModel.errorMessage.value,
            txHash = viewModel.txHash.collectAsState().value,
            transactionLink = viewModel.txLink.collectAsState().value,
            onComplete = {
                navController.navigate(Screen.VaultDetail.createRoute(viewModel.vault.name))
            },
        )
    }
}

@Composable
internal fun KeysignScreen(
    state: KeysignState,
    txHash: String,
    transactionLink: String,
    errorMessage: String,
    onComplete: () -> Unit,
) {
    KeepScreenOn()

    val textColor = Theme.colors.neutral0

    val text = when (state) {
        KeysignState.CreatingInstance -> stringResource(id = R.string.keysign_screen_preparing_vault)
        KeysignState.KeysignECDSA -> stringResource(id = R.string.keysign_screen_signing_with_ecdsa)
        KeysignState.KeysignEdDSA -> stringResource(id = R.string.vault_detail_screen_eddsa)
        KeysignState.KeysignFinished -> stringResource(id = R.string.keysign_screen_keysign_finished)
        KeysignState.ERROR -> stringResource(
            id = R.string.keysign_screen_error_please_try_again,
            errorMessage
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state == KeysignState.KeysignFinished) {
            TransactionDoneView(
                transactionHash = txHash,
                transactionLink = transactionLink,
                onComplete = onComplete,
            )
        } else {
            UiSpacer(weight = 1f)
            Text(
                text = text,
                color = Theme.colors.neutral0,
                style = Theme.menlo.subtitle1
            )

            UiSpacer(size = 32.dp)

            UiCirclesLoader()

            UiSpacer(weight = 1f)

            Icon(
                painter = painterResource(id = R.drawable.wifi),
                contentDescription = null,
                tint = Theme.colors.turquoise600Main
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.large),
                text = stringResource(id = R.string.keysign_screen_keep_devices_on_the_same_wifi_network_with_vultisig_open),
                color = textColor,
                style = Theme.menlo.body1,
                textAlign = TextAlign.Center,
            )

            UiSpacer(size = 80.dp)
        }
    }
}

@Preview
@Composable
private fun KeysignPreview() {
    KeysignScreen(
        state = KeysignState.CreatingInstance,
        errorMessage = "Error",
        txHash = "0x1234567890",
        transactionLink = "",
        onComplete = {},
    )
}
