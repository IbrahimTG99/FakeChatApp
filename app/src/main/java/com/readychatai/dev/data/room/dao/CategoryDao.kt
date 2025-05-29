package com.readychatai.dev.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.readychatai.dev.data.room.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(category: Category): Long

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM Category")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT * FROM Category")
    fun getAllCategoriesFlow(): Flow<List<Category>>
}
