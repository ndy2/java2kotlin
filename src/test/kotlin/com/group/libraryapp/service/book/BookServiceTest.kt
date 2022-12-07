package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.testsupport.createBook
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThatExceptionOfType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
){

    @AfterEach
    fun tearDown() {
        bookRepository.deleteAll()
        userRepository.deleteAll()

    }

    @Test
    fun saveBook() {
        //then
        val request = BookRequest("이상한 나라", BookType.ECONOMY)

        //when
        bookService.saveBook(request)

        //then
        val findAll = bookRepository.findAll()
        assertThat(findAll).hasSize(1)
        assertThat(findAll[0].name).isEqualTo("이상한 나라")
        assertThat(findAll[0].type).isEqualTo(BookType.ECONOMY)
    }

    @Test
    fun loanBook() {
        bookRepository.save(createBook("이상한 나라"))
        val savedUser = userRepository.save(User("하하", 20))
        val request = BookLoanRequest("하하", "이상한 나라")

        //when
        bookService.loanBook(request)

        //then
        val findAll = userLoanHistoryRepository.findAll()
        assertThat(findAll).hasSize(1)
        assertThat(findAll[0].bookName).isEqualTo("이상한 나라")
        assertThat(findAll[0].user.id).isEqualTo(savedUser.id)
        assertThat(findAll[0].status).isEqualTo(UserLoanStatus.LOANED)
    }

    @Test
    fun `loanBook fail`() {
        //given
        bookRepository.save(createBook("이상한 나라"))
        val savedUser = userRepository.save(User("하하", 20))
        val request = BookLoanRequest("하하", "이상한 나라")
        bookService.loanBook(request)

        //when then
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy { bookService.loanBook(request) }
    }

    @Test
    fun `return book`() {
        bookRepository.save(createBook("이상한 나라"))
        val savedUser = userRepository.save(User("하하", 20))
        bookService.loanBook(BookLoanRequest("하하", "이상한 나라"))
        val request = BookReturnRequest("하하", "이상한 나라")

        //when
        bookService.returnBook(request)

        //then
        val findAll = userLoanHistoryRepository.findAll()
        assertThat(findAll).hasSize(1)
        assertThat(findAll[0].bookName).isEqualTo("이상한 나라")
        assertThat(findAll[0].user.id).isEqualTo(savedUser.id)
        assertThat(findAll[0].status).isEqualTo(UserLoanStatus.RETURNED)
    }

}