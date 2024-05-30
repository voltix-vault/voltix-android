package com.vultisig.wallet.presenter.import_file

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vultisig.wallet.R
import com.vultisig.wallet.common.UiText
import com.vultisig.wallet.common.asString
import com.vultisig.wallet.common.decodeFromHex
import com.vultisig.wallet.common.fileContent
import com.vultisig.wallet.common.fileName
import com.vultisig.wallet.data.mappers.VaultIOSToAndroidMapper
import com.vultisig.wallet.data.repositories.VaultRepository
import com.vultisig.wallet.models.IOSVaultRoot
import com.vultisig.wallet.presenter.import_file.ImportFileEvent.FileSelected
import com.vultisig.wallet.presenter.import_file.ImportFileEvent.OnContinueClick
import com.vultisig.wallet.presenter.import_file.ImportFileEvent.RemoveSelectedFile
import com.vultisig.wallet.ui.navigation.Destination
import com.vultisig.wallet.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
internal class ImportFileViewModel @Inject constructor(
    private val vaultRepository: VaultRepository,
    private val vaultIOSToAndroidMapper: VaultIOSToAndroidMapper,
    private val gson: Gson,
    private val navigator: Navigator<Destination>,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val uiModel = MutableStateFlow(ImportFileState())

    private val snackBarChannel = Channel<UiText?>()
    val snackBarChannelFlow = snackBarChannel.receiveAsFlow()
    fun onEvent(event: ImportFileEvent) {
        when (event) {
            is FileSelected -> fetchFileName(event.uri)
            OnContinueClick -> saveFileToAppDir()
            RemoveSelectedFile -> removeSelectedFile()
        }
    }

    private fun insertContentToDb(fileContent: String?) {
        if (fileContent == null)
            return
        viewModelScope.launch {
            try {
                val fromJson = gson.fromJson(fileContent.decodeFromHex(), IOSVaultRoot::class.java)
                val vault = vaultIOSToAndroidMapper(fromJson)
                vaultRepository.add(vault)
                navigator.navigate(
                    Destination.Home(
                        openVaultId = vault.id,
                    )
                )
                } catch (e: SQLiteConstraintException) {
                    snackBarChannel.send(UiText.StringResource(R.string.import_file_screen_duplicate_vault))
            }

        }
    }


    private fun removeSelectedFile() {
        uiModel.update {
            it.copy(fileUri = null, fileName = null, fileContent = null)
        }
    }

    private fun saveFileToAppDir() {
        val uri = uiModel.value.fileUri ?: return
        val fileContent = uri.fileContent(context)
        insertContentToDb(fileContent)
    }

    private fun fetchFileName(uri: Uri?) {
        uiModel.update {
            it.copy(fileUri = uri, fileName = null, fileContent = null)
        }
        if (uri == null)
            return
        val fileName = uri.fileName(context)
        uiModel.update {
            it.copy(fileName = fileName)
        }
    }
}