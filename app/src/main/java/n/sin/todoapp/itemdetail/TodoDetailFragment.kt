package n.sin.todoapp.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import n.sin.todoapp.MainActivity
import n.sin.todoapp.R
import n.sin.todoapp.data.StorageRepository
import n.sin.todoapp.databinding.ItemDetailBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

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

        // Back Button
        val act = activity as AppCompatActivity
        act.setSupportActionBar(_binding.toolbar)
        val actionBar = act.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        _binding.toolbar.setNavigationOnClickListener { act.onBackPressed() }

        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _viewModel = getViewModel{ parametersOf(arguments!!.getInt(FRAGMENT_ID_KEY)) } // DI by Koin

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
