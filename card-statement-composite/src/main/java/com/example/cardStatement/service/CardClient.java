package com.example.cardStatement.service;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "card-service")
public interface CardClient extends CardService {

}
