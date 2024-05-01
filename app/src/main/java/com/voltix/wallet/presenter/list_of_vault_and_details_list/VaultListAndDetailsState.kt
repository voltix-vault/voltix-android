package com.voltix.wallet.presenter.list_of_vault_and_details_list

import com.voltix.wallet.presenter.base_components.tabbars.common.UiText
import com.voltix.wallet.presenter.base_components.tabbars.common.UiText.*


data class VaultListAndDetailsState(
    val selectedItem:String = "",
    val isMainListVisible:Boolean = true,
    val centerText: UiText = DynamicString("")
)