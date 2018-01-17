package com.example.shang.changeskin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Shang on 2018/1/17.
 */
public class SkinManager {

    private Context context;

    private Resources skinResources;// 皮肤apk
    private String skinPackageName;// 皮肤apk的包名

    private static SkinManager ourInstance = new SkinManager();

    public static SkinManager getInstance() {
        return ourInstance;
    }

    private SkinManager() {
    }

    public void loadSkin(String path){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            // 反射
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            skinResources = new Resources(assetManager, context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());

            PackageManager packageManager = context.getPackageManager();
            skinPackageName = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    public int getColor(int resid){
        if (skinResources == null){
            return  resid;
        }
        String resName = context.getResources().getResourceEntryName(resid);
        int skinid = skinResources.getIdentifier(resName, "color", skinPackageName);
        if (skinid == 0){
            return resid;
        }
        return skinResources.getColor(resid);
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

}
