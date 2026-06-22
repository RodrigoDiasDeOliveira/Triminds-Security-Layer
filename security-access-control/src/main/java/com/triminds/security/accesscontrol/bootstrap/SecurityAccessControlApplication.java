package com.triminds.security.accesscontrol.bootstrap;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class SecurityAccessControlApplication {


public static void main(String[] args){

SpringApplication.run(
SecurityAccessControlApplication.class,
args);

}


}