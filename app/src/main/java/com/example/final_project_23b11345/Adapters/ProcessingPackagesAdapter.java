package com.example.final_project_23b11345.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_23b11345.Interfaces.DetailsCallback;
import com.example.final_project_23b11345.Model.Parcel;
import com.example.final_project_23b11345.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ProcessingPackagesAdapter extends  RecyclerView.Adapter<ProcessingPackagesAdapter.ProcessingParcelViewHolder> {
    @NonNull
    private final ArrayList<Parcel> packages;
    private DetailsCallback detailsCallback;

    private final String[] parcel_Methods,parcel_Status;

    public void setDetailsCallback(DetailsCallback detailsCallback) {
        this.detailsCallback = detailsCallback;
    }
    public ProcessingPackagesAdapter(Context context, @NonNull ArrayList<Parcel> packages) {
        this.packages = packages;
        this.parcel_Methods = context.getResources().getStringArray(R.array.parcel_methods);
        this.parcel_Status = context.getResources().getStringArray(R.array.parcel_status);

    }
    @NonNull
    @Override
    public ProcessingParcelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.processing_item, parent, false);
        return new ProcessingParcelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessingParcelViewHolder holder, int position) {
        Parcel parcel = this.getItem(position);
        holder.materialTextViewDate.setText(parcel.getOrderDate());
        holder.materialTextViewStatus.setText(parcel_Status[parcel.getPackageStatus().getValue()]);
        holder.materialTextViewId.setText(parcel.getTrackingNumber());
        holder.materialTextViewMethod.setText(parcel_Methods[parcel.getDeliveryMethod().getValue()]);
        holder.progressIndicator.setProgress(parcel.getPackageStatus().getIndicatorValue(),true);
    }

    private Parcel getItem(int position) {
        return this.packages.get(position);
    }

    @Override
    public int getItemCount() {
        return this.packages.size();
    }

    public class ProcessingParcelViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView materialTextViewDate;
        private final MaterialTextView materialTextViewStatus;
        private final MaterialTextView materialTextViewId;
        private final MaterialTextView materialTextViewMethod;
        private LinearProgressIndicator progressIndicator;

        public ProcessingParcelViewHolder(@NonNull View itemView) {
            super(itemView);
            this.materialTextViewDate = itemView.findViewById(R.id.textview_Package_Date);
            this.materialTextViewStatus = itemView.findViewById(R.id.processing_Textview_packageStatus);
            this.materialTextViewId = itemView.findViewById(R.id.textview_Package_Id);
            this.materialTextViewMethod = itemView.findViewById(R.id.textview_Package_Method);
            this.progressIndicator = itemView.findViewById(R.id.processingIndicator);
            MaterialButton details = itemView.findViewById(R.id.processing_Details_BTN);
            this.progressIndicator = itemView.findViewById(R.id.processingIndicator);
            details.setOnClickListener((v) -> {
            if (ProcessingPackagesAdapter.this.detailsCallback != null) {
                ProcessingPackagesAdapter.this.detailsCallback.itemClicked(ProcessingPackagesAdapter.this.getItem(this.getAbsoluteAdapterPosition()));
            }
            });
        }

    }

}
