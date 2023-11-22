package com.example.demo.users

import org.springframework.data.repository.CrudRepository

// interface para interagir com o banco de dados.
interface UserRepository : CrudRepository<User, Int>