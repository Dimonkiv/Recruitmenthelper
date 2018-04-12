package com.pllug.course.ivankiv.recruitmenthelper.data.contentprovider;

import android.content.ContentResolver;
import android.content.Context;

import com.pllug.course.ivankiv.recruitmenthelper.data.db.InitDatabase;

public class ContentResolverLib extends InitDatabase {
    private static ContentResolverLib me;

    public ContentResolverLib() {
        me = this;
    }

    public static Context Context() {
        return me;
    }

    public static ContentResolver ContentResolver() {
        return me.getContentResolver();
    }
}

