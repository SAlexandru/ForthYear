package com.vvs.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.vvs.webserver.HTTP.HttpGetResponse;
import com.vvs.webserver.HTTP.HttpRequest;

public class WebServer implements AutoCloseable {
	private Path base_;
	private Path maintenance_;
	private ServerSocket server_;
	private Thread mainThread_;
	private ThreadPoolExecutor pool_;
	private WebServerState state_ = WebServerState.STOPPED;
	
	private class RequestSolver implements Runnable {
		private Socket s_;
		
		public RequestSolver(Socket s) {
			s_ = s;
		}

		@Override
		public void run() {
			try {
				while (true) {
					new HttpGetResponse(new HttpRequest(s_.getInputStream()),
										base_
										).send(s_.getOutputStream());
					if (!s_.getKeepAlive()) {
						s_.close();
						return;
					}
				}
			}
			catch (IOException e) {
				return;
			}
			finally {
				if (!s_.isClosed()) {
					try {
						// the one who implemented this is a bloody idiot 
						s_.close();
					} catch (IOException e) {
					}
				}
			}
			
		}
	}
	
	/*
	 *   Will create a web server with:
	 *   The core pool size is set to 2
	 *   The maximumNumberOfThreads will be set to 10 (or whatever number you added)
	 *   The backlog will have a default value of 100
	 */
	public WebServer(ServerSocket socket, Path path, int maxNumberOfThreads) {
		base_   = path;
		server_ = socket;
		pool_ = new ThreadPoolExecutor(/*core*/ 2, 
				   					   /*max*/ maxNumberOfThreads, 
				   					   /*keepAlive*/ 10,
				   					   TimeUnit.SECONDS,
				   					   new LinkedBlockingQueue<>()
				  					 );
	}
	
	public int getMaxNumberOfThreads() {return pool_.getMaximumPoolSize();}
	
	public void setMaxNumberOfThreads (int maxNumberOfThreads) {
		pool_.setMaximumPoolSize(maxNumberOfThreads);
	}
	
	public Path getMaintenancePath() {return maintenance_.toAbsolutePath();}
	
	public void setMaintenancePath(Path maintenance) {
		maintenance_ = maintenance;
	}
	
	public WebServerState getState() {
		return state_;
	}
	
	public void setState(WebServerState state) throws IOException {
		switch (state) {
			case RUNNING: start();
			case STOPPED: stop();
			default:
				state_ = state;
		}
	}
	
	public Path getBaseDirectory() {return base_.toAbsolutePath();}
	
	public void setBaseDirectory(Path path) throws IOException {
		stop();
		base_ = path;
		start();
	}
	
	public SocketAddress getSocketAddress() {return server_.getLocalSocketAddress();}
	
	public void bind(SocketAddress endpoint) throws IOException {
		stop();
		server_.bind(endpoint);
		start();
	}
	
	public void bind(SocketAddress endpoint, int backlog) throws IOException {
		stop();
		server_.bind(endpoint, backlog);
		start();
	}
	
	public boolean isAlive() {
		return null != mainThread_ &&
			   mainThread_.isAlive();
	}
	
	public void start() {
		mainThread_ = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					while (!Thread.interrupted()) {
						pool_.execute(new RequestSolver(server_.accept()));
					}

				} catch (IOException e) {
					return;
				} finally {
					state_ = WebServerState.STOPPED;
				}
			}
		});
		mainThread_.start(); 
		state_ = WebServerState.RUNNING;
	}
	
	public void restart() throws IOException {
		stop();
		start();
	}
	
	public void join() throws InterruptedException {
		if (isAlive()) {
			mainThread_.join();
		}
	}
	
	public void stop() throws IOException {
		mainThread_.interrupt();
		pool_.shutdown();
		server_.close();
		state_ = WebServerState.STOPPED;
	}

	@Override
	public void close() throws IOException {
		stop();
	}
}
