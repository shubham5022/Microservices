package com.telusko.quizservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.quizservice.model.QuestionWrapper;
import com.telusko.quizservice.model.QuizDto;

@RestController
@RequestMapping("quiz")
public class QuizController {
	
	@Autowired
	com.telusko.quizservice.service.QuizService quizservice;

	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {

		return  quizservice.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
	}
	
	@GetMapping("get/{id}")
	
	public ResponseEntity<List<com.telusko.questionservice.model.QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
		
	return 	quizservice.getQuizQuestions(id);
		
	}
	

}
