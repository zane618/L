package com.zane.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

public abstract class BaseFragment extends Fragment {
	protected Gson mGson;
	protected Context mContext;
	protected Activity activity;
	private ProgressDialog progressDialog = null;
//	protected Gson mGson;
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
		this.activity = activity;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		mGson = new Gson();
		initView(view, savedInstanceState);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
//		OkGo.getInstance().cancelTag(this);
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


}
