package com.vultisig.wallet.ui.screens.vault_settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vultisig.wallet.data.on_board.db.VaultDB
import com.vultisig.wallet.data.on_board.db.VaultDB.Companion.FILE_POSTFIX
import com.vultisig.wallet.models.Vault
import com.vultisig.wallet.ui.navigation.Destination
import com.vultisig.wallet.ui.navigation.Navigator
import com.vultisig.wallet.ui.screens.vault_settings.VaultSettingsEvent.Backup
import com.vultisig.wallet.ui.screens.vault_settings.VaultSettingsEvent.Delete
import com.vultisig.wallet.ui.screens.vault_settings.VaultSettingsEvent.ErrorDownloadFile
import com.vultisig.wallet.ui.screens.vault_settings.VaultSettingsEvent.SuccessBackup
import com.vultisig.wallet.ui.screens.vault_settings.VaultSettingsUiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal open class VaultSettingsViewModel @Inject constructor(
    private val vaultDB: VaultDB,
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator<Destination>,
) : ViewModel() {

    private val vaultId: String = savedStateHandle.get<String>(Destination.VaultSettings.ARG_VAULT_ID)!!
    val vault: Vault? = vaultDB.select(vaultId)

    private val channel = Channel<VaultSettingsUiEvent>()
    val channelFlow = channel.receiveAsFlow()
    fun onEvent(event: VaultSettingsEvent) {
        when (event) {
            Backup -> backupVault()
            Delete -> deleteVault()
            ErrorDownloadFile -> errorBackUp()
            is SuccessBackup -> successBackup(event.fileName)
        }
    }

    private fun successBackup(fileName: String) {
        viewModelScope.launch {
            channel.send(BackupSuccess(fileName + FILE_POSTFIX))
        }
    }


    private fun errorBackUp() {
        viewModelScope.launch {
            channel.send(BackupFailed)
        }
    }


    private fun backupVault() {
        viewModelScope.launch {
            vault?.let {
                channel.send(BackupFile(it.name))
            }
        }
    }

    private fun deleteVault() {
        viewModelScope.launch {
            vaultDB.delete(vaultId)
            navigator.navigate(Destination.Home)
        }
    }


}