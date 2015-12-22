package wei.jiang.selfview.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wei.jiang.selfview.R;

/**
 * Created by jiangwei on 2015/12/22.
 */
public class DragAdapter extends BaseAdapter{

    private List<String> data;
    private Context context;

    public DragAdapter(List<String> data, Context context) {
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
