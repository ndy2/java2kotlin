package com.group.libraryapp.domain.book

import com.group.libraryapp.dto.book.response.BookStatResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookRepository : JpaRepository<Book, Long>, BookRepositoryCustom {

    fun findByName(bookName: String): Book?

    @Deprecated("BookRepositoryCustom.getStats 사용!")
    //@formatter:off
    @Query(
        "select new com.group.libraryapp.dto.book.response.BookStatResponse(b.type, count(b)) " +
        "from Book b " +
        "group by b.type"
    )
    //@formatter:on
    fun getBookStatistics(): List<BookStatResponse>

}