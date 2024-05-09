package com.vultisig.wallet.presenter.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen(route = "welcome_screen")
    data object Home : Screen(route = "home_screen")

    data object CreateNewVault : Screen(route = "create_new_vault")
    data object JoinKeygen : Screen(route = "join_keygen")
    data object ImportFile : Screen(route = "import_file/{has_file}")
    data object Setup : Screen(route = "setup")
    data object KeygenFlow : Screen(route = "keygen_flow")
    data object SigningError : Screen(route = "signing_error")
    data object VaultDetail: Screen(route = "vault_detail/{vault_name}"){
        fun createRoute(vaultName: String): String {
            return "vault_detail/$vaultName"
        }
    }

}