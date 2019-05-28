package n.sin.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TodoItem::class), version = 1)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun itemDao(): TodoItemDao

    companion object {
        private var _instance: TodoDataBase? = null

        @JvmStatic
        fun getInstance(context: Context): TodoDataBase {
            synchronized(this) {
                if (_instance == null) {
                    _instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDataBase::class.java, "items.db")
                        .build()
                }
                return _instance!!
            }
        }

        @JvmStatic
        fun destroyInstance() {
            _instance = null
        }

    }
}