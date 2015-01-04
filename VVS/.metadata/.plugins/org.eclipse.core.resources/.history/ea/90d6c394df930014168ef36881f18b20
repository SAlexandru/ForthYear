package com.vvs.webserver.HTTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;

public class HttpGetResponse {
	private Path p_;
	private String body_;
	private String httpCode_;
	private String contentType_;
	private HttpRequest request_;
	
	public HttpGetResponse (HttpRequest request, Path base) {
		if (null == base || null == request) {
			throw new IllegalArgumentException();
		}
		
		p_ = base;
		body_ = "";
		contentType_ = "text/html; charset=UTF-8\r\n";
		httpCode_ = "HTTP/1.1 200 OK\r\n";
		request_ = request;
	}
	
	private void getFile(File f) {
		httpCode_ = "HTTP/1.1 200 OK\r\n";
		String encoding = "UTF-8";
		try (InputStreamReader input = new InputStreamReader(new FileInputStream(f));) {
			encoding = input.getEncoding();	
			for (int ch = input.read(); -1 != ch; ch = input.read()) {
				body_ = String.format("%s%c", body_, ch);
			}
		} catch (IOException e) {
		}
		
		String fileExt = f.getAbsolutePath();
		if (-1 == fileExt.lastIndexOf('.')) {
			fileExt = "txt";
		}
		else {
			fileExt = fileExt.substring(fileExt.lastIndexOf('.') + 1);
		}
		contentType_ =  ContentType.valueOf(fileExt.toUpperCase()) + "; " + encoding + "\r\n";
	}
	
	public void send(OutputStream out) throws IOException {
		File f;
		if (request_.getPath().isEmpty()) {
			f = p_.toAbsolutePath().resolve("index.html").toFile();
		}
		else {
			f = p_.toAbsolutePath().resolve(request_.getPath()).normalize().toFile();
		}
		if (request_.isInvalid()) {
			HttpUtility.send400(out);
		}
		else if (HttpUtility.isHeadMethod(request_.getHttpMethod())) {
			if (!f.exists()) {
				HttpUtility.send404(out);
			}
			else {
				HttpUtility.send200(out);
			}
		}
		else if (!HttpUtility.isGetMethod(request_.getHttpMethod())) {
			HttpUtility.send501(out);
		}
		else {
			if (f.exists()) {
				getFile(f);
				HttpUtility.send(out, httpCode_, contentType_, body_);
			}
			else HttpUtility.send404(out);
		}
	}
}
