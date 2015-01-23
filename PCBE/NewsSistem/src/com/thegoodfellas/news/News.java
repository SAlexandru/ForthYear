package com.thegoodfellas.news;

import java.util.Date;

import com.thegoodfellas.dispatcher.Dispatcher;
import com.thegoodfellas.events.*;

public class News {
    private String domain;
    private String subdomain;
    private String author;
    private String title;
    private String content;
    private Date publishingDate;
    private Date lastModifyDate;

    public News (String domain, String subdomain, String author, String title, String content) {
        this.domain = domain;
        this.subdomain = subdomain;
        this.author = author;
        this.title = title;
        this.content = content;
        publishingDate = new Date ();
        lastModifyDate = new Date ();
    }

    public void publish () {
        System.out.println ("News published");
        Dispatcher.getInstance ().notifyHandler (new Event (EventType.PUBLISHED, this));
    }

    public void modify () {
        System.out.println ("News modified");
        Dispatcher.getInstance ().notifyHandler (new Event (EventType.MODIFIED, this));
    }

    public void delete () {
        System.out.println ("News deleted");
        Dispatcher.getInstance ().notifyHandler (new Event (EventType.DELETED, this));
    }

    public void readNews () {
        System.out.println ("News read");
        Dispatcher.getInstance ().notifyHandler (new Event (EventType.READ, this));
    }

    public String getDomain () {
        return domain;
    }

    public void setDomain (String domain) {
        this.domain = domain;
    }

    public String getSubdomain () {
        return subdomain;
    }

    public void setSubdomain (String subdomain) {
        this.subdomain = subdomain;
    }

    public String getAuthor () {
        return author;
    }

    public void setAuthor (String author) {
        this.author = author;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Date getPublishingDate () {
        return publishingDate;
    }

    public Date getLastModifyDate () {
        return lastModifyDate;
    }

    public void setLastModifyDate (Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}
