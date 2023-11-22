package com.example.demo.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// @Autowired para dizer a Spring para injetar o .UserRepository
// @RequestBody = é para informar ao Spring a URL base da API Rest como "/api/users"


//  Possuímos cinco métodos para interagir com o banco de dados:

// getAllUsers: para buscar todos os usuários.
// createUser: para criar um novo usuário.
// getUserById: para obter um usuário por id.
// updateUserById: para atualizar um usuário por id.
// deleteUserById: para excluir um usuário por id.

@RestController
@RequestMapping("/api/users")
class UserController(
    @Autowired private val userRepository: UserRepository
) {

    @GetMapping("")
    fun getAllUsers(): List<User> = userRepository.findAll().toList()

    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val createdUser = userRepository.save(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        val user = userRepository.findById(userId).orElse(null)
        return if (user != null) ResponseEntity(user, HttpStatus.OK) else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Int, @RequestBody user: User): ResponseEntity<User> {
        val existingUser = userRepository.findById(userId).orElse(null)

        if (existingUser == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updateUser = existingUser.copy(name = user.name, email = user.email)
        userRepository.save(updateUser)
        return ResponseEntity(updateUser, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        userRepository.deleteById(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}


