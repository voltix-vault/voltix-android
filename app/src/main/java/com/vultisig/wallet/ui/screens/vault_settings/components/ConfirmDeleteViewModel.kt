package com.vultisig.wallet.ui.screens.vault_settings.components

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vultisig.wallet.R
import com.vultisig.wallet.data.db.models.VaultOrderEntity
import com.vultisig.wallet.data.repositories.OrderRepository
import com.vultisig.wallet.data.repositories.VaultRepository
import com.vultisig.wallet.ui.navigation.Destination
import com.vultisig.wallet.ui.navigation.NavigationOptions
import com.vultisig.wallet.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal data class ConfirmDeleteVaultState(
    val checkedCautionIndexes: List<Int> = emptyList(),
    val cautionsBeforeDelete: List<Int> = emptyList(),
    val isDeleteButtonEnabled: Boolean = false,
    val vaultDeleteUiModel: VaultDeleteUiModel = VaultDeleteUiModel()
)

@HiltViewModel
internal class ConfirmDeleteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val vaultRepository: VaultRepository,
    private val navigator: Navigator<Destination>,
    private val vaultOrderRepository: OrderRepository<VaultOrderEntity>,
) : ViewModel() {

    private val vaultId: String =
        savedStateHandle.get<String>(Destination.ConfirmDelete.ARG_VAULT_ID)!!

    val uiModel = MutableStateFlow(
        ConfirmDeleteVaultState(
            cautionsBeforeDelete = listOf(
                R.string.vault_settings_delete_vault_caution1,
                R.string.vault_settings_delete_vault_caution2,
                R.string.vault_settings_delete_vault_caution3,
            ),
            vaultDeleteUiModel = VaultDeleteUiModel()
        )
    )

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            vaultRepository.get(vaultId)?.let { vault ->
                uiModel.update {
                    it.copy(
                        vaultDeleteUiModel = VaultDeleteUiModel(
                            name = vault.name,
                            pubKeyECDSA = vault.pubKeyECDSA,
                            pubKeyEDDSA = vault.pubKeyEDDSA,
                            deviceList = vault.signers
                        )
                    )
                }
            }
        }
    }


    fun changeCheckCaution(index: Int, checked: Boolean) {
        val checkedCautionIndexes = uiModel.value.checkedCautionIndexes.toMutableList()
        if (checked) checkedCautionIndexes.add(index)
        else checkedCautionIndexes.remove(index)
        uiModel.update {
            it.copy(
                checkedCautionIndexes = checkedCautionIndexes,
                isDeleteButtonEnabled = checkedCautionIndexes.size == it.cautionsBeforeDelete.size
            )
        }
    }

    fun delete() {
        viewModelScope.launch {
            vaultRepository.delete(vaultId)
            vaultOrderRepository.delete(parentId = null, name = vaultId)
            if (vaultRepository.hasVaults()) {
                navigator.navigate(
                    Destination.Home(),
                    NavigationOptions(
                        clearBackStack = true
                    )
                )
            } else {
                navigator.navigate(
                    Destination.AddVault, NavigationOptions(
                        clearBackStack = true
                    )
                )
            }
        }
    }
}