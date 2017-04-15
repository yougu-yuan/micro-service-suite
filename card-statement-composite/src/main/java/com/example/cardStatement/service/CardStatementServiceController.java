package com.example.cardStatement.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cardStatement.model.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api")
public class CardStatementServiceController {
	@Autowired
	CardClient cardClient;
	
	@Autowired
	StatementClient statementClient;
	
    @RequestMapping(value="/statement-by-card", method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "defaultCardStatementError")
    public ResponseEntity<Map<CardVO, List<StatementVO>>> 
    getStatementByCardId(@RequestParam Long cardId){
        Map<CardVO, List<StatementVO>> response = new HashMap<>();
         
        response.put(cardClient.getCard(cardId), statementClient.getStatements(cardId));
         
        return new ResponseEntity<Map<CardVO,List<StatementVO>>>(response, HttpStatus.OK);
    }
	
    public ResponseEntity<Map<CardVO, List<StatementVO>>>
    defaultCardStatementError(Long cardId){
        Map<CardVO, List<StatementVO>> response = new HashMap<>();
        //System.out.println("\n!!!!fallback called");
        return new ResponseEntity<Map<CardVO,List<StatementVO>>>(response, HttpStatus.OK);
 
    }
}
