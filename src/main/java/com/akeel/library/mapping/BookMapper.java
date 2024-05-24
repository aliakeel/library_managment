package com.akeel.library.mapping;

import com.akeel.library.dto.BookDto;
import com.akeel.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    BookDto BookToBookDto(Book book);

    Book BookDtoToBook(BookDto bookDto);
}
