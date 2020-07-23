package fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectwnavigation.R;

import java.util.ArrayList;

import models.ApiProvider;
import models.Bookmarks;
import models.Executor;
import models.MyDataProvider;
import models.MyUtils;
import models.Order;
import models.Persons;

public class Fragment_bkmk_specials_adapter extends RecyclerView.Adapter<Fragment_bkmk_specials_adapter.MyViewHolder>{
    private Context context;
    MyDataProvider provider;
    ApiProvider apiProvider;
    ArrayList<Bookmarks> specials;
    Fragment_bkmk_specials fragment_bkmk_specials;

    Persons curPerson;

    ProgressDialog pd;
    public Fragment_bkmk_specials_adapter( Fragment_bkmk_specials fragment_bkmk_specials, Context context, ArrayList<Bookmarks> specials) {
        this.context = context;
        this.fragment_bkmk_specials = fragment_bkmk_specials;
        this.specials = specials;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_bkmk_specials_adapter_row, parent, false);
        return new Fragment_bkmk_specials_adapter.MyViewHolder(view,fragment_bkmk_specials);
    }
     Persons p;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        provider = new MyDataProvider(context);
        apiProvider = new ApiProvider();
        curPerson = provider.getLoggedInPerson();

        Bookmarks bookm = specials.get(position);
        int executorId = bookm.getExecutorId();
       final Executor executor = apiProvider.getExecutor(executorId); //  provider.getExecutor(executorId);

        if(executor != null) {
            holder.spcltn_txt.setText(executor.getSpecialztn());
            try {
                p = apiProvider.getPerson(executor.getPersonId());   //provider.getPerson(executor.getPersonId());
                if (p.getPhoto() == null) {
                    holder.photo.setImageResource(R.drawable.executors_default_image);
                } else {
                    holder.photo.setImageBitmap(MyUtils.decodeByteToBitmap(p.getPhoto()));
                }
                holder.name.setText(p.getName());
                holder.rating.setText(p.getRating() + "");

            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.adapter_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (p.getId() == curPerson.getId()) {
                        Intent intent = new Intent(context, MyProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, Executors_view_activity.class);
                        intent.putExtra("executorIdFragment", executor.getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });

            holder.btn_popup_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, v);

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_bookm_delete:
                                    showDialogDelete(executor);
                                    return true;
                                case R.id.menu_bookm_complain:
                                    //TODO: доделать методы
                                    Toast.makeText(context, "отправлена", Toast.LENGTH_SHORT).show();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.inflate(R.menu.menu_bookm_popup);
                    popup.show();
                }
            });

        }else{
            holder.spcltn_txt.setText("Ошибка");
            try {
                holder.photo.setImageResource(R.drawable.executors_default_image);
                holder.name.setText("Специалист удален или не найден" );
                holder.rating.setText("0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fragment_bkmk_specials.contextModeEnable) {
            holder.chbox.setVisibility(View.VISIBLE);
        } else {
            holder.chbox.setVisibility(View.GONE);
            //holder.chbox.setChecked(false);
        }
    }

    private void showDialogDelete(final Executor executor) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(R.layout.dialog_answer_delete);
        Button btnSave = dialog.findViewById(R.id.dialog_answer_delete_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_answer_delete_btn_cancel);

        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Executor r =apiProvider.getExecutor(executor.getId()); // provider.getExecutor(executorId);
                //TODO: передавать айди закладки

                specials.remove(executor.getId());

                DeleteExecutorTask task = new DeleteExecutorTask();
                task.execute(curPerson.getId() , executor.getId());
               // provider.deleteExecutorFromMyBookmarks(executor.getId());
                notifyDataSetChanged();
                Toast.makeText(context, "Специалист удален из ваших закладок", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (specials == null) {
            return 0;
        }
        return specials.size();
    }

    public void removeItem(ArrayList<Bookmarks> selectionList) {
        for (int i = 0; i < selectionList.size(); i++) {
            specials.remove(selectionList.get(i));
            DeleteExecutorTask task = new DeleteExecutorTask();
            task.execute(curPerson.getId(), selectionList.get(i).getExecutorId());
         //provider.deleteExecutorFromMyBookmarks(selectionList.get(i).getExecutorId());
            notifyDataSetChanged();
        }
        Toast.makeText(context, "Успешно удалено из закладок", Toast.LENGTH_SHORT).show();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, spcltn_txt, rating;
        ImageView photo;
        View view;
        Button btn_popup_menu;
        CheckBox chbox;
        LinearLayout adapter_layout;

        public MyViewHolder(@NonNull View itemView, Fragment_bkmk_specials fragment_bkmk_specials) {
            super(itemView);

            view = itemView;
            name = itemView.findViewById(R.id.frg_bkmk_specials_adapter_name);
            rating = itemView.findViewById(R.id.frg_bkmk_specials_adapter_rating);
            spcltn_txt = itemView.findViewById(R.id.frg_bkmk_specials_adapter_spec);
            chbox = itemView.findViewById(R.id.frg_bkmk_specials_adapter_chbox);
            photo = itemView.findViewById(R.id.frg_bkmk_specials_adapter_image);
            btn_popup_menu = itemView.findViewById(R.id.frg_bkmk_specials_adapter_btn_popup);

            chbox.setOnClickListener(this);
            view.setOnLongClickListener(fragment_bkmk_specials);

            adapter_layout = itemView.findViewById(R.id.frg_bkmk_specials_adapter_layout);

        }

        @Override
        public void onClick(View v) {
            fragment_bkmk_specials.setSelection(v, getAdapterPosition());
        }
    }

    private class DeleteExecutorTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setMessage("Пожалуйста, подождите");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                apiProvider.deleteExecutorFromPersonBookmarks(params[0], params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }
}
