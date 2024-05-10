package com.vultisig.wallet.presenter.vault_setting.vault_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vultisig.wallet.data.on_board.db.VaultDB
import com.vultisig.wallet.models.Vault
import com.vultisig.wallet.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VaultDetailViewmodel @Inject constructor(
    vaultDB: VaultDB,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val vaultId: String =
        savedStateHandle.get<String>(Screen.VaultDetail.VaultSettings.ARG_VAULT_ID)!!
    val vault: Vault? = vaultDB.select(vaultId)

    val uiModel = MutableStateFlow(VaultDetailUiModel())
    fun loadData() {
        vault?.let {
            uiModel.update {
                it.copy(
                    name = vault.name,
                    pubKeyECDSA = vault.pubKeyECDSA,
                    pubKeyEDDSA = vault.pubKeyEDDSA,
                    deviceList = listOf("SM-G990E-243","iPad-04C (this device)")
                )
            }
        }
    }
}