package com.akeel.library.mapping;

import com.akeel.library.dto.BorrowingRecordDto;
import com.akeel.library.model.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BorrowingRecordMapper {
    BorrowingRecordMapper INSTANCE = Mappers.getMapper( BorrowingRecordMapper.class );

    BorrowingRecordDto BorrowingRecordToBorrowingRecordDto(BorrowingRecord borrowingRecord);

    BorrowingRecord BorrowingRecordDtoToBorrowingRecord(BorrowingRecordDto borrowingRecordDto);
}
