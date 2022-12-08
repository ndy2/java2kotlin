package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long>{

    fun findByName(name: String): User?

    //@formatter:off
    @Query(
        "select u " +
        "from User u " +
        "left join u.userLoanHistories "
    )
    //@formatter:on
    fun findAllWithUserLoanHistories() : List<User>
}