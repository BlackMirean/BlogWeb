package com.rjgc.xzy.finalspringboot.Service;

import com.rjgc.xzy.finalspringboot.Pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category findById(Integer id);

    void update(Category category);

    void deleteById(Integer id);

    List<Category> formalList();
}
