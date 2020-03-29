package in.quallit.junitwithmockito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.quallit.junitwithmockito.entity.User;
import in.quallit.junitwithmockito.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<User> save(@RequestBody User user) {
		return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") long id) {
		User user = this.userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(this.userService.findById(id), HttpStatus.FOUND);
		}
	}
}
