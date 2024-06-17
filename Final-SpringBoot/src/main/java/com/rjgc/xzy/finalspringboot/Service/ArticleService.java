package com.rjgc.xzy.finalspringboot.Service;

import com.rjgc.xzy.finalspringboot.Pojo.Article;
import com.rjgc.xzy.finalspringboot.Pojo.Category;
import com.rjgc.xzy.finalspringboot.Pojo.PageBean;

public interface ArticleService {
    //新增文章
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void update(Article article);

    Article findById(Integer id);

    void deleteById(Integer id);

    PageBean<Article> formalList(Integer pageNum, Integer pageSize, Integer categoryId);
}
