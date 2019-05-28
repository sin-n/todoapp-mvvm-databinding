package n.sin.todoapp.itemlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import n.sin.todoapp.data.StorageService
import n.sin.todoapp.data.TodoItem
import n.sin.todoapp.databinding.ItemBinding

class TodoListAdapter(eventListener: TodoItemEventListener, private val fragment: Fragment): RecyclerView.Adapter<TodoListAdapter.ItemViewHolder>() {

    private var _todoList: List<TodoItem> = emptyList()

    private val _eventListener = eventListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.eventListener = _eventListener

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(createItemViewModel(_todoList[position]))
    }

    private fun createItemViewModel(item: TodoItem): TodoItemViewModel {
        val storage = StorageService.getInstance(fragment.context!!)
        val viewModel = TodoItemViewModel(storage, item) // 要検討
        return viewModel
    }

    fun log(msg: String) {
        Log.d("TodoListAdapter", msg)
    }

    override fun getItemCount(): Int {
        return _todoList.size
    }

    fun update(todoList: List<TodoItem>) {
        log("update")
        if (_todoList.isEmpty()) {
            this._todoList = todoList
            notifyDataSetChanged()
        }else {
            // 差分計算
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return _todoList.size
                }

                override fun getNewListSize(): Int {
                    return todoList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = todoList[newItemPosition]
                    val oldItem = _todoList[oldItemPosition]
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = todoList[newItemPosition]
                    val oldItem = _todoList[oldItemPosition]
                    return oldItem.text == newItem.text && oldItem.active == newItem.active
                }
            })
            _todoList = todoList
            result.dispatchUpdatesTo(this)
        }
    }

    class ItemViewHolder (binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var binding: ItemBinding = binding

        fun bind(viewModel: TodoItemViewModel) {
            binding.viewModel = viewModel
        }
    }
}