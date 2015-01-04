package com.vvs.webserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		int port;
		Path base;
		Path maintenance;
		
		try {
			port = Integer.parseInt(args[0]);
			base = FileSystems.getDefault().getPath(args[1]);
			maintenance = FileSystems.getDefault().getPath(args[2]);
			
			File b = base.toAbsolutePath().normalize().toFile(), m = maintenance.toAbsolutePath().normalize().toFile();
			
			if (port < 1025 || port > 65535) {
				throw new Exception ("port number must be between 1024 and 65535");
			}
			if (!b.exists() || !m.exists() || !b.isDirectory() || !m.isDirectory()) {
				throw new Exception ("base path and maitenance path must be real path to folders!");
			}
		}
		catch (Exception e) {
			System.out.println("Usage: java Main port basePath maitenancePath");
			System.out.println("Error: " + e.getMessage());
			throw e;
		}
		
		ServerSocket s = null;
		
		try {
			s = new ServerSocket(port, 100);
		} catch (IOException e) {
			System.out.println("Cannot listen to the port you specified");
			
		}
		
		WebServer server = new WebServer(s, base, maintenance, 10);
		try {
			server.start();
		} catch (IOException e1) {
			throw e1;
		}
		
		try {
			server.join();
		} catch (InterruptedException e) {
			System.out.println("server was interrupted");
			throw e;
		}
		finally {
			try {
				server.close();
			} catch (IOException e) {
				//e.printStackTrace();
				throw e;
			}
		}
		
		
	}
}
