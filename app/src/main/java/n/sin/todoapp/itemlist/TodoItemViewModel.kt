package n.sin.todoapp.itemlist

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import n.sin.todoapp.data.StorageService
import n.sin.todoapp.data.TodoItem

class TodoItemViewModel(private val storage: StorageService, item: TodoItem) : ViewModel() {

    private val _item = item
    val item: TodoItem = _item

    private val _text = MutableLiveData<String>().apply { value = item.text }
    val text: LiveData<String>
        get() = _text

    var active = MutableLiveData<Boolean>().apply { value = item.active }

    fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        item.active = !isChecked
        storage.update(item)
    }

    class Factory(private val storage: StorageService, private val item: TodoItem): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TodoItemViewModel(storage, item) as T
        }
    }

}

interface TodoItemEventListener {
    fun onClickItem(item: TodoItem)
}