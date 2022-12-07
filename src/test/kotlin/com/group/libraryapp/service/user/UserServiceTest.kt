package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
) {

    @AfterEach
    fun tearDown() {
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
}