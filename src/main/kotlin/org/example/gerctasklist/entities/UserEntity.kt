package org.example.gerctasklist.entities

import jakarta.persistence.*
import org.example.gerctasklist.dto.enums.Role
import org.hibernate.Hibernate

@Table(name = "users")
@Entity
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(unique = true, nullable = false)
    var id: Long? = 0,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    var roles: MutableList<Role> = mutableListOf(),

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    var tasks: MutableList<TaskEntity>? = mutableListOf()

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 1

}

