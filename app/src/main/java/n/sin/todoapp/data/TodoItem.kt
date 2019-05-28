package n.sin.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class TodoItem constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var text: String,
    var active: Boolean) {
}
