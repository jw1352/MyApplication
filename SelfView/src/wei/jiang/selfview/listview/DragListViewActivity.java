package wei.jiang.selfview.listview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wei.jiang.selfview.R;

public class DragListViewActivity extends Activity {
    private DragListView dragListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_list_view);

        dragListView = (DragListView) findViewById(R.id.dragListView);
        dragListView.setDragViewId(R.id.tvDrag);
        List<String> data = initData();
        MyAdapter dragAdapter = new MyAdapter(data,this);
        dragListView.setAdapter(dragAdapter);
    }

    private List<String> initData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("标题xxx" + i);
        }
        return data;
    }

    private class MyAdapter extends DragAdapter {

        private List<String> data;
        private Context context;

        public MyAdapter(List<String> data, Context context) {
            super(data);
            this.data = data;
            this.context = context;
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = View.inflate(context, R.layout.list_view_item, null);
                holder.tvDrag = (TextView) convertView.findViewById(R.id.tvDrag);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tvName.setText(data.get(position));

            return convertView;
        }

        private class Holder {
            TextView tvName;
            TextView tvDrag;
        }
    }

}
