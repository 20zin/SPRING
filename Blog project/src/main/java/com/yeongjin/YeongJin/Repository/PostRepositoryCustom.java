package com.yeongjin.YeongJin.Repository;

import com.yeongjin.YeongJin.Domain.Post;
import com.yeongjin.YeongJin.Request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
