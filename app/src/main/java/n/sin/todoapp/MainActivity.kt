package n.sin.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import n.sin.todoapp.itemdetail.TodoDetailFragment
import n.sin.todoapp.itemlist.TodoListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = TodoListFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment, TodoListFragment.TAG)
                .commit()
        }
    }

    fun show(todoId: Int) {
        val fragment = TodoDetailFragment.create(todoId)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, null)
            .addToBackStack(null)
            .commit()
    }

    fun back() {
        supportFragmentManager.popBackStack()
    }}
