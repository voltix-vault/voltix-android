package com.voltix.wallet.presenter.list_of_vault_and_details_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.voltix.wallet.presenter.base_components.tabbars.common.UiText.*
import com.voltix.wallet.presenter.list_of_vault_and_details_list.VaultListAndDetailsEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VaultListAndDetailsViewModel @Inject constructor(
) : ViewModel() {

    var state by mutableStateOf(VaultListAndDetailsState())
        private set

    init {
        onEvent(UpdateMainScreen(true))
    }

    fun onEvent(event: VaultListAndDetailsEvent) {
        when (event) {
            is OnItemClick -> updateData(event.value)
            is UpdateMainScreen -> updateScreen(event.isVisible)
        }
    }

    private fun updateScreen(visible: Boolean) {
        state = state.copy(
            isMainListVisible = visible,
            centerText = if (visible) DynamicString("Vaults") else DynamicString(state.selectedItem)
        )
    }

    private fun updateData(value: String) {
        state = state.copy(selectedItem = value)
    }


}