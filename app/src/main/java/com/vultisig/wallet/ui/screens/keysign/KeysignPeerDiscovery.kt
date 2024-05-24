package com.vultisig.wallet.ui.screens.keysign

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vultisig.wallet.R
import com.vultisig.wallet.common.Utils
import com.vultisig.wallet.models.Vault
import com.vultisig.wallet.presenter.keygen.NetworkPromptOption
import com.vultisig.wallet.presenter.keysign.KeysignFlowState
import com.vultisig.wallet.presenter.keysign.KeysignFlowViewModel
import com.vultisig.wallet.presenter.keysign.KeysignPayload
import com.vultisig.wallet.ui.components.MultiColorButton
import com.vultisig.wallet.ui.screens.PeerDiscoveryView
import com.vultisig.wallet.ui.theme.Theme
import com.vultisig.wallet.ui.theme.dimens
import timber.log.Timber

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
    LaunchedEffect(key1 = viewModel.participants) {
        viewModel.participants.asFlow().collect { newList ->
            // add all participants to the selection
            for (participant in newList) {
                viewModel.addParticipant(participant)
            }
        }
    }
    LaunchedEffect(key1 = viewModel.selection) {
        viewModel.selection.asFlow().collect { newList ->
            if (vault.signers.isEmpty()) {
                Timber.e("Vault signers size is 0")
                return@collect
            }
            if (newList.size >= Utils.getThreshold(vault.signers.size)) {
                // automatically kickoff keysign
                viewModel.moveToState(KeysignFlowState.KEYSIGN)
            }
        }
    }
    LaunchedEffect(Unit) {
        // start mediator server
        viewModel.setData(vault, context, keysignPayload)
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopParticipantDiscovery()
        }
    }

    KeysignPeerDiscovery(navController = navController,
        selectionState = selectionState,
        participants = participants,
        keysignMessage = viewModel.keysignMessage.value,
        networkPromptOption = viewModel.networkOption.value,
        onChangeNetwork = { viewModel.changeNetworkPromptOption(it, context) },
        onAddParticipant = { viewModel.addParticipant(it) },
        onRemoveParticipant = { viewModel.removeParticipant(it) },
        onStopParticipantDiscovery = {
            viewModel.stopParticipantDiscovery()
            viewModel.moveToState(KeysignFlowState.KEYSIGN)
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun KeysignPeerDiscovery(
    navController: NavController,
    selectionState: List<String>,
    participants: List<String>,
    keysignMessage: String,
    networkPromptOption: NetworkPromptOption,
    onChangeNetwork: (NetworkPromptOption) -> Unit = {},
    onAddParticipant: (String) -> Unit = {},
    onRemoveParticipant: (String) -> Unit = {},
    onStopParticipantDiscovery: () -> Unit = {},
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.keysign),
                    style = Theme.montserrat.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = Theme.colors.neutral0,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Theme.colors.oxfordBlue800,
                titleContentColor = Theme.colors.neutral0,
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.caret_left),
                        contentDescription = "back",
                        tint = Theme.colors.neutral0
                    )
                }
            },
        )
    }, bottomBar = {
        MultiColorButton(
            text = stringResource(R.string.keysign_peer_discovery_start),
            backgroundColor = Theme.colors.turquoise600Main,
            textColor = Theme.colors.oxfordBlue600Main,
            minHeight = MaterialTheme.dimens.minHeightButton,
            textStyle = Theme.montserrat.subtitle1,
            disabled = selectionState.size < 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.dimens.marginMedium,
                    end = MaterialTheme.dimens.marginMedium,
                    bottom = MaterialTheme.dimens.buttonMargin,
                ),
            onClick = onStopParticipantDiscovery,
        )
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Theme.colors.oxfordBlue800)
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            PeerDiscoveryView(
                selectionState = selectionState,
                participants = participants,
                keygenPayloadState = keysignMessage,
                networkPromptOption = networkPromptOption,
                onChangeNetwork = onChangeNetwork,
                onAddParticipant = onAddParticipant,
                onRemoveParticipant = onRemoveParticipant,
            )
        }
    }
}

@Preview
@Composable
private fun KeysignPeerDiscoveryPreview() {
    KeysignPeerDiscovery(navController = rememberNavController(),
        selectionState = listOf("1", "2"),
        participants = listOf("1", "2", "3"),
        keysignMessage = "keysignMessage",
        networkPromptOption = NetworkPromptOption.WIFI,
        onChangeNetwork = {},
        onAddParticipant = {},
        onRemoveParticipant = {},
        onStopParticipantDiscovery = {})
}