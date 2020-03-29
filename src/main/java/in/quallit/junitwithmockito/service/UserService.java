package in.quallit.junitwithmockito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.quallit.junitwithmockito.entity.User;
import in.quallit.junitwithmockito.exception.CustomException;
import in.quallit.junitwithmockito.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		if (user.getName() == null || "".equals(user.getName())) {
			throw new CustomException("ERR1", "User name is required");
		}
		return this.userRepository.save(user);
	}

	public User findById(long id) {
		return this.userRepository.findById(id).orElse(null);
	}

}
