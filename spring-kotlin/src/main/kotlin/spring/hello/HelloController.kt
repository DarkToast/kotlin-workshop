package spring.hello

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET

@Controller
class HelloController(private val helloService: HelloService) {

    @GetMapping("/hello")
    fun helloKotlin(): ResponseEntity<String> {
        return ResponseEntity.ok(helloService.getHello())
    }

}