package com.telusko.quizservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//import com.telusko.quizservice.Dao.QuestionDao;
import com.telusko.quizservice.dao.QuizDao;
import com.telusko.quizservice.feign.QuizInterface;
import com.telusko.quizservice.model.QuestionWrapper;
import com.telusko.quizservice.model.Quiz;

@Service
public class QuizService {

	@Autowired
	QuizDao quizdao;

	@Autowired
	QuizInterface quizinterface;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

		List<Integer> questions = quizinterface.getQuestionsForQuiz(category, numQ).getBody();

		Quiz quiz = new Quiz();

		quiz.setTitle(title);
		quiz.setQuestionIds(questions);
		quizdao.save(quiz);

		return new ResponseEntity<>("Success", HttpStatus.CREATED);

	}

	public ResponseEntity<List<com.telusko.questionservice.model.QuestionWrapper>> getQuizQuestions(Integer id) {

		Quiz quiz = quizdao.findById(id).get();
		List<Integer> questionIds = quiz.getQuestionIds();
		ResponseEntity<List<com.telusko.questionservice.model.QuestionWrapper>> questions = quizinterface
				.getQuestionsFromId(questionIds);

		return questions ;
	}

}
