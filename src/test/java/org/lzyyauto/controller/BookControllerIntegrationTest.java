package org.lzyyauto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lzyyauto.dto.BookDto;
import org.lzyyauto.entity.Book;
import org.lzyyauto.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll(); // 每个测试前清理数据

        book1 = Book.builder().title("集成测试书籍1").author("作者1").isbn("IT-ISBN-001").publicationYear(2020).description("工具书").build();
        book2 = Book.builder().title("集成测试书籍2").author("作者2").isbn("IT-ISBN-002").publicationYear(2021).description("故事书").build();

        book1 = bookRepository.save(book1);
        book2 = bookRepository.save(book2);
    }

    @Test
    void testCreateBook_Success() throws Exception {
        BookDto newBookDto = new BookDto();
        newBookDto.setTitle("很棒的新书");
        newBookDto.setAuthor("很棒的作者");
        newBookDto.setIsbn("NA-ISBN-003");
        newBookDto.setPublicationYear(2024);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(jsonPath("$.data.title", is("很棒的新书")))
                .andExpect(jsonPath("$.data.isbn", is("NA-ISBN-003")));
    }

    @Test
    void testCreateBook_IsbnAlreadyExists() throws Exception {
        BookDto existingIsbnBookDto = new BookDto();
        existingIsbnBookDto.setTitle("另一本相同ISBN的书");
        existingIsbnBookDto.setAuthor("作者X");
        existingIsbnBookDto.setIsbn(book1.getIsbn()); // 已存在的ISBN
        existingIsbnBookDto.setPublicationYear(2025);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingIsbnBookDto)))
                .andExpect(status().isInternalServerError()) // 错误全捕获为500
                .andExpect(jsonPath("$.message", containsString("ISBN已存在")));
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].title", is(book1.getTitle())))
                .andExpect(jsonPath("$.data[1].title", is(book2.getTitle())));
    }

    @Test
    void testGetBookById_Success() throws Exception {
        mockMvc.perform(get("/api/books/" + book1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(jsonPath("$.data.title", is(book1.getTitle())));
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        long nonExistentId = 9999L;
        mockMvc.perform(get("/api/books/" + nonExistentId))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("图书不存在: " + nonExistentId)));
    }

    @Test
    void testUpdateBook_Success() throws Exception {
        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setTitle("更新后的书籍1标题");
        updatedBookDto.setAuthor(book1.getAuthor());
        updatedBookDto.setIsbn(book1.getIsbn()); // 保持ISBN不变
        updatedBookDto.setPublicationYear(book1.getPublicationYear());

        mockMvc.perform(put("/api/books/" + book1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(jsonPath("$.data.title", is("更新后的书籍1标题")));
    }

    @Test
    void testUpdateBook_NotFound() throws Exception {
        long nonExistentId = 9999L;
        BookDto bookDtoToUpdate = new BookDto();
        bookDtoToUpdate.setTitle("不存在的书籍");
        bookDtoToUpdate.setAuthor("不存在的作者");
        bookDtoToUpdate.setIsbn("NE-ISBN-000");
        bookDtoToUpdate.setPublicationYear(2000);

        mockMvc.perform(put("/api/books/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDtoToUpdate)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("图书不存在: " + nonExistentId)));
    }


    @Test
    void testDeleteBook_Success() throws Exception {
        mockMvc.perform(delete("/api/books/" + book1.getId()))
                .andExpect(status().isOk()) // 服务层返回void，控制器包装在ResponseVO中
                .andExpect(jsonPath("$.message", is("success")));

        assertFalse(bookRepository.existsById(book1.getId()));
    }

    @Test
    void testDeleteBook_NotFound() throws Exception {
        long nonExistentId = 9999L;
        mockMvc.perform(delete("/api/books/" + nonExistentId))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("图书不存在: " + nonExistentId)));
    }
}