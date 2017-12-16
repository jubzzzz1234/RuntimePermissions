package com.example.jubin.permissions;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jubin on 7/12/17.
 */

public class PermissionUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PermissionUtil(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(context.getString(R.string.permission_preference),Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public  void updatePermission(String permission)
    {
        switch (permission)
        {
            case "write":
                editor.putBoolean(context.getString(R.string.permission_write),true);
                editor.commit();
                break;
            case "read":
                editor.putBoolean(context.getString(R.string.permission_read),true);
                editor.commit();
                break;
        }
    }

    public boolean checkPermission(String permission)
    {
        boolean isShown=false;
        switch (permission)
        {
            case "write" :
                isShown=sharedPreferences.getBoolean(context.getString(R.string.permission_write),false);
                break;
            case "read" :
                isShown=sharedPreferences.getBoolean(context.getString(R.string.permission_read),false);
                break;

        }
        return isShown;
    }


}
