package com.kilichapps.vacationscheduler.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kilichapps.vacationscheduler.Entities.Vacation;
import com.kilichapps.vacationscheduler.R;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    public class VacationViewHolder extends RecyclerView.ViewHolder {

        private final TextView vacationItemView;

        public VacationViewHolder(View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    final Vacation current = mVacations.get(position);
                    Intent intent=new Intent(context, VacationDetails.class);
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("vacationName", current.getVacationName());
                    intent.putExtra("hotelName", current.getHotelName());
                    intent.putExtra("startDate", current.getVacationStartDate());
                    intent.putExtra("endDate", current.getVacationEndDate());
                    intent.putExtra("vacationStartDate", current.getVacationStartDate());
                    intent.putExtra("vacationEndDate", current.getVacationEndDate());
                    intent.putExtra("vacaID", current.getVacationID());
                    context.startActivity(intent);

                }
            });
        }
    }

    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public VacationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_items, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VacationViewHolder holder, int position) {
        if (mVacations != null) {
            Vacation current = mVacations.get(position);
            String name = current.getVacationName();
            holder.vacationItemView.setText(name);
        }
        else {
            holder.vacationItemView.setText("No Vacation Name");
        }
    }

    private List<Vacation> allVacations;

    public void setVacations(List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();
    }

    public void setFilteredVacations(List<Vacation> filteredVacations){
        this.allVacations = filteredVacations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mVacations != null) {
            return mVacations.size();
        } else {
            return 0;
        }
    }

}