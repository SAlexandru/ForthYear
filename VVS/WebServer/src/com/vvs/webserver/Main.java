package com.vvs.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) {
		ServerSocket s = null;
		try {

			s = new ServerSocket(8011, 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WebServer server = new WebServer(s, Paths.get("/Users/salexandru/Desktop/"), 3);
		server.start();
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
