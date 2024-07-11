package com.jdeleonc.foro.forohub.repository;

import com.jdeleonc.foro.forohub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {
}
