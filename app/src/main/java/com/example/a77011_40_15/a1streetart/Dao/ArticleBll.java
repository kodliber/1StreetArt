package com.example.a77011_40_15.a1streetart.Dao;

import android.content.Context;

import com.example.a77011_40_15.a1streetart.Entities.Article;

public class ArticleBll {

    public static long insertArticle(Article article, Context context)
    {
        long id = 0;

        if (article != null && context != null && !article.getName().isEmpty()) {
            ArticleDao articleDao = new ArticleDao(context);
            articleDao.openForWrite();
            id = articleDao.insertArticle(article);
            articleDao.close();
        }
        return id;
    }

    public static void deleteArticle(int id, Context context)
    {

        if (id > 0 && context != null) {
            ArticleDao articleDao = new ArticleDao(context);
            articleDao.openForWrite();
            articleDao.deleteArticle(id);
            articleDao.close();
        }
    }

    public static long getArticle(int id, Context context)
    {
        long i = 0;
        if (id > 0 && context != null) {
            ArticleDao articleDao = new ArticleDao(context);
            articleDao.openForRead();
            i = articleDao.getArticle(id);
            articleDao.close();
        }
        return i;
    }

    public static long updateArticle(int id, Article article, Context context){
        long i = 0;
        if (id > 0 && context != null) {
            ArticleDao articleDao = new ArticleDao(context);
            articleDao.openForWrite();
            i = articleDao.updateArticle(article);
            articleDao.close();
        }
        return i;
    }
}
