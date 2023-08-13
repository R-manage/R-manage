package com.rmanage.rmanage.community.repository;

import com.rmanage.rmanage.entity.Community;
import com.rmanage.rmanage.entity.WorkPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByWorkPlace_WorkPlaceIdAndType(Long workPlaceId, String type);
    List<Community> findCommunityBypostId(Long postId);
    List<Community> findCommunityByWorkPlaceAndType(WorkPlace workPlace, String type);

}
