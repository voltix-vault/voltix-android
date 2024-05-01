package com.voltix.wallet.presenter.list_of_vault_and_details_list

sealed class VaultListAndDetailsEvent {
    data class OnItemClick(val value: String) : VaultListAndDetailsEvent()
    data class UpdateMainScreen(val isVisible: Boolean) : VaultListAndDetailsEvent()
}