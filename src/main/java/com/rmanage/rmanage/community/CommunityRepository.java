package com.rmanage.rmanage.community;

import com.rmanage.rmanage.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByWorkPlace_WorkPlaceIdAndType(Long workPlaceId, String type);

}
