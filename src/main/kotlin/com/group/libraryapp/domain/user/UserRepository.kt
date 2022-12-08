package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {

    fun findByName(name: String): User?

    @Deprecated("UserRepositoryCustom.findWithHistories 로 변경!")
    //@formatter:off
    @Query(
        "select distinct u " +
        "from User u " +
        "left join fetch u.userLoanHistories "
    )
    //@formatter:on
    fun findAllWithUserLoanHistories(): List<User>
}