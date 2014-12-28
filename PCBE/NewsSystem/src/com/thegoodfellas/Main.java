package com.thegoodfellas;

import com.thegoodfellas.actors.Editor;
import com.thegoodfellas.actors.Reader;
import com.thegoodfellas.dispatcher.Dispatcher;
import com.thegoodfellas.news.News;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
        Editor[] editors = new Editor[10];
        Reader[] readers = new Reader[10];



        for (int i = 0; i < 10; ++i) {
            editors[i] = new Editor(new Integer(i).toString());
            readers[i] = new Reader(new Integer(i).toString());
        }

        for (int i = 0; i < 10; ++i) {
            editors[i].createNews();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Dispatcher.getInstance().kill();
    }
}
