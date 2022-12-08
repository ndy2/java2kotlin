package com.group.libraryapp.domain.book

import com.group.libraryapp.domain.book.QBook.book
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.dto.book.response.QBookStatResponse
import com.querydsl.jpa.impl.JPAQueryFactory
import javax.persistence.EntityManager

class BookRepositoryCustomImpl(
    em: EntityManager
) : BookRepositoryCustom {

    private val queryFactory = JPAQueryFactory(em)

    override fun getStats(): List<BookStatResponse> {
        return queryFactory.select(
            QBookStatResponse(
                book.type,
                book.id.count()
            )
        )
            .from(book)
            .groupBy(book.type)
            .fetch()
    }
}