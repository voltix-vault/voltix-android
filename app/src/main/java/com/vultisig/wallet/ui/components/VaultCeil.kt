package com.vultisig.wallet.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vultisig.wallet.R
import com.vultisig.wallet.models.Vault
import com.vultisig.wallet.ui.theme.Theme

@Composable
internal fun VaultCeil(
    vault: Vault,
    onSelectVault: (vaultId: String) -> Unit,
) {
    FormCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 14.dp)
                .clickable(onClick = { onSelectVault(vault.id) }),
        ) {
            Text(
                text = vault.name,
                style = Theme.menlo.body1,
                color = Theme.colors.neutral0,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f),
            )

            UiIcon(
                R.drawable.caret_right,
                size = 20.dp
            )
        }
    }
}

@Preview
@Composable
private fun VaultCeilPreview() {
    VaultCeil(
        vault = Vault(
            name = "Vault 1",
        ),
        onSelectVault = {}
    )
}