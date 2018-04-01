package com.example.a77011_40_15.a1streetart.Entities;

import java.net.URI;
import java.util.Date;

/**
 * Created by 77011-40-15 on 26/03/2018.
 * classe qui décrit les oeuvres de Street Art.
 * Les informations seront stockees dans la BDD SQLite.
 */

public class Article {
    private String name;
    private int id;
    private int res; // ressource graphique dans les drawable
    private String description;

    public void setDate(String date)
    {
        this.date = date;
    }

    private String date; // date du cliché, pour comparer dans le temps si une oeuvre a plusieurs clichés ?
    private String  uri;
    private double latitude;
    private double longitude;

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String nom) {
        this.name = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getDate()
    {
        return date;
    }
}
