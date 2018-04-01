package com.example.a77011_40_15.a1streetart.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.Entities.Articles;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;

public class ArticleDao {
    SQLiteDatabase bdd;
    ArticleSqlite articleSqlite;

//    public ArticleDao (SQLiteDatabase bdd) {this.bdd = bdd}

    public ArticleDao(Context context)
    {
        articleSqlite = new ArticleSqlite(context, Constants.NOM_BDD, null, Constants.VERSION);
    }

    public void openForWrite()
    {
        bdd = articleSqlite.getWritableDatabase();
    }

    public void openForRead()
    {
        bdd = articleSqlite.getReadableDatabase();
    }

    public void close()
    {
        bdd.close();
    }

    public long insertArticle(Article article)
    {
        long i;
        ContentValues content = new ContentValues();
        content.put(Constants.COL_URI, article.getUri());
        content.put(Constants.COL_DESC, article.getDescription());
        content.put(Constants.COL_NAME, article.getName());
        i = bdd.insert(Constants.TABLE_ARTICLES, null, content);
        Log.i(Constantes.MYLOGTAG, "i " + i );
        return i;
    }

    public long updateArticle(Article article)
    {
        ContentValues content = new ContentValues();
        content.put(Constants.COL_NAME, article.getName());
        content.put(Constants.COL_DESC, article.getDescription());
        return bdd.update(Constants.TABLE_ARTICLES, content, Constants.COL_ID + " = " + article.getId(), null);
    }

    public int deleteArticle(String name)
    {
        return bdd.delete(Constants.TABLE_ARTICLES, Constants.COL_NAME + " = " + name, null);
    }

    public int deleteArticle(int id)
    {
        return bdd.delete(Constants.TABLE_ARTICLES, Constants.COL_ID + " = " + id, null);
    }

    public Article getArticle(String name)
    {
        Cursor c = bdd.query(Constants.TABLE_ARTICLES, new String[]{
                        Constants.COL_ID,
                        Constants.COL_NAME,
                        Constants.COL_DESC},
                Constants.COL_NAME + " LIKE \"" + name + "\"",
                null, null, null, Constants.COL_NAME);
        return cursorToArticle(c);
    }

    public long getArticle(int id)
    {
        Cursor c = bdd.query(Constants.TABLE_ARTICLES, new String[]{
                        Constants.COL_ID,
                        Constants.COL_NAME,
                        Constants.COL_DESC},
                Constants.COL_ID + " LIKE \"" + id + "\"",
                null, null, null, Constants.COL_NAME);
        return cursorToArticle(c).getId();
    }

    public Article cursorToArticle(Cursor c)
    {
        c.moveToFirst();
        Article article = null;

        if (c.getCount() > 0) {
            article = new Article();
            article.setId(c.getInt(Constants.NUM_COL_ID));
            article.setName(c.getString(Constants.NUM_COL_NAME));
            article.setDescription(c.getString(Constants.NUM_COL_DESC));
            c.close();
        }
        return article;
    }

    public Articles getAllArticles()
    {
//fred penser a adapter
        Cursor c = bdd.query(Constants.TABLE_ARTICLES, new String[]{
                        Constants.COL_ID,
                        Constants.COL_NAME,
                        Constants.COL_URI,
//                        Constants.COL_DESC
                },
                null, null, null, null, Constants.COL_ID);

        Articles articles = null;

        if (c.getCount() > 0) {
//            c.moveToFirst();
            articles = new Articles();
            Article article;
            while (c.moveToNext()) {
                article = new Article();
                article.setId(c.getInt(0));
                article.setName(c.getString(1));
                article.setDescription(c.getString(2));

                articles.add(article);
            }
        }
        close();

        return articles;
    }

}
