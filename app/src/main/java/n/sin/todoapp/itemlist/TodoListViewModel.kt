package n.sin.todoapp.itemlist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import n.sin.todoapp.R
import n.sin.todoapp.data.StorageCallback
import n.sin.todoapp.data.StorageRepository
import n.sin.todoapp.data.TodoItem

class TodoListViewModel(private val storage: StorageRepository): ViewModel(), StorageCallback.ItemsReadCallback {

    private val _items = MutableLiveData<List<TodoItem>>()
    val items: LiveData<List<TodoItem>>
        get() = _items

    var enabled = MutableLiveData<Boolean>().apply { value = false }

    var inputText = MutableLiveData<String>().apply { value = "" }

    val itemFilter = MutableLiveData<Int>().apply { value = R.id.allRadio }

    init {
        storage.read(this)
    }

    override fun onRead(items: List<TodoItem>?) {
        _items.value = items
    }

    fun onClickAddButton(view: View) {
        val str = inputText.value
        if (str != null) {
            inputText.value = ""
            saveTodo(str)
        }
    }

    fun saveTodo(str: String) {
        val item = TodoItem(0, str, true)
        storage.create(item)
        storage.read(this)
    }

    fun reload() {
        when(itemFilter.value) {
            R.id.allRadio -> {
                storage.read(this)
            }
            R.id.activeRadio -> {
                storage.read(true, this)
            }
            R.id.compRadio -> {
                storage.read(false, this)
            }
        }
    }

}