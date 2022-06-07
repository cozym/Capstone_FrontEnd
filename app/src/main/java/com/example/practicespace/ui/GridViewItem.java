package com.example.practicespace.ui;

public class GridViewItem {

    private String book_thumb;
    private int bookSeq;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String publishDate;
    private String description;
    private String category;
    private boolean rental;


    public GridViewItem(String icon, int bookSeq,String title,String author,
                        String publisher, String isbn,String publishDate,
                        String description, String category, boolean rental){
        this.book_thumb = icon;
        this.bookSeq = bookSeq;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.description = description;
        this.category = category;
        this.rental = rental;
    }

    public String getThumbnail(){ return this.book_thumb; }
    public int getBookSeq(){ return this.bookSeq; }
    public String getTitle(){ return this.title;}
    public String getAuthor(){return this.author;}
    public String getPublisher(){return this.publisher;}
    public String getIsbn(){return this.isbn;}
    public String getPublishDate(){return this.publishDate;}
    public String getDescription(){return this.description;}
    public String getCategory(){return this.category;}
    public boolean getRental(){return this.rental;}


}
