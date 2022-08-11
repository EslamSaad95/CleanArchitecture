package com.example.mycleanarch.common

sealed class CustomExceptions : RuntimeException()

class EmailExceptionIsEmpty : CustomExceptions()
class EmailExceptionIsNotValid : CustomExceptions()