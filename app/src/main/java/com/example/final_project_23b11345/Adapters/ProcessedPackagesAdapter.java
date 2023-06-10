package com.example.final_project_23b11345.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_23b11345.Interfaces.DetailsCallback;
import com.example.final_project_23b11345.R;
import com.example.final_project_23b11345.Model.Parcel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ProcessedPackagesAdapter extends RecyclerView.Adapter<ProcessedPackagesAdapter.ProcessedParcelViewHolder>  {
    private final ArrayList<Parcel> packages;
    private DetailsCallback detailsCallback;

    private final String[] parcel_Status;

    public void setDetailsCallback(DetailsCallback detailsCallback) {
        this.detailsCallback = detailsCallback;
    }
    public ProcessedPackagesAdapter(Context context, @NonNull ArrayList<Parcel> packages) {
        this.packages = packages;
        this.parcel_Status = context.getResources().getStringArray(R.array.parcel_status);
    }
    @NonNull
    @Override
    public ProcessedParcelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.processed_item, parent, false);
        return new ProcessedParcelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessedParcelViewHolder holder, int position) {
        Parcel parcel = this.getItem(position);
        holder.materialTextViewDate.setText(parcel.getReceivedDate());
        holder.materialTextViewStatus.setText(parcel_Status[parcel.getPackageStatus().getValue()]);
    }

    private Parcel getItem(int position) {
        return this.packages.get(position);
    }

    @Override
    public int getItemCount() {
        return this.packages == null ? 0 : this.packages.size();
    }


    public class ProcessedParcelViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView materialTextViewDate;
        private final MaterialTextView materialTextViewStatus;

        public ProcessedParcelViewHolder(@NonNull View itemView) {
            super(itemView);
            this.materialTextViewDate = itemView.findViewById(R.id.textview_packageDate);
            this.materialTextViewStatus = itemView.findViewById(R.id.textview_packageStatus);
            MaterialButton details = itemView.findViewById(R.id.processed_Details_BTN);
            details.setOnClickListener((v) -> {
                if (ProcessedPackagesAdapter.this.detailsCallback != null) {
                    ProcessedPackagesAdapter.this.detailsCallback.itemClicked(ProcessedPackagesAdapter.this.getItem(this.getAbsoluteAdapterPosition()));
                }
            });
        }
    }
}

