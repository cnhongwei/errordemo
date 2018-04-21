package com.example.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.Serializable

enum class CorrectType {
    TYPEA,
    TYPEB,
}

enum class IncorrectType {
    TYPEA {
        override fun desc() = "type a"
    },

    TYPEB {
        override fun desc() = "type b"
    };

    abstract fun desc(): String
}

data class CorrentBean(
        val name: String,
        val type: CorrectType
) : Serializable

data class IncorrentBean(
        val name: String,
        val type: IncorrectType
)

@RunWith(SpringRunner::class)
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun correntBean() {
        Assert.assertEquals("{\"name\":\"corrent\",\"type\":\"TYPEA\"}", objectMapper.writeValueAsString(CorrentBean("corrent", CorrectType.TYPEA)))
    }

    //Will throw exception when run 'mvn test'.
    //Don't throw exception when run test in ide.
    @Test
    fun incorrentBean() {
        Assert.assertEquals("{\"name\":\"incorrent\",\"type\":\"TYPEA\"}", objectMapper.writeValueAsString(IncorrentBean("incorrent", IncorrectType.TYPEA)))
    }
}
