package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
class User(

    @Column(nullable = false)
    var name: String,

    val age: Int?,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long?=null,
) {

    init {
        if(name.isBlank()) throw IllegalArgumentException("이름이 공백입니다")
    }

    fun loanBook(book: Book){
        userLoanHistories.add(UserLoanHistory(this, book.name, false))
    }

    fun returnBook(bookName: String){
        val targetHistory = userLoanHistories.first { it.bookName == bookName }
        targetHistory.doReturn()
    }

    fun updateName(name: String){
        this.name = name
    }
}