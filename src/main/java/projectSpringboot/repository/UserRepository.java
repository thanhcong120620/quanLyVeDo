package projectSpringboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import projectSpringboot.entity.UserEntity;

//@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

//	List<UserEntity> findByRoleUser(String userRole);
	Page<UserEntity> findByRoleUser(String userRole, Pageable pageable);
	UserEntity findByMailUser(String mailUser);
	
	//pagination
	Page<UserEntity> findAll(Pageable pageable);
	
	
	
}
