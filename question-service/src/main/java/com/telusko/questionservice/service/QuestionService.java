package com.telusko.questionservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.questionservice.Dao.QuestionDao;

import com.telusko.questionservice.model.Question;
import com.telusko.questionservice.model.QuestionWrapper;
import com.telusko.questionservice.model.Response;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;

	public List<Question> getAllQuestion() {

		return questionDao.findAll();

	}

	public Optional<Question> getQuestionById(int id) {

		return questionDao.findById(id);
	}

	public void addQuestion(Question q) {

		questionDao.save(q);

	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
		List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

		List<QuestionWrapper> wrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>();
		
		for(Integer id : questionIds) {
			questions.add(questionDao.findById(id).get());
		}
		
		for (Question question : questions) {
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setCategory(question.getCategory());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			
			wrappers.add(wrapper);
		}
		
		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
	    int right = 0;
	    
	    for (Response response : responses) {
	        Optional<Question> optionalQuestion = questionDao.findById(response.getId());
	        
	        if (optionalQuestion.isPresent()) {
	            Question question = optionalQuestion.get();
	            
	            if (response.getResponse().equals(question.getRight_answer())) {
	                right++;
	            }
	        } else {
	            // Handle the case where the question with the specified ID is not found.
	            // For example, you might throw an exception or handle it in some other way.
	            // Here, I'll just print a message to the console.
	            System.out.println("Question not found for ID: " + response.getId());
	        }
	    }
	    
	    return new ResponseEntity<>(right, HttpStatus.OK);
	}

}
