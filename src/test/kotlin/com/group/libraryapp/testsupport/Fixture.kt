package com.group.libraryapp.testsupport

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus

fun createBook(
    name: String = "책 이름",
    type: BookType = BookType.COMPUTER,
    id: Long? = null,
) = Book(name, type, id)


fun createUserLoanHistory(
    user: User,
    bookName: String = " ",
    status: UserLoanStatus = UserLoanStatus.LOANED,
) = UserLoanHistory(user, bookName, status)