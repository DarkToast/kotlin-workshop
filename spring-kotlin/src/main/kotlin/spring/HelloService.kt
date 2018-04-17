package spring

import org.springframework.stereotype.Service

@Service
class HelloService {
    fun getHello(): String = "Hallo Welt"
}