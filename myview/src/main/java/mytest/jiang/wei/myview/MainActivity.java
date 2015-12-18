package mytest.jiang.wei.myview;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private RelativeLayout floatGroupLayout;
    private List<Map<String, Object>> list;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatGroupLayout = (RelativeLayout) findViewById(R.id.floatGroupLayout);
        tvDate = (TextView) floatGroupLayout.findViewById(R.id.tvDate);
        listView = (ListView) findViewById(R.id.listView);
        initData();


        listView.setAdapter(new MyAdapter(this));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0) {
                    tvDate.setText(list.get(firstVisibleItem).get("date").toString());
                }
            }
        });

    }

    private void initData() {
        list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < 10; i ++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", "2015-12-1" + i);
            List<String> colList = new ArrayList<String>();
            for (int j = 0; j < 10; j++) {
                colList.add("第" + i + "组，第" + j + "条数据");
            }
            map.put("col", colList);
            list.add(map);
        }

        //要放在初始后
        floatGroupLayout.setVisibility(View.VISIBLE);
        if (list.size() > 0) {
            tvDate.setText(list.get(0).get("date").toString());
        }
    }


    public class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = inflater.inflate(R.layout.group_item, null);
                holder.groupLayout = (LinearLayout) convertView.findViewById(R.id.groupLayout);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
                holder.groupLayout.removeAllViews(); //一定要清空
            }

            Map<String, Object> listItem = list.get(position);
            String date = listItem.get("date").toString();
            holder.tvDate.setText(date);

            List<String> itemList = (List<String>) listItem.get("col");
            for (String item : itemList) {
                View itemView = inflater.inflate(R.layout.col_item, null);
                TextView tvContent = (TextView) itemView.findViewById(R.id.tvContent);
                tvContent.setText(item);
                tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, ((TextView) v).getText().toString(), 0).show();
                    }
                });
                holder.groupLayout.addView(itemView);
            }
            return convertView;
        }
    }

    public class Holder {
        private LinearLayout groupLayout;
        private TextView tvDate;
    }
}
