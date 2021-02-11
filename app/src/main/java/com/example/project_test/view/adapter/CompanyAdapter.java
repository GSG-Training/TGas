package com.example.project_test.view.adapter;

import android.content.Context;
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

    public CompanyAdapter(Context context, List<Company> companies) {
        this.context = context;
        this.companies = companies;
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
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }
    public class CompanyHolder extends  RecyclerView.ViewHolder{
          TextView company_name;
        public CompanyHolder(@NonNull View itemView) {
            super(itemView);
            company_name=itemView.findViewById(R.id.txt_company_name);
        }
    }
}
