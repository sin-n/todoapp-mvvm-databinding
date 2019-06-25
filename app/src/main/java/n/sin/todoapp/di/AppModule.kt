package n.sin.todoapp.di

import n.sin.todoapp.data.StorageRepository
import n.sin.todoapp.itemdetail.TodoDetailViewModel
import n.sin.todoapp.itemlist.TodoListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { StorageRepository(androidContext()) }
    viewModel { TodoListViewModel(get()) }
    viewModel { (id: Int) -> TodoDetailViewModel(get(), id) }
}