package com.vultisig.wallet.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vultisig.wallet.R
import com.vultisig.wallet.data.models.Address
import com.vultisig.wallet.models.Chain
import com.vultisig.wallet.ui.components.BoxWithSwipeRefresh
import com.vultisig.wallet.ui.components.ChainAccountItem
import com.vultisig.wallet.ui.components.UiIcon
import com.vultisig.wallet.ui.components.UiPlusButton
import com.vultisig.wallet.ui.components.UiSpacer
import com.vultisig.wallet.ui.components.VaultActionButton
import com.vultisig.wallet.ui.components.library.UiPlaceholderLoader
import com.vultisig.wallet.ui.components.reorderable.VerticalReorderList
import com.vultisig.wallet.ui.models.AccountUiModel
import com.vultisig.wallet.ui.models.VaultAccountsUiModel
import com.vultisig.wallet.ui.models.VaultAccountsViewModel
import com.vultisig.wallet.ui.navigation.Screen
import com.vultisig.wallet.ui.theme.Theme

@Composable
internal fun VaultAccountsScreen(
    vaultId: String,
    navHostController: NavHostController,
    viewModel: VaultAccountsViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState().value

    LaunchedEffect(vaultId) {
        viewModel.loadData(vaultId)
    }

    VaultAccountsScreen(
        state = state,
        onRefresh = viewModel::refreshData,
        onSend = viewModel::send,
        onSwap = viewModel::swap,
        onJoinKeysign = {
            navHostController.navigate(
                Screen.JoinKeysign.createRoute(vaultId)
            )
        },
        onAccountClick = viewModel::openAccount,
        onChooseChains = {
            navHostController.navigate(
                Screen.AddChainAccount.createRoute(vaultId)
            )
        },
        onMove = viewModel::onMove,
    )
}

@Composable
private fun VaultAccountsScreen(
    state: VaultAccountsUiModel,
    modifier: Modifier = Modifier,
    onSend: () -> Unit = {},
    onSwap: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onJoinKeysign: () -> Unit = {},
    onAccountClick: (AccountUiModel) -> Unit = {},
    onChooseChains: () -> Unit = {},
    onMove: (Int, Int) -> Unit = { _, _ -> },
) {
    BoxWithSwipeRefresh(
        onSwipe = DebouncedClickable {onRefresh },
        isRefreshing = state.isRefreshing,
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            VerticalReorderList(
                data = state.accounts,
                onMove = onMove,
                key = { it.chainName },
                contentPadding = PaddingValues(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                beforeContents = listOf {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 22.dp,
                                end = 22.dp,
                                top = 0.dp,
                                bottom = 16.dp
                            )
                    ) {
                        AnimatedContent(
                            targetState = state.totalFiatValue,
                            label = "ChainAccount FiatAmount",
                        ) { totalFiatValue ->
                            if (totalFiatValue != null) {
                                Text(
                                    text = totalFiatValue,
                                    style = Theme.menlo.heading4
                                        .copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 32.sp,
                                        ),
                                    color = Theme.colors.neutral100,
                                    textAlign = TextAlign.Center,
                                )
                            } else {
                                UiPlaceholderLoader(
                                    modifier = Modifier
                                        .width(48.dp)
                                        .height(32.dp),
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            VaultActionButton(
                                text = stringResource(R.string.chain_account_view_send),
                                color = Theme.colors.turquoise600Main,
                                modifier = Modifier.weight(1f),
                                onClick = DebouncedClickable {onSend },
                            )

                            VaultActionButton(
                                text = stringResource(R.string.chain_account_view_swap),
                                color = Theme.colors.persianBlue200,
                                modifier = Modifier.weight(1f),
                                onClick = DebouncedClickable {onSwap },
                            )
                        }
                    }
                },
                afterContents = listOf {
                    UiSpacer(
                        size = 16.dp,
                    )
                    UiPlusButton(
                        title = stringResource(R.string.vault_choose_chains),
                        onClick = DebouncedClickable {onChooseChains },
                    )
                    UiSpacer(
                        size = 64.dp,
                    )
                }
            ) { account ->
                ChainAccountItem(
                    account = account,
                    onClick = {
                        onAccountClick(account)
                    },
                )
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                UiIcon(
                    drawableResId = R.drawable.camera,
                    size = 40.dp,
                    contentDescription = "join keysign",
                    tint = Theme.colors.oxfordBlue600Main,
                    onClick = DebouncedClickable {onJoinKeysign },
                    modifier = Modifier
                        .background(
                            color = Theme.colors.turquoise600Main,
                            shape = CircleShape,
                        )
                        .padding(all = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun VaultAccountsScreenPreview() {
    VaultAccountsScreen(
        state = VaultAccountsUiModel(
            vaultName = "Vault Name",
            totalFiatValue = "$1000",
            accounts = listOf(
                AccountUiModel(
                    chainName = "Ethereum",
                    logo = R.drawable.ethereum,
                    address = "0x1234567890",
                    nativeTokenAmount = "1.0",
                    fiatAmount = "$1000",
                    assetsSize = 4,
                    model = Address(
                        chain = Chain.ethereum,
                        address = "0x123456",
                        accounts = emptyList()
                    )
                ),
                AccountUiModel(
                    chainName = "Bitcoin",
                    logo = R.drawable.bitcoin,
                    address = "123456789abcdef",
                    nativeTokenAmount = "1.0",
                    fiatAmount = "$1000",
                    model = Address(
                        chain = Chain.bitcoin,
                        address = "0x123456",
                        accounts = emptyList()
                    )
                ),
            ),
        ),
    )
}

@Composable
fun DebouncedClickable(
    debounceTime: Long = 300L, // Set debounce time to 300 milliseconds
    onClick: () -> Unit
): () -> Unit {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    return {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= debounceTime) {
            lastClickTime = currentTime
            onClick()
        }
    }
}