package n.sin.todoapp.data

import androidx.room.*

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM items")
    fun readItems(): List<TodoItem>

    @Query("SELECT * FROM items WHERE active = :active")
    fun readItems(active: Boolean): List<TodoItem>

    @Query("SELECT * FROM items WHERE id = :id")
    fun readItem(id: Int): TodoItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createItem(vararg item: TodoItem)

    @Update
    fun updateItem(vararg item: TodoItem)

    @Delete
    fun deleteItem(vararg item: TodoItem)

}