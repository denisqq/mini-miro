package com.denisqq.web.controller


import com.denisqq.service.WidgetService
import com.denisqq.web.dto.WidgetDto
import com.denisqq.web.dto.WidgetRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import java.util.UUID


import org.hamcrest.collection.IsCollectionWithSize.hasSize

import org.hamcrest.Matchers.*
import org.mockito.BDDMockito.given
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [WidgetController::class])
class WidgetControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val service: WidgetService? = null

    @Test
    @Throws(Exception::class)
    fun getWidgets() {
        val widgetDto = WidgetDto.builder()
                .id(UUID.randomUUID())
                .x(1.0)
                .y(1.0)
                .height(500.0)
                .width(500.0)
                .zIndex(5.0)
                .build()
        val widgetDtoList = List.of<WidgetDto>(widgetDto)
        given(service!!.findAll(10, 0)).willReturn(widgetDtoList)


        mvc!!.perform(get("/widgets")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize<Any>(1)))
                .andExpect(jsonPath("$[0].id", `is`(widgetDto.id.toString())))
    }

    @Test
    @Throws(Exception::class)
    fun getWidget() {
        val widgetDto = WidgetDto.builder()
                .id(UUID.randomUUID())
                .x(1.0)
                .y(1.0)
                .height(500.0)
                .width(500.0)
                .zIndex(5.0)
                .build()

        given(service!!.findById(widgetDto.id)).willReturn(widgetDto)


        mvc!!.perform(get("/widgets/" + widgetDto.id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", `is`(widgetDto.id.toString())))
                .andExpect(jsonPath("$.x", `is`(widgetDto.x)))
                .andExpect(jsonPath("$.y", `is`(widgetDto.y)))
                .andExpect(jsonPath("$.height", `is`(widgetDto.height)))
                .andExpect(jsonPath("$.width", `is`(widgetDto.width)))
                .andExpect(jsonPath("$.zindex", `is`(widgetDto.zIndex)))
    }

    @Test
    @Throws(Exception::class)
    fun addWidget() {
        val request = WidgetRequest.builder()
                .x(31.0)
                .y(2.0)
                .height(200.0)
                .width(250.0)
                .zIndex(1.0)
                .build()


        val widgetDto = WidgetDto.builder()
                .id(UUID.randomUUID())
                .x(31.0)
                .y(2.0)
                .height(200.0)
                .width(250.0)
                .zIndex(1.0)
                .build()

        given(service!!.create(request)).willReturn(widgetDto)

        mvc!!.perform(post("/widgets")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", `is`(widgetDto)))


    }

    @Test
    fun patchWidget() {
    }

    @Test
    fun deleteWidget() {
    }
}