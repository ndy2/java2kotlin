package com.group.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long> {

    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?

    //@formatter:off
    @Query(
        "select count (h)" +
        "from UserLoanHistory h " +
        "where h.status = com.group.libraryapp.domain.user.loanhistory.UserLoanStatus.LOANED"
    )
    //@formatter:on
    fun countLoanedBook(): Int
}