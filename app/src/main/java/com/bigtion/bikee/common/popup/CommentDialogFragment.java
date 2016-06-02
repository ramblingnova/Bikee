package com.bigtion.bikee.common.popup;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.dao.Comment;
import com.bigtion.bikee.etc.dao.ReceiveObject;
import com.bigtion.bikee.etc.manager.NetworkManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016-05-26.
 */
public class CommentDialogFragment extends DialogFragment {
    // TODO : android api 버전 23이상은 필요한 권한을 체크해야 함
    // INTERNET : Network통신을 하기 위함
    @Bind(R.id.fragment_comment_dialog_rating_bar)
    RatingBar ratingBar;
    @Bind(R.id.fragment_comment_dialog_edit_text)
    EditText editText;
    @Bind(R.id.fragment_comment_dialog_button)
    Button button;

    private String bicycleId;

    private static final String TAG = "COMMENT_DIALOG_F";

    public CommentDialogFragment newInstance(String bicycleId) {
        CommentDialogFragment commentDialogFragment = new CommentDialogFragment();

        Bundle args = new Bundle();
        args.putString("BICYCLE_ID", bicycleId);
        commentDialogFragment.setArguments(args);

        return commentDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.BikeeDialog);

        Bundle args = getArguments();
        bicycleId = args.getString("BICYCLE_ID");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_dialog, container);

        ButterKnife.bind(this, view);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    button.setBackgroundResource(R.drawable.detailpage_button2);
                    button.setEnabled(true);
                } else {
                    button.setBackgroundResource(R.drawable.detailpage_button1);
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawableResource(R.color.bikeeTransParent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        params.x = 0;
        params.y = 0;
        dialog.getWindow().setAttributes(params);
    }

    @OnClick({R.id.fragment_comment_dialog_dismiss_view,
            R.id.fragment_comment_dialog_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_comment_dialog_dismiss_view:
                dismiss();
                break;
            case R.id.fragment_comment_dialog_button:
                Comment comment = new Comment();
                comment.setBody(editText.getText().toString());
                comment.setPoint((int) ratingBar.getRating());
                NetworkManager.getInstance().insertBicycleComment(
                        bicycleId,
                        comment,
                        null,
                        new Callback<ReceiveObject>() {
                            @Override
                            public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                ReceiveObject receiveObject = response.body();
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "insertBicycleComment onResponse Code : " + receiveObject.getCode()
                                                    + "\nSuccess : " + receiveObject.isSuccess()
                                                    + "\nMsg : " + receiveObject.getMsg()
                                    );
                                dismiss();
                            }

                            @Override
                            public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "insertBicycleComment onFailure Error", t);
                            }
                        });
                break;
        }
    }
}
