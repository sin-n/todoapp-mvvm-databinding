package n.sin.todoapp

class Event<out T>(private val content: T) {

    fun getContent(): T = content
}
