package n.sin.todoapp.itemdetail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import n.sin.todoapp.Event
import n.sin.todoapp.data.StorageCallback
import n.sin.todoapp.data.StorageRepository
import n.sin.todoapp.data.TodoItem

class TodoDetailViewModel(private val storage: StorageRepository, private val id: Int): ViewModel(), StorageCallback.ItemReadCallback {

    var active: Boolean = false

    var text = MutableLiveData<String>()

    private val _updatedItem = MutableLiveData<Event<Int>>()
    val updatedItem: LiveData<Event<Int>>
        get() = _updatedItem

    private val _deletedItem = MutableLiveData<Event<Int>>()
    val deletedItem: LiveData<Event<Int>>
        get() = _deletedItem


    init {
        storage.read(id, this)
    }

    override fun onRead(item: TodoItem?) {
        item?.let {
            active = it.active
            text.value = it.text
        }
    }

    fun updateItem() {
        var str = ""
        text.value?.let { str = it }
        val item = TodoItem(id, str, active)
        storage.update(item)
        _updatedItem.value = Event(id)
    }

    fun deleteItem() {
        val item = TodoItem(id, "", true)
        storage.delete(item)
        _deletedItem.value = Event(id)
    }

    private fun log(msg: String) {
        Log.d("Detail", msg)
    }

    class Factory(private val storage: StorageRepository, private val itemId: Int): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TodoDetailViewModel(storage, itemId) as T
        }
    }
}
