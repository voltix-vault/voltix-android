package com.vultisig.wallet.presenter.keysign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import com.vultisig.wallet.R
import com.vultisig.wallet.models.Vault
import com.vultisig.wallet.presenter.common.QRCodeKeyGenImage
import com.vultisig.wallet.presenter.keygen.NetworkPrompts
import com.vultisig.wallet.presenter.keygen.components.DeviceInfo
import com.vultisig.wallet.ui.components.MultiColorButton
import com.vultisig.wallet.ui.components.TopBar
import com.vultisig.wallet.ui.theme.Theme
import com.vultisig.wallet.ui.theme.dimens

@Composable
internal fun KeysignPeerDiscovery(
    navController: NavController,
    vault: Vault,
    keysignPayload: KeysignPayload,
    viewModel: KeysignFlowViewModel,
) {
    val selectionState = viewModel.selection.asFlow().collectAsState(initial = emptyList()).value
    val participants = viewModel.participants.asFlow().collectAsState(initial = emptyList()).value
    val context = LocalContext.current.applicationContext
    LaunchedEffect(Unit) {
        // start mediator server
        viewModel.setData(vault, context, keysignPayload)
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopParticipantDiscovery()
        }
    }

    val textColor = Theme.colors.neutral0
    Scaffold(
        topBar = {
            TopBar(
                centerText = stringResource(id = R.string.keysign),
                startIcon = R.drawable.caret_left,
                navController = navController
            )
        },
        bottomBar = {
            MultiColorButton(
                text = stringResource(R.string.keysign_peer_discovery_start),
                backgroundColor = Theme.colors.turquoise600Main,
                textColor = Theme.colors.oxfordBlue600Main,
                minHeight = MaterialTheme.dimens.minHeightButton,
                textStyle = Theme.montserrat.titleLarge,
                disabled = selectionState.size < 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.marginMedium,
                        end = MaterialTheme.dimens.marginMedium,
                        bottom = MaterialTheme.dimens.buttonMargin,
                    )
            ) {
                viewModel.stopParticipantDiscovery()
                viewModel.moveToState(KeysignFlowState.KEYSIGN)
            }
        }
    ) { it ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Theme.colors.oxfordBlue800)
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Text(
                text = stringResource(R.string.keysign_peer_discovery_pair_with_other_devices),
                color = textColor,
                style = Theme.montserrat.bodyMedium
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            if (viewModel.keysignMessage.value.isNotEmpty()) {
                QRCodeKeyGenImage(viewModel.keysignMessage.value)
            }
            NetworkPrompts(networkPromptOption = viewModel.networkOption.value) { networOption ->
                viewModel.changeNetworkPromptOption(networOption, context)
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            if (!participants.isNullOrEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(participants.size) { index ->
                        val participant = participants[index]
                        val isSelected = selectionState.contains(participant)
                        DeviceInfo(
                            R.drawable.ipad,
                            participant,
                            isSelected = isSelected
                        ) { isChecked ->
                            when (isChecked) {
                                true -> viewModel.addParticipant(participant)
                                false -> viewModel.removeParticipant(participant)
                            }

                        }
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.keysign_peer_discovery_waiting_for_other_devices_to_connect),
                    color = textColor,
                    style = Theme.montserrat.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1.0f))

            Image(painter = painterResource(id = R.drawable.wifi), contentDescription = null)
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginSmall))
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.marginExtraLarge),
                text = stringResource(R.string.keysign_peer_discovery_desc1),
                color = textColor,
                style = Theme.menlo.headlineSmall.copy(
                    textAlign = TextAlign.Center, fontSize = 13.sp
                ),
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        }
    }
}
