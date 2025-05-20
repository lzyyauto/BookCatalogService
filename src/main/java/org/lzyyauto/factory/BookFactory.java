package org.lzyyauto.factory;

import org.lzyyauto.entity.Book;

/**
 * @author zingliu
 * @date 2025/5/20
 */
public class BookFactory {
    //TODO 工厂模式
    public static Book createBook(int type, String title, String author, String isbn, Integer publicationYear) {
        Book book = Book.builder().title(title).author(author).isbn(isbn).publicationYear(publicationYear).build();

        //没有实际意义
        switch (type) {
            case 1:
                //工具书
                book.setDescription("工具书");
                break;
            case 2:
                //故事书
                book.setDescription("故事书");
                break;
            default:
                // 其他值忽略不计
                book.setDescription("未分类");
                break;
        }
        return book;
    }

}
