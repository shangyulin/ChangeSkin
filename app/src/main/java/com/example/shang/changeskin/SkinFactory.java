package com.example.shang.changeskin;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shang on 2018/1/17.
 */

public class SkinFactory implements LayoutInflaterFactory {

    private List<SkinView> allList = new ArrayList<>();

    private static final String TAG = "David";
    // 系统控件前缀
    private static final String[] prefixList = {"android.widget.", "android.view.", "android.webkit."};

    /**
     * 收集需要换肤的控件
     * @param parent
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.contains(".")){
            // 自定义控件
            view = createView(context, attrs, name);
        }else{
            // 处理系统控件
            for (String pre : prefixList){
                view = createView(context, attrs, pre + name);
                if (view != null){
                    // zhaodao--跳出
                    break;
                }
            }
        }
        if (view != null){
            parseSkinView(context, attrs, view);
        }
        return view;
    }

    // 收集换肤控件
    private void parseSkinView(Context context, AttributeSet attrs, View view) {
        List<SkinItem> list = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++){
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if (attrName.contains("textColor") || attrName.contains("background")){
                // 可能需要换肤的控件
                int resId = Integer.parseInt(attrValue.substring(1));

                String entryName = context.getResources().getResourceEntryName(resId);

                String typeName = context.getResources().getResourceTypeName(resId);

                SkinItem item = new SkinItem(attrName, resId, entryName, typeName);

                list.add(item);
            }
        }
        if (!list.isEmpty()){
            SkinView skinView = new SkinView(view, list);
            allList.add(skinView);
            skinView.apply();
        }
    }

    private View createView(Context context, AttributeSet attrs, String name) {
        try {
            // 反射--调用构造方法--创建自定义控件
            Class clazz = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = clazz.getConstructor(new Class[]{Context.class, AttributeSet.class});
            return constructor.newInstance(context, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void apply() {
        for (SkinView view : allList){
            view.apply();
        }
    }


    class SkinView{
        private View view;
        private List<SkinItem> skinList;

        public SkinView(View view, List<SkinItem> skinList) {
            this.view = view;
            this.skinList = skinList;
        }

        // 换肤
        public void apply() {
            for (SkinItem item : skinList){
                if ("background".equals(item.getAttrName())){
                    if ("color".equals(item.getAttrType())){
                        // 换肤
                        view.setBackgroundColor(SkinManager.getInstance().getColor(item.getRefId()));
                    }else if("drawable".equals(item.getAttrType())){

                    }
                }
            }
        }
    }
}
