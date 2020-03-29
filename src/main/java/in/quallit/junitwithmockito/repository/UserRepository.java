package in.quallit.junitwithmockito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.quallit.junitwithmockito.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
