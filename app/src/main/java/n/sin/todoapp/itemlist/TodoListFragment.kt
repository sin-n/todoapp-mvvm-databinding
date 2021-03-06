package n.sin.todoapp.itemlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import n.sin.todoapp.MainActivity
import n.sin.todoapp.R
import n.sin.todoapp.data.StorageRepository
import n.sin.todoapp.data.TodoItem
import n.sin.todoapp.databinding.ItemListBinding
import androidx.recyclerview.widget.DividerItemDecoration
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel


class TodoListFragment: Fragment(), TodoItemEventListener {

    companion object {
        const val TAG = "TodoListFragment"
    }

    private lateinit var _viewModel: TodoListViewModel
    private lateinit var _binding: ItemListBinding
    private lateinit var _adapter: TodoListAdapter
    private val _storage by inject<StorageRepository>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setObserve(_viewModel)
    }

    private fun log(msg: String) {
        Log.d(TAG, msg)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_list,
                container,
                false)

        _viewModel = getViewModel() // DI by Koin

        _binding.viewModel = _viewModel
        _adapter = TodoListAdapter(this, _storage)
        _binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        _binding.recyclerView.adapter = _adapter
        _binding.lifecycleOwner = this

        // 区切り線の追加
        addDivider(_binding.recyclerView)

        return _binding.root
    }

    private fun addDivider(recyclerView: RecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun setObserve(viewModel: TodoListViewModel) {
        viewModel.items.observe(this, Observer<List<TodoItem>> {
            log("items observe")
            if (it != null) {
                _adapter.update(it)
            }
        })
        viewModel.inputText.observe(this, Observer<String> {
            log("observe text : $it")
            var flag = false
            if (it.isNotBlank()) {
                flag = true
            }
            viewModel.enabled.postValue(flag)
            log("enabled: $flag")
        })
        viewModel.itemFilter.observe(this, Observer<Int> {
            viewModel.reload()
        })
    }

    override fun onClickItem(item: TodoItem) {
        (activity as MainActivity).show(item.id)
    }
}