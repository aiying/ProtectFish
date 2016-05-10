package com.wang.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import com.example.wang.gametwo.R;
import com.wang.utils.GameData;
import java.io.FileNotFoundException;

/**
 * Created by WANG on 2016/4/19.
 */
public class FreeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_layout);

        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            Uri uri = data.getData();
            //inJustDecodeBounds该值设为true,将不返回实际的bitmap,不给其分配内存空间
            // 只是得到图片大小信息，获取到outHeight(图片原始高度)和
            // outWidth(图片的原始宽度)，然后计算一个inSampleSize(缩放值)
            BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    // 用uri来获取图片， 获取这个图片的宽和高，此时bitmap为空
            try {
                GameData.bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri),null,options);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
            options.inJustDecodeBounds = false;
            int biH = options.outHeight / 200;
            int biW = options.outWidth / 200;
            int bi = 0;
            if (biH > biW) {
                bi = biH;
            } else {
                bi = biW;
            }
            if (bi <= 0) {
                bi = 1;
            }
            options.inSampleSize = bi;// 图片宽高都为原来bi分之一
            // 此时bitmap不为空，为缩放后的图片大小
            try {
                GameData.bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), null, options);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //是这个activity消失就可，不用再CCScene和activity之间切换来切换去
            finish();
        }
    }

}
