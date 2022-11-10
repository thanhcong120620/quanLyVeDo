package projectSpringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projectSpringboot.entity.RegionEntity;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Long>{

	RegionEntity findOneByRegionCode(String code); 
	RegionEntity findOneByNameProvince(String nameProvince); 
	
}
