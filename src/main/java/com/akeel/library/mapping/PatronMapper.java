package com.akeel.library.mapping;

import com.akeel.library.dto.PatronDto;
import com.akeel.library.model.Patron;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatronMapper {
    PatronMapper INSTANCE = Mappers.getMapper( PatronMapper.class );

    PatronDto PatronToPatronDto(Patron patron);

    Patron PatronDtoToPatron(PatronDto patronDto);
}
