package com.vultisig.wallet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vultisig.wallet.data.db.models.CoinEntity
import com.vultisig.wallet.data.db.models.ChainOrderEntity
import com.vultisig.wallet.data.db.models.KeyShareEntity
import com.vultisig.wallet.data.db.models.SignerEntity
import com.vultisig.wallet.data.db.models.VaultEntity
import com.vultisig.wallet.data.db.models.VaultOrderEntity

@Database(
    entities = [
        VaultEntity::class,
        KeyShareEntity::class,
        SignerEntity::class,
        CoinEntity::class,
        ChainOrderEntity::class,
        VaultOrderEntity::class,
    ],
    version = 3,
    exportSchema = false,
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun vaultDao(): VaultDao
    abstract fun chainOrderDao(): ChainOrderDao
    abstract fun vaultOrderDao(): VaultOrderDao

}