package org.lzyyauto.dto;

import lombok.Data;

/**
 * @author zingliu
 * @date 2025/5/20
 */
@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private String description;

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                '}';
    }

}
