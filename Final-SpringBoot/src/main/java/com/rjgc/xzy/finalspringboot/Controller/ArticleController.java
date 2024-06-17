package com.rjgc.xzy.finalspringboot.Controller;

import com.rjgc.xzy.finalspringboot.Pojo.*;
import com.rjgc.xzy.finalspringboot.Service.ArticleService;
import com.rjgc.xzy.finalspringboot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated(Article.Add.class) Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article> pb =  articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article) {
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article c = articleService.findById(id);
        return Result.success(c);
    }

    @DeleteMapping
    public Result delete(Integer id){
        articleService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/formal")
    public Result<PageBean<Article>> formalList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId
    ) {
        PageBean<Article> pb =  articleService.formalList(pageNum,pageSize,categoryId);
        return Result.success(pb);
    }


}
