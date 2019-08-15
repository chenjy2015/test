package com.example.chenjy.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenjy on 2019/4/24.
 */

public class BitmapUtils {
    public static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    public static Bitmap loadBitmap(Context context, String url) {
        Bitmap bitmap = null;
        FutureTarget<Bitmap> target = Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(
                        new RequestOptions().dontAnimate().centerCrop().override(180, 260)).thumbnail(0.5f)
                .submit();
        try {
            bitmap = target.get();
            if (bitmap != null) {
                return bitmap;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static boolean saveBitmap(Bitmap bitmap, String destPath) {
        if (bitmap == null) {
            return false;
        }
        if (bitmap.isRecycled()){
            return false;
        }
        //创建文件
        File f = new File(destPath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.write(bitmap.getRowBytes());
            out.flush();
            out.close();
            Log.i("saveBitmap", "已经保存");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
