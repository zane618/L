/*
package com.zane;

import android.content.Context;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.Request;
import com.zane.dialog.CustomDialog;
import com.zane.l.common.R;

*/
/**
 * Created by shizhang on 2017/6/26.
 *//*


public abstract class StringDialogCall extends StringCallback {
    private CustomDialog dialog;

    public StringDialogCall(Context context) {
        dialog = new CustomDialog(context, R.style.dialog);
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        //网络请求前显示对话框
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
*/
