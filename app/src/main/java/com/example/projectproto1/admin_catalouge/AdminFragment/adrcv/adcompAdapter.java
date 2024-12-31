package com.example.projectproto1.admin_catalouge.AdminFragment.adrcv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectproto1.R;
import com.example.projectproto1.admin_catalouge.AdminFragment.adcomplaint_show;
import com.example.projectproto1.user_catalouge.Fragments.complaint_show;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adcompAdapter extends FirebaseRecyclerAdapter<admodel,adcompAdapter.admyviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adcompAdapter(@NonNull FirebaseRecyclerOptions<admodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull admyviewholder holder, int position, @NonNull admodel admodel) {

        holder.adtitle.setText(admodel.getTitle());
        holder.adrefid.setText(admodel.getRefid());
        holder.adtaxno.setText(admodel.getTaxno());
        holder.addate.setText(admodel.getDate());
        holder.adstatusshow.setText(admodel.getStatus());

        holder.adcrdview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.adframecontainer,new adcomplaint_show(admodel.getComplaint(),admodel.getRefid(),admodel.getTaxno(),admodel.getTitle(),admodel.getDepartment())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public admyviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adcomplaint_row_design,parent,false);
        return new admyviewholder(view);
    }

    public class admyviewholder extends RecyclerView.ViewHolder{

        TextView adtitle,adrefid,adtaxno,addate,adstatusshow;
        CardView adcrdview;

        public admyviewholder(@NonNull View itemView) {
            super(itemView);
            adtitle=itemView.findViewById(R.id.adtitlerow);
            adrefid=itemView.findViewById(R.id.adrefidrow);
            adtaxno=itemView.findViewById(R.id.adtaxnorow);
            addate=itemView.findViewById(R.id.addaterow);
            adcrdview=itemView.findViewById(R.id.adcrdview);
            adstatusshow=(itemView).findViewById(R.id.adstatusrow);
        }
    }
}
