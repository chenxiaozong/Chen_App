package com.example.a06_chen.bean;

/**
 * Created by chen on 2017/7/14.
 * bookStore 中book对应的bean 对象
 */

public class Book {
    private  String name;
    private  String author;
    private  int pages;
    private  float price;

    public Book() {
    }

    public Book(String name, String author, int pages, float price) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
