package org.lzyyauto.factory;

import org.junit.jupiter.api.Test;
import org.lzyyauto.entity.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookFactoryTest {

    @Test
    void testCreateBook() {
        Book toolBook = BookFactory.createBook(1, "三体", "刘慈欣", "978-0134685991", 2018);
        assertEquals("工具书", toolBook.getDescription());
        assertEquals("三体", toolBook.getTitle());

        Book storyBook = BookFactory.createBook(2, "小王子", "安托万·德·圣-埃克苏佩里", "978-0156012195", 1943);
        assertEquals("故事书", storyBook.getDescription());
        assertEquals("小王子", storyBook.getTitle());

        Book otherBook = BookFactory.createBook(3, "未知分类书籍", "未知作者", "123-4567890123", 2024);
        assertEquals("未分类", otherBook.getDescription());
        assertEquals("未知分类书籍", otherBook.getTitle());
    }
}