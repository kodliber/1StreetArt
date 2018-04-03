package com.example.a77011_40_15.a1streetart.Entities;

import java.util.ArrayList;

/**
 * ce tableau sera l'intermediaire entre la BDD et differents adapter
 */
class manInTheMiddle extends ArrayList<Article>
{
    private static final manInTheMiddle ourInstance = new manInTheMiddle();

    static manInTheMiddle getInstance()
    {
        return ourInstance;
    }

    private manInTheMiddle()
    {
    }
}
