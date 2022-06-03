package com.example.practicespace.ui;

public class GridViewItem {

    private int group_icon;
    private int bookSeq;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    //private String thumbnail;
    private String publishDate;
    private String description;
    private String category;
    private boolean rental;


    public GridViewItem(int icon, int bookSeq,String title,String author,
                        String publisher, String isbn,String publishDate,
                        String description, String category, boolean rental){
        this.group_icon = icon;
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

    public int getIcon(){ return this.group_icon; }
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
