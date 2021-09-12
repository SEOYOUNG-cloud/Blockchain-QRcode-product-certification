package com.example.PARM;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CustomViewHolder> {

    private ArrayList<ProductData> mList = null;
    private Activity context = null;


    private ProductAdapter.OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(ProductAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }


    public ProductAdapter(Activity context, ArrayList<ProductData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView manu_name;
        protected TextView dis_name;
        protected TextView shop_name;


        public CustomViewHolder(View view) {
            super(view);
            this.manu_name = (TextView) view.findViewById(R.id.manu_Name);
            this.dis_name = (TextView) view.findViewById(R.id.dis_Name);
            this.shop_name = (TextView) view.findViewById(R.id.shop_Name);


        }
    }


    @Override
    public ProductAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item, null);
        ProductAdapter.CustomViewHolder viewHolder = new ProductAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.CustomViewHolder viewholder, int position) {


        viewholder.manu_name.setText(mList.get(position).getmanu_name());
        viewholder.dis_name.setText(mList.get(position).getdis_name());
        viewholder.shop_name.setText(mList.get(position).getshop_name());





    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
