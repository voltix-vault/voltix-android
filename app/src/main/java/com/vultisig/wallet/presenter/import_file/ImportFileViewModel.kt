package com.vultisig.wallet.presenter.import_file

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vultisig.wallet.common.fileContent
import com.vultisig.wallet.common.fileName
import com.vultisig.wallet.data.on_board.db.VaultDB
import com.vultisig.wallet.models.Vault
import com.vultisig.wallet.presenter.import_file.ImportFileEvent.FileSelected
import com.vultisig.wallet.presenter.import_file.ImportFileEvent.OnContinueClick
import com.vultisig.wallet.presenter.import_file.ImportFileEvent.RemoveSelectedFile
import com.vultisig.wallet.ui.navigation.Destination
import com.vultisig.wallet.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class ImportFileViewModel @Inject constructor(
    private val vaultDB: VaultDB,
    private val gson: Gson,
    private val navigator: Navigator<Destination>,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val uiModel = MutableStateFlow(ImportFileState())

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
        val fromJson = gson.fromJson(fileContent, Vault::class.java)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vaultDB.upsert(fromJson)
            }
            navigator.navigate(Destination.Home)
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