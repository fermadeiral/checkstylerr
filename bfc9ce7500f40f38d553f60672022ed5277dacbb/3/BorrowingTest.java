package de.codecentric.psd.worblehat.domain;


import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


class BorrowingTest {

    @Test
    void getDueDate() {
        var borrowingUnderTest = new Borrowing();
        var now = new Date(2020, Calendar.OCTOBER, 1, 9, 0);
        borrowingUnderTest.setBorrowDate(now);

        Date dueDate = borrowingUnderTest.getDueDate();
        assertThat(dueDate, is(new Date(2020, Calendar.OCTOBER, 29, 9, 0)));
    }

    @Test
    void worksWithSqlDate() {
        var borrowingUnderTest = new Borrowing();
        var now = new java.sql.Date((new Date(2020, Calendar.OCTOBER, 1, 9, 0)).getTime());

        borrowingUnderTest.setBorrowDate(now);

        Date dueDate = borrowingUnderTest.getDueDate();
        assertThat(dueDate, is(new Date(2020, Calendar.OCTOBER, 29, 9, 0)));

    }
}
