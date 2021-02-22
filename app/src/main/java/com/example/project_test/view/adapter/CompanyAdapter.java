package com.example.project_test.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.model.utils.Company;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {
    Context context;
    List<Company> companies;
    private OnItemClicked onItemClicked;
  public   interface OnItemClicked{
        void onClicked(int position);
    }

    public CompanyAdapter(Context context, List<Company> companies,OnItemClicked onItemClicked) {
        this.context = context;
        this.companies = companies;
        this.onItemClicked=onItemClicked;
    }

    @NonNull
    @Override
    public CompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.company_cell,parent,false);
        return new CompanyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyHolder holder, int position) {
      holder.company_name.setText(companies.get(position).getCompanyName());
      holder.txt_rate.setText(companies.get(position).getRate()+"");
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onItemClicked.onClicked(position);
              Log.d("position",position+"");
          }
      });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }
    public class CompanyHolder extends  RecyclerView.ViewHolder{
          TextView company_name,txt_rate;

        public CompanyHolder(@NonNull View itemView) {
            super(itemView);
            company_name=itemView.findViewById(R.id.txt_company_name);
            txt_rate=itemView.findViewById(R.id.txt_rate);
        }
    }
}
