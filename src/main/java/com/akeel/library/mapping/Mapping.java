package com.akeel.library.mapping;

import com.akeel.library.dto.BookDto;
import com.akeel.library.dto.BorrowingRecordDto;
import com.akeel.library.dto.PatronDto;
import com.akeel.library.model.Book;
import com.akeel.library.model.BorrowingRecord;
import com.akeel.library.model.Patron;

public class Mapping {
    public static BookDto toBookDto(Book book){
        return BookMapper.INSTANCE.BookToBookDto(book);
    }
    public static Book toBook(BookDto book){
        return BookMapper.INSTANCE.BookDtoToBook(book);
    }

    public static PatronDto toPatronDto(Patron patron){
        return PatronMapper.INSTANCE.PatronToPatronDto(patron);
    }
    public static Patron toPatron(PatronDto patronDto){
        return PatronMapper.INSTANCE.PatronDtoToPatron(patronDto);
    }
    public static BorrowingRecordDto toBorrowingRecordDto(BorrowingRecord borrowingRecord){
        return BorrowingRecordMapper.INSTANCE.BorrowingRecordToBorrowingRecordDto(borrowingRecord);
    }
    public static BorrowingRecord toBorrowingRecord(BorrowingRecordDto borrowingRecordDto){
        return BorrowingRecordMapper.INSTANCE.BorrowingRecordDtoToBorrowingRecord(borrowingRecordDto);
    }
}
