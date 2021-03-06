package com.tpb.projects.repo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tpb.projects.R;
import com.tpb.projects.data.Loader;
import com.tpb.projects.data.auth.models.Project;
import com.tpb.projects.util.Data;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by theo on 17/12/16.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> implements Loader.ProjectsLoader {
    private static final String TAG = ProjectAdapter.class.getSimpleName();

    private Project[] mProjects = new Project[0];
    private ProjectEditor mEditor;

    public ProjectAdapter(ProjectEditor editor) {
        mEditor = editor;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_project, parent, false));
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.mName.setText(mProjects[position].getName());
        holder.mLastUpdate.setText(
                String.format(
                        holder.itemView.getContext().getString(R.string.text_last_updated),
                        Data.timeAgo(mProjects[position].getUpdatedAt())
                )
        );
    }

    @Override
    public int getItemCount() {
        return mProjects.length;
    }

    @Override
    public void projectsLoaded(Project[] projects) {
        Log.i(TAG, "projectsLoaded: " + Arrays.toString(projects));
        mProjects = projects;
        notifyDataSetChanged();
    }

    void clearProjects() {
        mProjects = new Project[0];
        notifyDataSetChanged();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.project_name) TextView mName;
        @BindView(R.id.project_last_updated) TextView mLastUpdate;

        ProjectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener((v) -> mEditor.editProject(mProjects[getAdapterPosition()]));
        }

    }

    interface ProjectEditor {

        void editProject(Project project);
    }

}
