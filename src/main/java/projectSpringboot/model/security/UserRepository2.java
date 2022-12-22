package projectSpringboot.model.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import projectSpringboot.entity.UserEntity;



@Repository
public interface UserRepository2 extends JpaRepository<UserEntity, Long> {

	@Query(value ="select * FROM user as e WHERE e.mailuser = :mailUser", nativeQuery = true)
	Optional<UserEntity> findByMailUser2(String mailUser);
}