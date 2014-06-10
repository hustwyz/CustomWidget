
package hust.wyz.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import hust.wyz.widget.R;
import hust.wyz.widget.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿微信滑动删除
 * 
 * @author WangYunzhen
 */
public class SampleSwipeMenuActivity extends Activity {

    private static final String[] CONTENTS = {
            "滑动删除 1", "滑动删除 2", "滑动删除 3", "滑动删除 4", "滑动删除 5", "滑动删除 6", "滑动删除 7", "滑动删除 8",
            "滑动删除 9", "滑动删除 10", "滑动删除 11"
    };

    private SampleSwipeMenuActivity mContext;

    private List<SwipeMenuItem> list = new ArrayList<SwipeMenuItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_swipe_menu);
        ListView lv = (ListView)findViewById(R.id.lv);
        lv.setAdapter(new ScrollAdapter());
        lv.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("OnScrollListener", "onScrollStateChanged");
                for (SwipeMenuItem item : list) {
                    if (item.isOpen()) {
                        item.closeMenu();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
            }
        });
    }

    static class ViewHolder {
        public TextView itemContent;

        public View menuDelete;

        public View menuTop;
    }

    class ScrollAdapter extends BaseAdapter {

        private List<String> mList;

        public ScrollAdapter() {
            mList = new ArrayList<String>(CONTENTS.length);
            for (String item : CONTENTS) {
                mList.add(item);
            }
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private void deleteItem(int position) {
            mList.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            final SwipeMenuItem swipeMenuItem;
            if (convertView == null) {
                holder = new ViewHolder();
                swipeMenuItem = new SwipeMenuItem(mContext);
                convertView = swipeMenuItem;
                holder.itemContent = (TextView)convertView.findViewById(R.id.content);
                holder.menuDelete = convertView.findViewById(R.id.menu_delete);
                holder.menuTop = convertView.findViewById(R.id.menu_top);
                list.add((SwipeMenuItem)convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
                swipeMenuItem = (SwipeMenuItem)convertView;
            }
            String content = getItem(position);
            holder.itemContent.setText(content);
            final int p = position;
            holder.menuDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(p);
                    swipeMenuItem.reset();
                }
            });
            return convertView;
        }

    }

}
