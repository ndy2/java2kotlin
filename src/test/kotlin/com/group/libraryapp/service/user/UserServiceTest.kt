package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.testsupport.createUserLoanHistory
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @AfterEach
    fun tearDown() {
        println("clean up db")
        userRepository.deleteAll()
    }

    @Test
    fun saveUser() {
        //given
        val request = UserCreateRequest("남득윤", null)

        //when
        userService.saveUser(request)

        //then
        val findAll = userRepository.findAll()
        assertThat(findAll).hasSize(1)
        assertThat(findAll[0].name).isEqualTo("남득윤")
        assertThat(findAll[0].age).isEqualTo(null)
    }

    @Test
    fun getUsers() {
        //given
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", null)
            )
        )

        //when
        val results = userService.getUsers()

        //then
        assertThat(results).hasSize(2)
        assertThat(results).extracting("name").containsExactly("A", "B")
        assertThat(results).extracting("age").containsExactly(20, null)
    }

    @Test
    fun updateUsername() {
        //given
        val saveUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(saveUser.id!!, "B")

        //when
        userService.updateUserName(request)

        //then
        val findAll = userRepository.findAll()
        assertThat(findAll).hasSize(1)
        assertThat(findAll[0].name).isEqualTo("B")
    }

    @Test
    fun deleteUser() {
        //given
        userRepository.save(User("A", null))

        //when
        userService.deleteUser("A")

        //then
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다")
    fun getUserLoanHistoriesTest1(){
        //given
        userRepository.save(User("A", null))

        //when
        val results = userService.getUserLoanHistories()

        //then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저도 대출 기록을 잘 응답한다")
    fun getUserLoanHistoriesTest2(){
        //given
        val savedUser = userRepository.save(User("A", null))
        userLoanHistoryRepository.save(createUserLoanHistory(savedUser, "책1", UserLoanStatus.LOANED))
        userLoanHistoryRepository.save(createUserLoanHistory(savedUser, "책2", UserLoanStatus.RETURNED))

        //when
        val results = userService.getUserLoanHistories()

        //then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(2)
        assertThat(results[0].books[0].name).isEqualTo("책1")
        assertThat(results[0].books[0].isReturn).isFalse
        assertThat(results[0].books[1].name).isEqualTo("책2")
        assertThat(results[0].books[1].isReturn).isTrue
    }

}