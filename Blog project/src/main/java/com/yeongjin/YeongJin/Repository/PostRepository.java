package com.yeongjin.YeongJin.Repository;

import com.yeongjin.YeongJin.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{
}
