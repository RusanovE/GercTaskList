package org.example.gerctasklist.entities

import jakarta.persistence.*
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate

@Entity
@Table(name = "tasks")
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    var id: Long? = null,

    @Column(nullable = false)
    var title: String = "",

    @Column(columnDefinition = "TEXT")
    var description: String? = "",

    @LastModifiedDate
    @Column(nullable = false)
    var dueDate: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var priority: TaskPriority = TaskPriority.LOW,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TaskStatus = TaskStatus.UNCOMPLETED,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
)
