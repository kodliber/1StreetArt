package com.example.a77011_40_15.a1streetart.Dao;

import android.content.Context;
import android.util.Log;

import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.Entities.Articles;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;

public class ArticleBll {

    public long insertArticle(Article article, Context context)
    {
        long id = 0;

        if (article != null && context != null && !article.getUri().isEmpty()) {
            ArticleDao articleDao = new ArticleDao(context);
            articleDao.openForWrite();
            id = articleDao.insertArticle(article);
            if (id == -1)
            {
                Log.i(Constantes.MYLOGTAG, "id is " + id);
            }
            else
            {
                Log.i(Constantes.MYLOGTAG, "Photo is stored");
            }
            article.setId((int) id);
            articleDao.close();
        }
        return id;
    }

    public void deleteArticle(int id, Context context)
    {

        if (id > 0 && context != null) {
            ArticleDao articleDao = new ArticleDao(context);
            articleDao.openForWrite();
            articleDao.deleteArticle(id);
            articleDao.close();
        }
    }

    public long getArticle(int id, Context context)
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

    public Articles getAllArticles(Context context)
    {
        ArticleDao articleDao = new ArticleDao(context);
        articleDao.openForRead();
        Articles tout = articleDao.getAllArticles();
        articleDao.close();

        return tout;
    }

    public long updateArticle(int id, Article article, Context context)
    {
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
