package com.yeongjin.YeongJin.Request;


import com.yeongjin.YeongJin.Domain.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;


@Getter
@Setter
@Builder
public class PostSearch {

    private static final int Max_Size = 2000;
    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset(){
        return (long) (max(1,page) - 1) * min(size,Max_Size);
    }
}
