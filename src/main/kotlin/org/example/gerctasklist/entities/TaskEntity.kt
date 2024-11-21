package org.example.gerctasklist.entities

import jakarta.persistence.*
import org.example.gerctasklist.dto.enums.TaskPriority
import org.example.gerctasklist.dto.enums.TaskStatus
import org.springframework.data.annotation.LastModifiedDate

@Entity
@Table(name = "tasks")
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true)
    var id: Long? = 0,

    @Column(nullable = false)
    var title: String = "task title",

    @Column(columnDefinition = "TEXT")
    var description: String? = "null",

    @LastModifiedDate
    @Column(nullable = false)
    var deadLine: String? = "No time limit",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var priority: TaskPriority = TaskPriority.LOW,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TaskStatus = TaskStatus.UNCOMPLETED,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
)
