package com.vultisig.wallet.data.db

import androidx.room.Dao
import androidx.room.Query
import com.vultisig.wallet.data.db.models.ChainOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class ChainOrderDao : BaseOrderDao<ChainOrderEntity>("chainOrder") {
    @Query("SELECT * FROM chainOrder ORDER BY `order` DESC")
    abstract override fun loadOrders(): Flow<List<ChainOrderEntity>>
}