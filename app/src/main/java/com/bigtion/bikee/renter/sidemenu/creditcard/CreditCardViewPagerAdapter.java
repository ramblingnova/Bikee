package com.bigtion.bikee.renter.sidemenu.creditcard;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-05-10.
 */
public class CreditCardViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<CreditCardItem> itemList;
    private List<CreditCardView> viewList;

    private static final String TAG = "CARD_VIEW_PAGER_ADAPTER";

    public CreditCardViewPagerAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
        viewList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return itemList.size() == viewList.size() ? itemList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        viewList.get(position).setView(itemList.get(position));
        viewList.get(position).setOnCreditCardViewClickListener(new OnCreditCardViewClickListener() {
            @Override
            public void onDeleteClick(View view) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onDeleteClick");
                // TODO : 카드 삭제, 현재 remove를 해도 뷰가 남아있는 상황
//                itemList.remove(position);
//                viewList.remove(position);
//                notifyDataSetChanged();
            }

            @Override
            public void onCompleteClick(CheckBox representationCheckBox) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onCompleteClick");
                // TODO : 설정한 대표 카드를 SharedPreference에 저장해야 하는 문제가 있음
                if (representationCheckBox.isChecked())
                    for (int i = 0; (i != position) && (i < itemList.size()); i++) {
                        itemList.get(i).setRepresentation(false);
                        ((CheckBox) viewList.get(i).findViewById(R.id.view_credit_card_set_representation_check_box)).setChecked(false);
                    }
                notifyDataSetChanged();
            }
        });

        container.addView(viewList.get(position));

        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void add(CreditCardItem item) {
        itemList.add(item);
        viewList.add(new CreditCardView(context));
        notifyDataSetChanged();
    }

    public void clear() {
        itemList.clear();
        notifyDataSetChanged();
    }
}
