package n.sin.todoapp.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

class StorageService(private val context: Context) {

    private val _bgThreadHandler = Executors.newSingleThreadExecutor()
    private val _mainThreadHandler = Handler(Looper.getMainLooper())

    private val _dao = TodoDataBase.getInstance(context).itemDao()

    fun read(callback: StorageCallback.ItemsReadCallback) {

        _bgThreadHandler.execute {
            val items = _dao.readItems()
            _mainThreadHandler.post {
                callback.onRead(items)
            }
        }
    }

    fun read(id: Int, callback: StorageCallback.ItemReadCallback) {
        _bgThreadHandler.execute {
            val item = _dao.readItem(id)
            _mainThreadHandler.post {
                callback.onRead(item)
            }
        }
    }

    fun read(active: Boolean, callback: StorageCallback.ItemsReadCallback) {
        _bgThreadHandler.execute {
            val items = _dao.readItems(active)
            _mainThreadHandler.post {
                callback.onRead(items)
            }
        }
    }

    fun create(item: TodoItem) {
        _bgThreadHandler.execute {
            _dao.createItem(item)
        }
    }

    fun update(item: TodoItem) {
        _bgThreadHandler.execute {
            _dao.updateItem(item)
        }
    }

    fun delete(item: TodoItem) {
        _bgThreadHandler.execute {
            _dao.deleteItem(item)
        }
    }

    companion object {

        private var instance: StorageService? = null

        @JvmStatic fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance
                ?: StorageService(context).also { instance = it }
        }

        @JvmStatic fun destroyInstance() {
            instance = null
        }
    }
}

interface StorageCallback {

    interface ItemsReadCallback {
        fun onRead(items: List<TodoItem>?)

    }

    interface ItemReadCallback {
        fun onRead(item: TodoItem?)
    }


}