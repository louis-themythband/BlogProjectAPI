package com.sos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sos.blog.entity.Like;
import com.sos.blog.entity.LikeCompoundKey;


@Repository
public interface LikeRepository extends JpaRepository<Like, LikeCompoundKey> {

}
