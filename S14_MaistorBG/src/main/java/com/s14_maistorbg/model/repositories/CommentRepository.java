package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.Comment;
import com.s14_maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Override
    Optional<Comment> findById(Integer integer);

    List<Comment> findAllByCommentOwner(User user);
}
