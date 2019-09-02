package com.enjoy.netty;

import com.enjoy.netty.nettysocket.DiscardServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class NettyDemoApplication {

//	@Resource
//	private DiscardServer discardServer;

	public static void main(String[] args) {
		SpringApplication.run(NettyDemoApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		discardServer.run(8080);
//	}
}
