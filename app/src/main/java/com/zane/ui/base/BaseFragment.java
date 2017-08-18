package com.zane.ui.base;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.zane.l.R;

public abstract class BaseFragment extends Fragment {
	protected Gson mGson;
	protected Context mContext;
	protected Activity mActivity;
	private ProgressDialog progressDialog = null;
	protected View noDataView, errorView;

	protected abstract void initView(View view, Bundle savedInstanceState);



	//获取布局文件ID
	protected abstract int getLayoutId();

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.mContext = context;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		initView(view, savedInstanceState);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		OkGo.getInstance().cancelTag(this);
	}

	/**
	 * 隐藏floatBtn
	 */
	protected void hideFloatBtn(View view, int viewHeight) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, viewHeight);

		animator.setDuration(300);
		animator.start();
	}

	/**
	 * 显示floatBtn
	 */
	protected void showFloatBtn(View view, int viewHeight) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, viewHeight, 0);
		animator.setDuration(300);
		animator.start();
	}
	//显示progressdialog
	protected void showProDialog() {
		if (progressDialog != null) {
			progressDialog.show();
		} else {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setTitle("Loging..");
			progressDialog.setMessage("please wait..");
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}
	}
	//隐藏progressdialog
	protected void hideProDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		mActivity.overridePendingTransition(R.anim.fade, R.anim.hold);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		mActivity.overridePendingTransition(R.anim.fade, R.anim.hold);
	}
}
