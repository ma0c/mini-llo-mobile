package com.contraslash.android.mini_llo.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.contraslash.android.mini_llo.activities.BoardDetail;
import com.contraslash.android.mini_llo.config.Conf;
import com.contraslash.android.mini_llo.databinding.ElementBoardBinding;
import com.contraslash.android.mini_llo.databinding.ElementIdeaBinding;
import com.contraslash.android.mini_llo.models.Board;
import com.contraslash.android.mini_llo.models.Idea;

import java.util.ArrayList;
import java.util.List;

public class IdeaArrayAdapter extends ArrayAdapter<Idea> {
    private Context context;
    private int resource;
    private ArrayList<Idea> elements;

    public IdeaArrayAdapter(@NonNull Context context, int resource, @NonNull List<Idea> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.elements = (ArrayList<Idea>)objects;
    }

    @Override
    public int getCount() {
        return this.elements.size();
    }

    @Nullable
    @Override
    public Idea getItem(int position) {
        return this.elements.get(position);
    }

    @Override
    public void clear() {
        this.elements.clear();
    }

    @Override
    public void add(@Nullable Idea object) {
        this.elements.add(object);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ElementIdeaBinding binding = DataBindingUtil.getBinding(convertView);
        if(convertView == null) {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    this.resource,
                    parent,
                    false
            );
            convertView = binding.getRoot();
        }
        final Idea actualIdea =  elements.get(position);

        binding.elementIdeaName.setText(actualIdea.getText());

        return convertView;
    }

}
