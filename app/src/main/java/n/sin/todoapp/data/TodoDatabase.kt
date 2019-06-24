package n.sin.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItem::class], version = 1)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun itemDao(): TodoItemDao

    companion object {
        private var instance: TodoDataBase? = null

        @JvmStatic
        fun getInstance(context: Context): TodoDataBase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDataBase::class.java, "items.db")
                        .build()
                }
                return instance!!
            }
        }

        @JvmStatic
        fun destroyInstance() {
            instance = null
        }

    }
}