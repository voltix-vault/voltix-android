package com.voltix.wallet.presenter.base_components.tabbars.common

import com.voltix.wallet.presenter.navigation.Screen

sealed class UiEvent {
    data object PopBackStack : UiEvent()
    data class ScrollToNextPage(val screen: Screen)  : UiEvent()
    data class NavigateTo(val screen: Screen) : UiEvent()
}