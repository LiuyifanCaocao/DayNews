package com.jredu.liuyifan.mygoalapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.liuyifan.mygoalapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPhoneFragment extends Fragment {
    TextView mTextView;
    TextView mTextView_verification;
    TextView mTextView_text_view;
    EditText mEditText_phone;
    EditText mEditText_pwd;
    ImageView mImageView;
    Button mButton_to_logon_fragment;
    PhoneFragment mPhoneFragment;
    Toast mToast;
    ImageView mImageView_one;
    ImageView mImageView_two;
    ImageView mImageView_three;
    ImageView imageView_qq_login;

    String i;
    public interface PhoneFragment {
        void dismiss_phone();
        void qq_login_phone();
    }

    public void get_phoneFragment(PhoneFragment phoneFragment) {
        mPhoneFragment = phoneFragment;
    }

    public LoginPhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_phone, container, false);
        mTextView = (TextView) v.findViewById(R.id.login_phone);
        mTextView_text_view = (TextView) v.findViewById(R.id.text_view);
        mEditText_phone = (EditText) v.findViewById(R.id.phone);
        mEditText_pwd = (EditText) v.findViewById(R.id.pwd);
        mImageView = (ImageView) v.findViewById(R.id.show);
        mImageView_one = (ImageView) v.findViewById(R.id.one);
        mImageView_two = (ImageView) v.findViewById(R.id.two);
        mImageView_three = (ImageView) v.findViewById(R.id.three);
        imageView_qq_login = (ImageView) v.findViewById(R.id.QQ_login);
        imageView_qq_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneFragment.qq_login_phone();
                //mPhoneFragment.dismiss_phone();
            }
        });
        mTextView_verification = (TextView) v.findViewById(R.id.verification);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageView_one.getVisibility() == View.GONE){
                    mImageView.setImageResource(R.drawable.shadow);
                    mImageView_one.setVisibility(View.VISIBLE);
                    mImageView_two.setVisibility(View.VISIBLE);
                    mImageView_three.setVisibility(View.VISIBLE);
                }else {
                    mImageView.setImageResource(R.drawable.like_arrow_textpage);
                    mImageView_one.setVisibility(View.GONE);
                    mImageView_two.setVisibility(View.GONE);
                    mImageView_three.setVisibility(View.GONE);
                }
            }
        });
        mButton_to_logon_fragment = (Button) v.findViewById(R.id.to_logon_fragment);
        mButton_to_logon_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEditText_phone.getText())) {
                    if (mToast == null){
                        mToast = Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT);
                    }else {
                        mToast.setText("手机号不能为空");
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    }
                    mToast.show();
                    mTextView_text_view.setVisibility(View.VISIBLE);
                } else {
                    if (mEditText_pwd.getText().toString().equals(i)){
                        if (mToast != null){
                            mToast.cancel();
                        }
                        mPhoneFragment.dismiss_phone();
                    }else {
                        if (mToast == null){
                            mToast = Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT);
                        }else {
                            mToast.setText("验证码错误");
                            mToast.setDuration(Toast.LENGTH_SHORT);
                        }
                        mToast.show();
                    }
                }
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mTextView_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEditText_phone.getText())){
                    i = (int) (Math.random()*8999+1000)+"";
                    mEditText_pwd.setText(i);
                    if (mToast == null){
                        mToast = Toast.makeText(getActivity(), i, Toast.LENGTH_SHORT);
                    }else {
                        mToast.setText(i);
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    }
                    mToast.show();
                }else {
                    if (mToast == null){
                        mToast = Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT);
                    }else {
                        mToast.setText("手机号不能为空");
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    }
                    mToast.show();
                }
            }
        });
        return v;
    }

}
