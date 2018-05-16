package com.shadowws.offurz.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shadowws.offurz.R;
import com.shadowws.offurz.interfaces.ItemClickListener;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //OUR VIEWS
    ImageView img;
    TextView nameTxt;
    public TextView posTxt;
    public TextView headertext, descTxt,cuponTxt,prdnameTxt,companyTxt,offerPecTxt,offerPriceTxt;

    ItemClickListener itemClickListener;


    public MyHolder(View itemView) {
        super(itemView);

        this.prdnameTxt= (TextView) itemView.findViewById(R.id.bestSeller_productName);
       this.companyTxt= (TextView) itemView.findViewById(R.id.bestSeller_companyName);
        this.offerPecTxt= (TextView) itemView.findViewById(R.id.bestSeller_offerPercentage);
        this.offerPriceTxt = (TextView)itemView.findViewById(R.id.bestSeller_offerPrice);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}

