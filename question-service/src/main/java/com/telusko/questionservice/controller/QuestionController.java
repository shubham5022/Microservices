package com.telusko.questionservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.telusko.questionservice.model.Question;
import com.telusko.questionservice.model.QuestionWrapper;
import com.telusko.questionservice.model.Response;
import com.telusko.questionservice.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@GetMapping("allQuestions")
	public List<Question> getAllQuestions() {

		return questionService.getAllQuestion();

	}

	@GetMapping("QuestionById/{id}")
	public Optional<Question> getQuestionById(@PathVariable int id) {

		return questionService.getQuestionById(id);

	}

	@PostMapping("addQuestions")

	public void addQuestion(@RequestBody Question q) {
		questionService.addQuestion(q);
	}

	// generate
	// getQuestions
	// getScore

	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,
			@RequestParam int numQuestions) {
		return questionService.getQuestionsForQuiz(categoryName, numQuestions);

	}

	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
			
		return questionService.getQuestionsFromId(questionIds);
	}
	
	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
		
		return questionService.getScore(responses);
		
	}
	
}
