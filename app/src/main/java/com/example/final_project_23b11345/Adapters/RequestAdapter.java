package com.example.final_project_23b11345.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_23b11345.Model.SupportRequest;
import com.example.final_project_23b11345.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RequestAdapter extends  RecyclerView.Adapter<RequestAdapter.SupportRequestViewHolder> {

    private final ArrayList<SupportRequest> requestArrayList;

    private final String[] request_Status;

    public RequestAdapter(Context context, @NonNull ArrayList<SupportRequest> packages) {
        this.requestArrayList = packages;
        this.request_Status =  context.getResources().getStringArray(R.array.requests_options);
    }

    @NonNull
    @Override
    public SupportRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
        return new SupportRequestViewHolder(view);
    }
    private SupportRequest getItem(int position) {
        return this.requestArrayList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull SupportRequestViewHolder holder, int position) {
        SupportRequest supportRequest = this.getItem(position);
        holder.materialTextViewId.setText(supportRequest.getParcelTrackingNumber());
        holder.materialTextViewRequestStatus.setText(request_Status[supportRequest.getRequestStatus().getValue()]);
        holder.progressIndicator.setProgress(supportRequest.getRequestStatus().getIndicatorValue(),true);
    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public static class SupportRequestViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView materialTextViewId;
        private final MaterialTextView materialTextViewRequestStatus;
        private final CircularProgressIndicator progressIndicator;

        public SupportRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            this.materialTextViewId = itemView.findViewById(R.id.textview_Request_Id);
            this.materialTextViewRequestStatus = itemView.findViewById(R.id.textview_Request_status);
            this.progressIndicator = itemView.findViewById(R.id.requestIndicator);

        }

    }

}
