package com.leeeeo.mydict.models;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.leeeeo.mydict.models.EasyDictWords;

import com.leeeeo.mydict.models.EasyDictWordsDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig easyDictWordsDaoConfig;

    private final EasyDictWordsDao easyDictWordsDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        easyDictWordsDaoConfig = daoConfigMap.get(EasyDictWordsDao.class).clone();
        easyDictWordsDaoConfig.initIdentityScope(type);

        easyDictWordsDao = new EasyDictWordsDao(easyDictWordsDaoConfig, this);

        registerDao(EasyDictWords.class, easyDictWordsDao);
    }
    
    public void clear() {
        easyDictWordsDaoConfig.getIdentityScope().clear();
    }

    public EasyDictWordsDao getEasyDictWordsDao() {
        return easyDictWordsDao;
    }

}
