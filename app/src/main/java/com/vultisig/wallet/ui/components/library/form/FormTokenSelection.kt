package com.vultisig.wallet.ui.components.library.form

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vultisig.wallet.R
import com.vultisig.wallet.models.Chain
import com.vultisig.wallet.models.Coin
import com.vultisig.wallet.ui.components.UiHorizontalDivider
import com.vultisig.wallet.ui.models.send.TokenBalanceUiModel
import com.vultisig.wallet.ui.utils.tokenBalanceUiModels

@Composable
internal fun FormTokenSelection(
    selectedToken: TokenBalanceUiModel?,
    availableTokens: List<TokenBalanceUiModel>,
    onSelectToken: (TokenBalanceUiModel) -> Unit,
) {
    var isTokenListExpanded by remember { mutableStateOf(false) }

    FormTokenCard(
        selectedTitle = selectedToken?.title ?: "",
        availableToken = selectedToken?.balance ?: "",
        selectedIcon = selectedToken?.tokenLogo
            ?: R.drawable.ethereum,
        isExpanded = isTokenListExpanded,
        chainLogo = selectedToken?.chainLogo,
        onClick = { isTokenListExpanded = !isTokenListExpanded },
    ) {
        availableTokens.forEach { token ->
            UiHorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            )

            TokenCard(
                title = token.title,
                tokenLogo = token.tokenLogo,
                chainLogo = token.chainLogo.takeIf { !token.isNativeToken || token.isLayer2 },
                tokenStandard = token.tokenStandard.takeIf { !token.isNativeToken || token.isLayer2 },
                actionIcon = if (token == selectedToken)
                    R.drawable.check
                else null,
                onClick = {
                    isTokenListExpanded = false
                    onSelectToken(token)
                }
            )
        }
    }
}

@Preview
@Composable
private fun FormTokenSelectionPreview() {
    val m = tokenBalanceUiModels
    FormTokenSelection(
        selectedToken = m[0],
        availableTokens = m,
        onSelectToken = {}
    )
}