package n.sin.todoapp.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import n.sin.todoapp.MainActivity
import n.sin.todoapp.R
import n.sin.todoapp.data.StorageService
import n.sin.todoapp.databinding.ItemDetailBinding

class TodoDetailFragment: Fragment() {

    private lateinit var _viewModel: TodoDetailViewModel
    private lateinit var _binding: ItemDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_detail,
            container,
            false)
        _binding.lifecycleOwner = this

        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = arguments!!.getInt(FRAGMENT_ID_KEY)
        val storage = StorageService.getInstance(context!!)
        val factory = TodoDetailViewModel.Factory(storage, id)
        _viewModel = ViewModelProviders.of(this, factory).get(TodoDetailViewModel::class.java)
        _binding.viewModel = _viewModel

        setObserve(_viewModel)
    }

    private fun setObserve(viewModel: TodoDetailViewModel) {
        viewModel.updatedItem.observe(this, Observer {
            (activity as MainActivity).back()
        })
        viewModel.deletedItem.observe(this, Observer {
            (activity as MainActivity).back()
        })
    }

    companion object {
        val FRAGMENT_ID_KEY = "todo_id"

        fun create(id: Int): TodoDetailFragment {
            var fragment = TodoDetailFragment()
            val bundle = Bundle()
            bundle.putInt(FRAGMENT_ID_KEY, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
