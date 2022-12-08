package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.user.QUser.user
import com.querydsl.jpa.impl.JPAQueryFactory
import javax.persistence.EntityManager

class UserRepositoryCustomImpl(
    em: EntityManager
) : UserRepositoryCustom {

    private val queryFactory = JPAQueryFactory(em)

    override fun findWithHistories(): List<User> {
        return queryFactory.selectFrom(user)
            .distinct()
            .leftJoin(user.userLoanHistories)
            .fetchJoin()
            .fetch()
    }
}