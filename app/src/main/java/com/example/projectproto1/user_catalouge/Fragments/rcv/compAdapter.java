package com.example.projectproto1.user_catalouge.Fragments.rcv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectproto1.R;
import com.example.projectproto1.user_catalouge.Fragments.complaint_show;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class compAdapter extends FirebaseRecyclerAdapter<model,compAdapter.myviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public compAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.title.setText(model.getTitle());
        holder.refid.setText(model.getRefid());
        holder.taxno.setText(model.getTaxno());
        holder.date.setText(model.getDate());

        holder.crdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                int commit = activity.getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, new complaint_show(model.getComplaint(), model.getRefid(), model.getTaxno(), model.getTitle())).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_row_design,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        TextView title,refid,taxno,date;
        CardView crdView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.titlerow);
            refid=itemView.findViewById(R.id.refidrow);
            taxno=itemView.findViewById(R.id.taxnorow);
            date=itemView.findViewById(R.id.daterow);
            crdView= itemView.findViewById(R.id.crd_view);
        }
    }
}
