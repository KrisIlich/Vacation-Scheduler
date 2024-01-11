package com.kilichapps.vacationscheduler.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kilichapps.vacationscheduler.Entities.Excursion;
import com.kilichapps.vacationscheduler.R;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    class ExcursionViewHolder extends RecyclerView.ViewHolder {

        private final TextView excursionItemView;
        private final TextView excursionItemView2;

        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.eItemView);
            excursionItemView2 = itemView.findViewById(R.id.eItemView2);

            itemView.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("excursionID", current.getExcursionID());
                    intent.putExtra("excursionName", current.getExcursionName());
                    intent.putExtra("excursionStartDate", current.getExcursionStartDate());
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("vacaID", current.getVacationID());
                    context.startActivity(intent);
                }
            });


        }

    }

    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView=mInflater.inflate(R.layout.excursion_list_items,parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursion current = mExcursions.get(position);
            String excursionName = current.getExcursionName();
            String excursionDate = current.getExcursionStartDate();
            holder.excursionItemView.setText(excursionName);
            holder.excursionItemView2.setText(excursionDate);
        } else {
            holder.excursionItemView.setText("No Excursion Name");
            holder.excursionItemView2.setText("No Excursion ID");
        }
    }



    @Override
    public int getItemCount() {
        return mExcursions.size();
    }

    public void setExcursions(List<Excursion> excursions){
            mExcursions=excursions;
            notifyDataSetChanged();

        }




}

