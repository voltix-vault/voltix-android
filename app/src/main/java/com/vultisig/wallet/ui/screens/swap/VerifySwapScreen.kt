package com.vultisig.wallet.ui.screens.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vultisig.wallet.R
import com.vultisig.wallet.ui.components.MultiColorButton
import com.vultisig.wallet.ui.components.library.form.FormCard
import com.vultisig.wallet.ui.models.swap.VerifySwapViewModel
import com.vultisig.wallet.ui.screens.send.AddressField
import com.vultisig.wallet.ui.screens.send.CheckField
import com.vultisig.wallet.ui.screens.send.OtherField
import com.vultisig.wallet.ui.theme.Theme

@Composable
internal fun VerifySwapScreen(
    viewModel: VerifySwapViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    VerifySwapScreen(
        srcTokenValue = state.srcTokenValue,
        dstTokenValue = state.dstTokenValue,
        estimatedFees = state.estimatedFees,
        estimatedTime = state.estimatedTime,
        consentAmount = state.consentAmount,
        consentReceiveAmount = state.consentReceiveAmount,
        confirmTitle = "Sign",
        onConsentReceiveAmount = viewModel::consentReceiveAmount,
        onConsentAmount = viewModel::consentAmount,
        onConfirm = viewModel::confirm,
    )
}


@Composable
private fun VerifySwapScreen(
    srcTokenValue: String,
    dstTokenValue: String,
    estimatedFees: String,
    estimatedTime: String,
    consentAmount: Boolean,
    consentReceiveAmount: Boolean,
    confirmTitle: String,
    isConsentsEnabled: Boolean = true,
    onConsentReceiveAmount: (Boolean) -> Unit,
    onConsentAmount: (Boolean) -> Unit,
    onConfirm: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Theme.colors.oxfordBlue800)
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(all = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            FormCard {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        )
                ) {
                    AddressField(
                        title = stringResource(R.string.verify_transaction_from_title),
                        address = srcTokenValue,
                    )

                    AddressField(
                        title = stringResource(R.string.verify_transaction_to_title),
                        address = dstTokenValue
                    )

                    OtherField(
                        title = stringResource(R.string.verify_swap_screen_estimated_fees),
                        value = estimatedFees,
                    )

                    OtherField(
                        title = stringResource(R.string.verify_swap_screen_estimated_time),
                        value = estimatedTime,
                        divider = false,
                    )
                }
            }

            if (isConsentsEnabled) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    CheckField(
                        title = stringResource(R.string.verify_swap_consent_amount),
                        isChecked = consentAmount,
                        onCheckedChange = onConsentAmount,
                    )

                    CheckField(
                        title = stringResource(R.string.verify_swap_agree_receive_amount),
                        isChecked = consentReceiveAmount,
                        onCheckedChange = onConsentReceiveAmount,
                    )
                }
            }
        }

        MultiColorButton(
            text = confirmTitle,
            textColor = Theme.colors.oxfordBlue800,
            minHeight = 44.dp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(all = 16.dp),
            onClick = onConfirm,
        )
    }
}

@Preview
@Composable
private fun VerifySwapScreenPreview() {
    VerifySwapScreen(
        srcTokenValue = "1 RUNE",
        dstTokenValue = "1 ETH",
        estimatedFees = "1.00$",
        estimatedTime = "Instant",
        consentAmount = true,
        consentReceiveAmount = false,
        confirmTitle = "Sign",
        onConsentReceiveAmount = {},
        onConsentAmount = {},
        onConfirm = {},
    )
}