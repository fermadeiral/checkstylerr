package de.codecentric.psd.worblehat.acceptancetests.step.page;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBook;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ListBorrowedBooksPage {

    private SeleniumAdapter seleniumAdapter;
    private StoryContext storyContext;

    @Autowired
    public ListBorrowedBooksPage(SeleniumAdapter seleniumAdapter, StoryContext storyContext) {
        this.seleniumAdapter = seleniumAdapter;
        this.storyContext = storyContext;
    }

    @When("{string} requests list of borrowed books")
    public void requests_list_of_borrowed_books(String borrower) {
        seleniumAdapter.gotoPage(Page.BORROWED_BOOK_LIST);
        seleniumAdapter.typeIntoField("email", borrower);
        seleniumAdapter.clickOnPageElementById(PageElement.SHOW_BORROWED_BOOKS_BUTTON);
    }

    @Then("the list shows the books {string} with due date {date}")
    public void the_list_shows_the_books(String isbns, LocalDate dueDate) {
        HtmlBookList tableContent = seleniumAdapter.getTableContent(PageElement.BORROWED_BOOK_LIST);
        HtmlBook bookByIsbn = tableContent.getBookByIsbn(isbns);

        assertThat(bookByIsbn).isNotNull();
        assertThat(bookByIsbn)
            .hasFieldOrPropertyWithValue("dueDate", dueDate)
            .hasFieldOrPropertyWithValue("isbn", isbns);
    }




}
