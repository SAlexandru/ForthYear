package com.vvs.webserver.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class HttpRequest {
	private String httpMethod_;
	private String httpPath_;
	private String protocol_;
	private boolean invalidRequest_ = false;
	
	public HttpRequest (InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String request = "";
		for (String line; null != (line = reader.readLine()); ) {
			if (line.trim().isEmpty()) {
				break;
			}
			request += " " + line;
		}
		try {
			StringTokenizer tokenizer = new StringTokenizer(request);
			httpMethod_ = tokenizer.nextToken();
			httpPath_ = tokenizer.nextToken();
			protocol_ = tokenizer.nextToken();
			
			if (httpPath_.contains("://www")) {
				httpPath_ = httpPath_.substring(httpPath_.indexOf("/", httpPath_.indexOf("://www") + 6) + 1);
			}
			if (httpPath_.contains("?")) {
				httpPath_ = httpPath_.substring(0, httpPath_.indexOf("?"));
			}
			if (httpPath_.startsWith("/")) {
				httpPath_ = httpPath_.substring(1);
			}
		} catch (NoSuchElementException e) {
			invalidRequest_ = true;
			httpMethod_ = "";
			httpPath_ = "}{}{}{";
			protocol_ = "ana are mere multe si frumoase :)";
		}
	}
	
	public String getHttpMethod() {return httpMethod_;}
	
	public String getPath() {return httpPath_;}
	
	public String getProtocol() {return protocol_;}

	public boolean isInvalid() {return invalidRequest_;}
}