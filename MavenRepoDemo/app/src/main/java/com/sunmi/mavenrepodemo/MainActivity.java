package com.sunmi.mavenrepodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sunmi.toastutils.ToastUtils;

/**
 * Created by Xiho on 17:16
 * MyBlog: xuhao.tech
 * GitHub: github.com/git-xuhao
 * Function: 调用私服maven仓库里面的arr（演示Demo类）
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtils.makeToast(this,"我来自Toast-lib，嘻嘻~");
    }
}
