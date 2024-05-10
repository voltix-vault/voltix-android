package com.vultisig.wallet.presenter.keygen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.navigation.NavHostController
import com.vultisig.wallet.R
import com.vultisig.wallet.common.Utils
import com.vultisig.wallet.models.TssAction
import com.vultisig.wallet.models.Vault
import com.vultisig.wallet.presenter.common.QRCodeKeyGenImage
import com.vultisig.wallet.presenter.keygen.components.DeviceInfo
import com.vultisig.wallet.ui.components.MultiColorButton
import com.vultisig.wallet.ui.components.TopBar
import com.vultisig.wallet.ui.theme.appColor
import com.vultisig.wallet.ui.theme.dimens
import com.vultisig.wallet.ui.theme.menloFamily
import com.vultisig.wallet.ui.theme.montserratFamily

@Composable
fun KeygenPeerDiscovery(
    navController: NavHostController,
    vault: Vault,
    viewModel: KeygenFlowViewModel,
) {
    val selectionState = viewModel.selection.asFlow().collectAsState(initial = emptyList()).value
    val participants = viewModel.participants.asFlow().collectAsState(initial = emptyList()).value
    val keygenPayload = viewModel.keygenPayloadState
    val context = LocalContext.current.applicationContext
    LaunchedEffect(Unit) {
        // start mediator server
        viewModel.setData(TssAction.KEYGEN, vault, context)
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopParticipantDiscovery()
        }
    }
    val textColor = MaterialTheme.appColor.neutral0
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.appColor.oxfordBlue800)
            .padding(
                vertical = MaterialTheme.dimens.marginMedium,
                horizontal = MaterialTheme.dimens.marginSmall
            )
    ) {
        TopBar(
            centerText = "Keygen", startIcon = R.drawable.caret_left,
            navController = navController
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium2))
        if (!selectionState.isNullOrEmpty() && selectionState.count() > 1) {
            Text(
                text = "${Utils.getThreshold(selectionState.count())} of ${selectionState.count()} Vault",
                color = textColor,
                style = MaterialTheme.montserratFamily.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Text(
            text = "Pair with other devices:",
            color = textColor,
            style = MaterialTheme.montserratFamily.bodyMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        if (viewModel.keygenPayloadState.value.isNotEmpty()) {
            QRCodeKeyGenImage(viewModel.keygenPayloadState.value, (LocalConfiguration.current.screenWidthDp/2).toInt().dp,(LocalConfiguration.current.screenWidthDp/2).toInt().dp)
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        NetworkPrompts(networkPromptOption = viewModel.networkOption.value) {
            viewModel.changeNetworkPromptOption(it, context)
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
                    DeviceInfo(R.drawable.ipad, participant, isSelected = isSelected) { isChecked ->
                        when (isChecked) {
                            true -> viewModel.addParticipant(participant)
                            false -> viewModel.removeParticipant(participant)
                        }

                    }
                }
            }
        } else {
            Text(
                text = "Waiting for other devices to connect...",
                color = textColor,
                style = MaterialTheme.montserratFamily.bodyMedium
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Image(painter = painterResource(id = R.drawable.wifi), contentDescription = null)
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.marginSmall))
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.marginExtraLarge),
            text = "Keep all devices on same WiFi with vultisig App open. (May not work on hotel/airport WiFi)",
            color = textColor,
            style = MaterialTheme.menloFamily.headlineSmall.copy(
                textAlign = TextAlign.Center, fontSize = 13.sp
            ),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        MultiColorButton(
            text = "Continue",
            backgroundColor = MaterialTheme.appColor.turquoise600Main,
            textColor = MaterialTheme.appColor.oxfordBlue600Main,
            minHeight = MaterialTheme.dimens.minHeightButton,
            textStyle = MaterialTheme.montserratFamily.titleLarge,
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
            viewModel.moveToState(KeygenFlowState.DEVICE_CONFIRMATION)
        }
    }
}

@Composable
fun NetworkPrompts(
    networkPromptOption: NetworkPromptOption = NetworkPromptOption.WIFI,
    onChange: (NetworkPromptOption) -> Unit = {},
) {
    Row {
        FilterChip(selected = networkPromptOption == NetworkPromptOption.WIFI, onClick = {
            onChange(NetworkPromptOption.WIFI)
        }, label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.wifi),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.marginSmall))
                Text(text = "Wifi")
            }
        })

        Spacer(modifier = Modifier.width(MaterialTheme.dimens.marginSmall))

        FilterChip(
            selected = networkPromptOption == NetworkPromptOption.HOTSPOT,
            onClick = { onChange(NetworkPromptOption.HOTSPOT) },
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_wifi_tethering_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.marginSmall))
                    Text(text = "Hotspot")
                }
            })

        Spacer(modifier = Modifier.width(MaterialTheme.dimens.marginSmall))

        FilterChip(
            selected = networkPromptOption == NetworkPromptOption.CELLULAR,
            onClick = { onChange(NetworkPromptOption.CELLULAR) },
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_signal_cellular_alt_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.marginSmall))
                    Text(text = "Cellular")
                }
            })
    }
}

@Preview
@Composable
fun PreviewNetworkPrompts() {
    NetworkPrompts()
}