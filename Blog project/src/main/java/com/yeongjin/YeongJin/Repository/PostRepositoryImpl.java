package com.yeongjin.YeongJin.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yeongjin.YeongJin.Domain.Post;
import com.yeongjin.YeongJin.Domain.QPost;
import com.yeongjin.YeongJin.Request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.orderBy;
import static com.yeongjin.YeongJin.Domain.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch){
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset((long) (postSearch.getOffset()))
                .orderBy(post.id.desc())
                .fetch();

    }
}
