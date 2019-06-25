package n.sin.todoapp

import android.app.Application
import n.sin.todoapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Koin
        startKoin {
            androidContext(this@CustomApplication)
            modules(appModule)
        }
    }
}